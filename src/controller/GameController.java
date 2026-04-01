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
import model.LevelConfig;
import model.Player;
import model.Potion;
import model.PowerStone;
import model.SmokeBomb;
import model.EasyMode;
import model.MediumMode;
import model.HardMode;
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
        LevelConfig selectedLevel;

        switch (level) {
            case 1:
                selectedLevel = new EasyMode();
                break;
            case 2:
                selectedLevel = new MediumMode();
                break;
            case 3:
                selectedLevel = new HardMode();
                break;
            default:
                return false;
        }

        mainEnemies.addAll(selectedLevel.createInitialEnemies());
        backupEnemies.addAll(selectedLevel.createBackupEnemies());
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
        Integer savedPlayerSelection = null;
        ArrayList<Integer> savedItemSelections = new ArrayList<Integer>();
        Integer savedLevelSelection = null;
        boolean useSavedSetup = false;

        while (true) {
            ArrayList<Item> items = new ArrayList<Item>();
            items.add(new PowerStone());
            items.add(new Potion());
            items.add(new SmokeBomb());

            if (!useSavedSetup) {
                ArrayList<Player> players = new ArrayList<Player>();
                players.add(new Warrior());
                players.add(new Wizard());

                ArrayList<Combatant> enemies = new ArrayList<Combatant>();
                enemies.add(new Goblin());
                enemies.add(new Wolf());

                gameView.showLoadingScreen(players, items, enemies);

                savedPlayerSelection = gameView.choosePlayerSelection();
                savedItemSelections.clear();
                for (int i = 0; i < 2; i++) {
                    savedItemSelections.add(gameView.chooseItemsSelection(items));
                }
                savedLevelSelection = gameView.chooseLevelSelection();
            }

            round = 1;
            if (!selectPlayer(savedPlayerSelection)) {
                gameView.showInvalidPlayerSelection();
                return;
            }

            player.getItems().clear();
            for (int itemSelection : savedItemSelections) {
                player.addItem(items.get(itemSelection).copy());
            }

            if (!selectLevel(savedLevelSelection)) {
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
                gameView.showRoundSummary(player, battle.getEnemies());

                if (!player.isAlive()) {
                    int enemiesRemaining = backupEnemies.size();
                    for (Combatant enemy : battle.getEnemies()) {
                        if (enemy.isAlive()) {
                            enemiesRemaining++;
                        }
                    }
                    gameView.showDefeat(enemiesRemaining, round);
                    break;
                }

                if (!battle.hasAliveEnemies()) {
                    if (!backupEnemies.isEmpty()) {
                        gameView.showBackupEnemiesArrived();
                        battle.setEnemies(new ArrayList<Combatant>(backupEnemies));
                        backupEnemies.clear();
                    } else {
                        gameView.showVictory(player, round);
                        break;
                    }
                }

                round++;
            }

            int postGameChoice = gameView.choosePostGameOption();
            if (postGameChoice == 1) {
                useSavedSetup = true;
            } else if (postGameChoice == 2) {
                useSavedSetup = false;
            } else {
                return;
            }
        }
    }
}
