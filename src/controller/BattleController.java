package controller;

import java.util.ArrayList;

import model.Entity;
import model.Player;
import model.Battle;
import engine.BattleEngine;
import engine.strategy.SpeedTurnOrderStrategy;
import view.GameCLI;

/**
 * BattleController: Thin wrapper for backward compatibility.
 * 
 * RECOMMENDED: Use BattleEngine directly instead.
 * This class is maintained for compatibility with old code patterns only.
 * 
 * New code should create Battle + BattleEngine directly.
 */
@Deprecated
public class BattleController {
    private Battle battle;
    private BattleEngine engine;
    private GameCLI view;

    public BattleController(Player player, ArrayList<Entity> enemies, GameCLI view) {
        this.battle = new Battle(player, enemies);
        this.engine = new BattleEngine(battle, new SpeedTurnOrderStrategy(), view);
        this.view = view;
    }

    public void executeRound() throws InterruptedException {
        engine.executeRound();
    }

    public void updateRoundStatusesAtStartOfRound() {
        engine.updateRoundStatusEffects();
    }

    public boolean enemiesRemaining() {
        return battle.hasAliveEnemies();
    }

    public void setEnemies(ArrayList<Entity> enemies) {
        battle.setEnemies(enemies);
    }

    public ArrayList<Entity> getEnemies() {
        return battle.getEnemies();
    }

    public Battle getBattle() {
        return battle;
    }

    public BattleEngine getEngine() {
        return engine;
    }
}