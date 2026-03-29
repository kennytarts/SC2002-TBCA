package controller;

import java.util.ArrayList;
import java.util.List;

import engine.Battle;
import engine.BattleEngine;
import engine.strategy.SpeedTurnOrderStrategy;
import model.Enemy;
import model.Goblin;
import model.Item;
import model.Player;
import model.Potion;
import model.PowerStone;
import model.SmokeBomb;
import model.Warrior;
import model.Wizard;
import model.Wolf;
import view.GameCLI;

public class GameController {
    private GameCLI cli;
    private BattleEngine battleEngine;
    private Battle currentBattle;
    private int currentLevel;
    private List<Enemy> mainEnemies;
    private List<Enemy> backupEnemies;

    public GameController() {
        this.cli = new GameCLI();
        this.mainEnemies = new ArrayList<>();
        this.backupEnemies = new ArrayList<>();
    }
    public void startGame() throws InterruptedException {
        cli.showWelcome();
        boolean keepPlaying = true;
        while (keepPlaying) {
            Player player = setupPlayer();
            setupItems(player);
            currentLevel = cli.chooseLevel();
            currentBattle = setupBattle(currentLevel, player);
            battleEngine = new BattleEngine(currentBattle, new SpeedTurnOrderStrategy(), cli);
            runGameLoop(player);
            keepPlaying = handlePostGame();
        }
        cli.showMessage("Thanks for playing. Goodbye!");
    }

    public Player setupPlayer() {
        cli.showMainMenu();
        int choice = cli.choosePlayerClass();
        switch (choice) {
            case 1: return new Warrior();
            case 2: return new Wizard();
            default:
                cli.showMessage("Invalid choice. Defaulting to Warrior.");
                return new Warrior();
        }
    }

    public Battle setupBattle(int level, Player player) {
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
                cli.showMessage("Invalid level. Defaulting to Easy.");
                mainEnemies.add(new Goblin("Goblin A"));
                mainEnemies.add(new Goblin("Goblin B"));
                mainEnemies.add(new Goblin("Goblin C"));
                break;
        }
        return new Battle(player, new ArrayList<>(mainEnemies));
    }

    public void runGameLoop(Player player) throws InterruptedException {
        int round = 1;
        while (!isGameOver(player)) {
            cli.showRoundHeader(round);
            if (round > 1) {
                battleEngine.updateRoundStatusEffects();
            }
            handleBattle();
            if (!player.isAlive()) {
                int enemiesLeft = countAliveEnemies();
                cli.showDefeat(player, enemiesLeft, round);
                return;
            }
            if (!currentBattle.hasAliveEnemies()) {
                if (!backupEnemies.isEmpty()) {
                    cli.showBackupEnemiesArrived();
                    currentBattle.setEnemies(new ArrayList<>(backupEnemies));
                    backupEnemies.clear();
                } else {
                    cli.showVictory(player, round);
                    return;
                }
            }
            round++;
        }
    }

    public void handleBattle() throws InterruptedException {
        battleEngine.executeRound();
    }
  
    public boolean isGameOver(Player player) {
        if (!player.isAlive()) return true;
        if (!currentBattle.hasAliveEnemies() && backupEnemies.isEmpty()) return true;
        return false;
    }
  
    private void setupItems(Player player) {
        cli.showMessage("\n=== Choose 2 items (duplicates allowed) ===");
        cli.showMessage("1. Potion       - Heal 100 HP");
        cli.showMessage("2. Power Stone  - Free extra use of Special Skill");
        cli.showMessage("3. Smoke Bomb   - Enemy attacks deal 0 damage for 2 turns");

        for (int i = 1; i <= 2; i++) {
            cli.showMessage("\nPick item " + i + ":");
            int choice = cli.chooseAction();
            switch (choice) {
                case 1:
                    player.addItem(new Potion());
                    cli.showMessage("Added: Potion");
                    break;
                case 2:
                    player.addItem(new PowerStone());
                    cli.showMessage("Added: Power Stone");
                    break;
                case 3:
                    player.addItem(new SmokeBomb());
                    cli.showMessage("Added: Smoke Bomb");
                    break;
                default:
                    player.addItem(new Potion());
                    cli.showMessage("Invalid choice. Added: Potion");
                    break;
            }
        }
    }

    private boolean handlePostGame() {
        cli.showMessage("\n1. Replay with same settings");
        cli.showMessage("2. Start a new game");
        cli.showMessage("3. Exit");
        int choice = cli.chooseAction();
        return choice == 1 || choice == 2;
    }

    private int countAliveEnemies() {
        int count = 0;
        for (Enemy e : currentBattle.getEnemies()) {
            if (e.isAlive()) count++;
        }
        return count;
    }
}
