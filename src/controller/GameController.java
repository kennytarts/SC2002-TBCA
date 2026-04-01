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
import model.Item;
import model.Player;
import model.Potion;
import model.PowerStone;
import model.SmokeBomb;
import model.Warrior;
import model.Wizard;
import model.Wolf;
import view.BattleDisplay;
import view.BattleInput;
import view.BattleInputView;
import view.BattleView;
import view.GameView;

public class GameController {
    private Player player;
    private final ArrayList<Combatant> mainEnemies;
    private final ArrayList<Combatant> backupEnemies;
    private int round = 1;
    private final GameView gameView;
    private final BattleDisplay battleDisplay;
    private final BattleInput battleInput;

    public GameController() {
        Scanner scanner = new Scanner(System.in);
        this.mainEnemies = new ArrayList<Combatant>();
        this.backupEnemies = new ArrayList<Combatant>();
        this.gameView = new GameView(scanner);
        this.battleDisplay = new BattleView();
        this.battleInput = new BattleInputView(scanner);
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

    public void selectItem(ArrayList<Item> items, int choices) {
        for (int i = 0; i < choices; i++) {
            int itemSelection = gameView.chooseItemsSelection(items);
            Item item = items.get(itemSelection);
            player.addItem(item.copy());
        }
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
        if (!selectPlayer(playerSelection)) {
            gameView.showInvalidPlayerSelection();
            return;
        }

        ArrayList<Item> items = new ArrayList<>();
        items.add(new PowerStone());
        items.add(new Potion());
        items.add(new SmokeBomb());
        selectItem(items, 2);

        int levelSelection = gameView.chooseLevelSelection();
        if (!selectLevel(levelSelection)) {
            gameView.showInvalidLevelSelection();
            return;
        }


        Battle battle = new Battle(player, mainEnemies);
        ArrayList<CombatantTurnHandler> turnHandlers = new ArrayList<CombatantTurnHandler>();
        turnHandlers.add(new PlayerActionHandler(battleDisplay, battleInput));
        turnHandlers.add(new EnemyActionHandler(battleDisplay));

        BattleEngine battleEngine = new BattleEngine(
                battle,
                new SpeedTurnOrderStrategy(),
                battleDisplay,
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
