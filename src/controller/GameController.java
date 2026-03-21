package controller;

import java.util.ArrayList;

import model.Entity;
import model.Goblin;
import model.Player;
import model.Warrior;
import model.Wizard;
import model.Wolf;
import view.BattleView;
import view.GameView;

public class GameController {
    private Player player;
    private ArrayList<Entity> mainEnemies;
    private ArrayList<Entity> backupEnemies;
    private int round = 1;
    private GameView view;

    public GameController() {
        this.mainEnemies = new ArrayList<Entity>();
        this.backupEnemies = new ArrayList<Entity>();
        this.view = new GameView();
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
                mainEnemies.add(new Goblin());
                mainEnemies.add(new Goblin());
                mainEnemies.add(new Goblin());
                break;

            case 2:
                mainEnemies.add(new Goblin());
                mainEnemies.add(new Wolf());

                backupEnemies.add(new Wolf());
                backupEnemies.add(new Wolf());
                break;

            case 3:
                mainEnemies.add(new Goblin());
                mainEnemies.add(new Goblin());

                backupEnemies.add(new Goblin());
                backupEnemies.add(new Wolf());
                backupEnemies.add(new Wolf());
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
            view.showInvalidPlayerSelection();
            return;
        }

        if (!selectLevel(level)) {
            view.showInvalidLevelSelection();
            return;
        }

        BattleView battleView = new BattleView();
        BattleController battleController = new BattleController(player, mainEnemies, battleView);

        while (player.isAlive()) {
            view.showRoundHeader(round);

            if (round > 1) {
                battleController.updateDefendStatusesAtStartOfRound();
            }

            battleController.executeRound();

            if (!player.isAlive()) {
                view.showDefeat(player);
                break;
            }

            if (!battleController.enemiesRemaining()) {
                if (!backupEnemies.isEmpty()) {
                    view.showBackupEnemiesArrived();
                    battleController.setEnemies(new ArrayList<Entity>(backupEnemies));
                    backupEnemies.clear();
                } else {
                    view.showVictory();
                    break;
                }
            }

            round++;
        }
    }
}