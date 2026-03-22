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
import engine.BattleEngine;
import engine.strategy.SpeedTurnOrderStrategy;
import view.GameCLI;

/**
 * GameController: Game-level orchestrator.
 * Responsible for: Setting up game, managing levels, deciding when battles
 * occur
 * NOT responsible for: Battle execution (that's BattleEngine)
 */
public class GameController {
    private Player player;
    private ArrayList<Entity> mainEnemies;
    private ArrayList<Entity> backupEnemies;
    private GameCLI cli;

    public GameController() {
        this.mainEnemies = new ArrayList<Entity>();
        this.backupEnemies = new ArrayList<Entity>();
        this.cli = new GameCLI();
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

    public ArrayList<Entity> getMainEnemies() {
        return mainEnemies;
    }

    public ArrayList<Entity> getBackupEnemies() {
        return backupEnemies;
    }

    public void run(int playerSelection, int levelSelection) throws InterruptedException {
        // Validate player selection
        if (!selectPlayer(playerSelection)) {
            cli.showInvalidPlayerSelection();
            return;
        }

        // Validate level selection
        if (!selectLevel(levelSelection)) {
            cli.showInvalidLevelSelection();
            return;
        }

        // Give player starting items
        player.addItem(new PowerStone());
        player.addItem(new Potion());
        player.addItem(new SmokeBomb());

        // Create battle and engine
        Battle battle = new Battle(player, mainEnemies);
        BattleEngine battleEngine = new BattleEngine(
                battle,
                new SpeedTurnOrderStrategy(),
                cli);

        int round = 1;

        // Main game loop
        while (player.isAlive()) {
            cli.showRoundHeader(round);

            // Update status durations at start of round (except first round)
            if (round > 1) {
                battleEngine.updateRoundStatusEffects();
            }

            // Execute round combat
            battleEngine.executeRound();

            // Check if player died
            if (!player.isAlive()) {
                cli.showDefeat(player);
                break;
            }

            // Check if all enemies defeated
            if (!battle.hasAliveEnemies()) {
                // Check if there are backup enemies to bring in
                if (!backupEnemies.isEmpty()) {
                    cli.showBackupEnemiesArrived();
                    battle.setEnemies(new ArrayList<Entity>(backupEnemies));
                    backupEnemies.clear();
                } else {
                    // All enemies defeated, game won!
                    cli.showVictory();
                    break;
                }
            }

            round++;
        }
    }
}