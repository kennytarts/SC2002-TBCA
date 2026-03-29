package controller;

import java.util.ArrayList;

import model.Entity;
import model.Goblin;
import model.Player;
import model.Potion;
import model.PowerStone;
import model.SmokeBomb;
import model.Warrior;
import model.Wizard;
import model.Wolf;
import model.Battle;
import controller.strategy.SpeedTurnOrderStrategy;
import view.BattleView;
import view.GameView;

public class GameController {
    private Player player;
    private ArrayList<Entity> mainEnemies;
    private ArrayList<Entity> backupEnemies;
    private int round = 1;
    private GameView gameView;
    private BattleView battleView;

    public GameController() {
        this.mainEnemies = new ArrayList<Entity>();
        this.backupEnemies = new ArrayList<Entity>();
        this.gameView = new GameView();
        this.battleView = new BattleView();
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

    public ArrayList<Entity> getMainEnemies() {
        return mainEnemies;
    }

    public ArrayList<Entity> getBackupEnemies() {
        return backupEnemies;
    }

    public void run(int playerSelection, int level) throws InterruptedException {
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

        // Create battle and engine
        Battle battle = new Battle(player, mainEnemies);
        BattleEngine battleEngine = new BattleEngine(battle, new SpeedTurnOrderStrategy(), battleView);

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
                    battle.setEnemies(new ArrayList<Entity>(backupEnemies));
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