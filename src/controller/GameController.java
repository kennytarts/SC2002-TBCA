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
import model.levels.LevelConfig;
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
    private final PlayerResolver playerResolver;
    private final LevelResolver levelResolver;

    public GameController() {
        Scanner scanner = new Scanner(System.in);
        this.mainEnemies = new ArrayList<Combatant>();
        this.backupEnemies = new ArrayList<Combatant>();
        this.gameView = new GameView(scanner);
        this.battleDisplay = new BattleView();
        this.battleInput = new BattleInputView(scanner);
        this.playerResolver = new PlayerResolver();
        this.levelResolver = new LevelResolver();
    }

    public boolean selectPlayer(int selection) {
        Player selectedPlayer = playerResolver.resolvePlayer(selection);
        if (selectedPlayer == null) {
            return false;
        }

        player = selectedPlayer;
        return true;
    }

    public boolean selectLevel(int level) {
        mainEnemies.clear();
        backupEnemies.clear();
        LevelConfig selectedLevel = levelResolver.resolveLevel(level);
        if (selectedLevel == null) {
            return false;
        }

        mainEnemies.addAll(selectedLevel.createInitialEnemies());
        backupEnemies.addAll(selectedLevel.createBackupEnemies());
        return true;
    }

    public void run() throws InterruptedException {
        GameSetup savedSetup = null;
        boolean useSavedSetup = false;

        while (true) {
            GameSetup setup = getSetup(useSavedSetup, savedSetup);
            savedSetup = setup;
            playGame(setup);

            int postGameChoice = handlePostGame();
            if (postGameChoice == 1) {
                useSavedSetup = true;
            } else if (postGameChoice == 2) {
                useSavedSetup = false;
            } else {
                return;
            }
        }
    }

    private GameSetup getSetup(boolean useSavedSetup, GameSetup savedSetup) {
        if (useSavedSetup && savedSetup != null) {
            return savedSetup;
        }

        ArrayList<Item> items = createAvailableItems();
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(new Warrior());
        players.add(new Wizard());

        ArrayList<Combatant> enemies = new ArrayList<Combatant>();
        enemies.add(new Goblin());
        enemies.add(new Wolf());

        gameView.showLoadingScreen(players, items, enemies);

        int playerSelection = gameView.choosePlayerSelection();
        ArrayList<Integer> itemSelections = new ArrayList<Integer>();
        for (int i = 0; i < 2; i++) {
            itemSelections.add(gameView.chooseItemsSelection(items));
        }

        int levelSelection = gameView.chooseLevelSelection();
        return new GameSetup(playerSelection, itemSelections, levelSelection);
    }

    private void playGame(GameSetup setup) throws InterruptedException {
        ArrayList<Item> items = createAvailableItems();

        round = 1;
        selectPlayer(setup.getPlayerSelection());

        player.getItems().clear();
        for (int itemSelection : setup.getItemSelections()) {
            player.addItem(items.get(itemSelection).copy());
        }

        selectLevel(setup.getLevelSelection());

        Battle battle = new Battle(player, mainEnemies);
        BattleEngine battleEngine = createBattleEngine(battle);

        while (player.isAlive()) {
            gameView.showRoundHeader(round);

            if (round > 1) {
                battleEngine.updateRoundStatusEffects();
            }

            battleEngine.executeRound();
            gameView.showRoundSummary(player, battle.getEnemies());

            if (!player.isAlive()) {
                showDefeatSummary(battle);
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
    }

    private BattleEngine createBattleEngine(Battle battle) {
        ArrayList<CombatantTurnHandler> turnHandlers = new ArrayList<CombatantTurnHandler>();
        turnHandlers.add(new PlayerActionHandler(battleDisplay, battleInput));
        turnHandlers.add(new EnemyActionHandler(battleDisplay));

        return new BattleEngine(
                battle,
                new SpeedTurnOrderStrategy(),
                battleDisplay,
                new StatusEffectManager(),
                turnHandlers);
    }

    private void showDefeatSummary(Battle battle) {
        int enemiesRemaining = backupEnemies.size();
        for (Combatant enemy : battle.getEnemies()) {
            if (enemy.isAlive()) {
                enemiesRemaining++;
            }
        }
        gameView.showDefeat(enemiesRemaining, round);
    }

    private int handlePostGame() {
        return gameView.choosePostGameOption();
    }

    private ArrayList<Item> createAvailableItems() {
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new PowerStone());
        items.add(new Potion());
        items.add(new SmokeBomb());
        return items;
    }
}
