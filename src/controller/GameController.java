package controller;

import java.util.ArrayList;
import java.util.Scanner;

import controller.battle.CombatantTurnHandler;
import controller.battle.PlayerActionHandler;
import controller.battle.enemy.EnemyActionHandler;
import controller.battle.status.StatusEffectManager;
import controller.setup.EntityDataSyncService;
import controller.setup.GameSetup;
import controller.setup.ItemResolver;
import controller.setup.LevelResolver;
import controller.setup.PlayerResolver;
import controller.strategy.SpeedTurnOrderStrategy;
import model.battle.Battle;
import model.characters.Combatant;
import model.characters.Player;
import model.items.Item;
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
    private final ItemResolver itemResolver;
    private final EntityDataSyncService entityDataSyncService;

    public GameController() {
        Scanner scanner = new Scanner(System.in);
        this.mainEnemies = new ArrayList<Combatant>();
        this.backupEnemies = new ArrayList<Combatant>();
        this.gameView = new GameView(scanner);
        this.battleDisplay = new BattleView();
        this.battleInput = new BattleInputView(scanner);
        this.playerResolver = new PlayerResolver();
        this.levelResolver = new LevelResolver();
        this.itemResolver = new ItemResolver();
        this.entityDataSyncService = new EntityDataSyncService();
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
        entityDataSyncService.syncEntityData();
        if (!entityDataSyncService.areAllEntityFilesPresent()) {
            gameView.showEntityDataLoadError();
            return;
        }

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

        ArrayList<String> itemNames = itemResolver.getItemNames();
        ArrayList<Player> players = createPreviewPlayers();
        ArrayList<Combatant> enemies = createPreviewEnemies();
        ArrayList<String> levels = levelResolver.getLevelDescriptions();

        gameView.showLoadingScreenHeader();
        gameView.showPlayersSection(players);
        int playerSelection = gameView.choosePlayerSelection(players);

        gameView.showItemsSection(itemNames);
        ArrayList<Integer> itemSelections = new ArrayList<Integer>();
        for (int i = 0; i < 2; i++) {
            itemSelections.add(gameView.chooseItemsSelection(itemNames, i + 1));
        }

        gameView.showEnemiesSection(enemies);
        gameView.showLevelsSection(levels);
        int levelSelection = gameView.chooseLevelSelection(levels);
        return new GameSetup(playerSelection, itemSelections, levelSelection);
    }

    private void playGame(GameSetup setup) throws InterruptedException {
        round = 1;
        selectPlayer(setup.getPlayerSelection());

        player.getItems().clear();
        for (int itemSelection : setup.getItemSelections()) {
            Item item = itemResolver.resolveItem(itemSelection);
            if (item != null) {
                player.addItem(item);
            }
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

    private ArrayList<Player> createPreviewPlayers() {
        return playerResolver.getPlayerOptions();
    }

    private ArrayList<Combatant> createPreviewEnemies() {
        return levelResolver.getEnemyPreviews();
    }
}
