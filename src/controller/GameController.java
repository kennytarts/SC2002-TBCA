package controller;

import java.util.ArrayList;
import java.util.Scanner;

import controller.battle.CombatantTurnHandler;
import controller.battle.EnemyActionHandler;
import controller.battle.PlayerActionHandler;
import controller.battle.StatusEffectManager;
import controller.strategy.SpeedTurnOrderStrategy;
import model.battle.Battle;
import model.characters.Combatant;
import model.characters.Player;
import model.characters.enemies.Goblin;
import model.characters.enemies.Wolf;
import model.characters.players.Warrior;
import model.characters.players.Wizard;
import model.items.Item;
import model.items.Potion;
import model.items.PowerStone;
import model.items.SmokeBomb;
import model.levels.EasyMode;
import model.levels.HardMode;
import model.levels.LevelConfig;
import model.levels.MediumMode;
import view.display.BattleDisplay;
import view.display.BattleView;
import view.display.GameView;
import view.input.BattleInput;
import view.input.BattleInputView;

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
            selectPlayer(savedPlayerSelection);

            player.getItems().clear();
            for (int itemSelection : savedItemSelections) {
                player.addItem(items.get(itemSelection).copy());
            }

            selectLevel(savedLevelSelection);

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
