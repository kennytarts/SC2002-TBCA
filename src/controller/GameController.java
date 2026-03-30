package controller;

import java.util.ArrayList;
import java.util.Scanner;

import controller.battle.CombatantTurnHandler;
import controller.battle.EnemyActionHandler;
import controller.battle.PlayerActionHandler;
import controller.battle.StatusEffectManager;
import controller.strategy.SpeedTurnOrderStrategy;
import model.Battle;
import model.Combatant;
import model.Goblin;
import model.Player;
import model.Potion;
import model.PowerStone;
import model.SmokeBomb;
import model.Warrior;
import model.Wizard;
import model.Wolf;
import view.BattleInputView;
import view.BattleView;
import view.GameView;

public class GameController {
    private Player player;
    private final ArrayList<Combatant> mainEnemies;
    private final ArrayList<Combatant> backupEnemies;
    private int round = 1;
    private final GameView gameView;
    private final BattleView battleView;
    private final BattleInputView battleInputView;

    public GameController() {
        Scanner scanner = new Scanner(System.in);
        this.mainEnemies = new ArrayList<Combatant>();
        this.backupEnemies = new ArrayList<Combatant>();
        this.gameView = new GameView(scanner);
        this.battleView = new BattleView();
        this.battleInputView = new BattleInputView(scanner);
    }

    public boolean selectPlayer(int selection) {
        switch (selection) {
            case 1:
                player = new Warrior();
                break;
            case 2:
                player = new Wizard();
                break;
            default:
                return false;
        }
        return true;
    }

    public boolean selectLevel(int level) {
        mainEnemies.clear();
        backupEnemies.clear();

        switch (level) {
            case 1:
                mainEnemies.add(new Goblin("Goblin A"));
                mainEnemies.add(new Goblin("Goblin B"));
                mainEnemies.add(new Goblin("Goblin C"));
                break;
            case 2:
                mainEnemies.add(new Goblin("Goblin"));
                mainEnemies.add(new Wolf("Wolf"));
                backupEnemies.add(new Wolf("Wolf A"));
                backupEnemies.add(new Wolf("Wolf B"));
                break;
            case 3:
                mainEnemies.add(new Goblin("Goblin A"));
                mainEnemies.add(new Goblin("Goblin B"));
                backupEnemies.add(new Goblin("Goblin C"));
                backupEnemies.add(new Wolf("Wolf A"));
                backupEnemies.add(new Wolf("Wolf B"));
                break;
            default:
                return false;
        }

        return true;
    }

    public Player getPlayer() {
        return player;
    }

    public int getRound() {
        return round;
    }

    public ArrayList<Combatant> getMainEnemies() {
        return mainEnemies;
    }

    public ArrayList<Combatant> getBackupEnemies() {
        return backupEnemies;
    }

    public void run() throws InterruptedException {
        int playerSelection = gameView.choosePlayerSelection();
        int levelSelection = gameView.chooseLevelSelection();
        run(playerSelection, levelSelection);
    }

    public void run(int playerSelection, int level) throws InterruptedException {
        round = 1;

        if (!selectPlayer(playerSelection)) {
            gameView.showInvalidPlayerSelection();
            return;
        }

        if (!selectLevel(level)) {
            gameView.showInvalidLevelSelection();
            return;
        }

        player.addItem(new PowerStone());
        player.addItem(new Potion());
        player.addItem(new SmokeBomb());

        Battle battle = new Battle(player, mainEnemies);
        ArrayList<CombatantTurnHandler> turnHandlers = new ArrayList<CombatantTurnHandler>();
        turnHandlers.add(new PlayerActionHandler(battleView, battleInputView));
        turnHandlers.add(new EnemyActionHandler(battleView));

        BattleEngine battleEngine = new BattleEngine(
                battle,
                new SpeedTurnOrderStrategy(),
                battleView,
                new StatusEffectManager(),
                turnHandlers);

        while (player.isAlive()) {
            gameView.showRoundHeader(round);

            if (round > 1) {
                battleEngine.updateRoundStatusEffects();
            }

            battleEngine.executeRound();

            if (!player.isAlive()) {
                gameView.showDefeat(player);
                break;
            }

            if (!battle.hasAliveEnemies()) {
                if (!backupEnemies.isEmpty()) {
                    gameView.showBackupEnemiesArrived();
                    battle.setEnemies(new ArrayList<Combatant>(backupEnemies));
                    backupEnemies.clear();
                } else {
                    gameView.showVictory();
                    break;
                }
            }

            round++;
        }
    }
}
