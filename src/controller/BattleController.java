package controller;

import java.util.ArrayList;

import controller.battle.EnemyActionHandler;
import controller.battle.PlayerActionHandler;
import controller.battle.StatusEffectManager;
import controller.strategy.TurnOrderStrategy;
import model.Battle;
import model.Entity;
import model.Player;
import view.BattleView;

public class BattleController {
    private final Battle battle;
    private final TurnOrderStrategy turnOrderStrategy;
    private final BattleView view;
    private final StatusEffectManager statusEffectManager;
    private final PlayerActionHandler playerActionHandler;
    private final EnemyActionHandler enemyActionHandler;

    public BattleController(Battle battle, TurnOrderStrategy turnOrderStrategy, BattleView view) {
        this.battle = battle;
        this.turnOrderStrategy = turnOrderStrategy;
        this.view = view;
        this.statusEffectManager = new StatusEffectManager();
        this.playerActionHandler = new PlayerActionHandler(view);
        this.enemyActionHandler = new EnemyActionHandler(view);
    }

    public void executeRound() throws InterruptedException {
        Player player = battle.getPlayer();
        ArrayList<Entity> enemies = battle.getEnemies();
        ArrayList<Entity> turnOrder = getTurnOrder(player, enemies);
        ArrayList<Entity> defeatedThisRound = new ArrayList<Entity>();

        for (Entity entity : turnOrder) {
            if (!entity.isAlive()) {
                continue;
            }

            if (battle.isBattleOver()) {
                break;
            }

            view.showTurnHeader(entity);

            if (entity instanceof Player) {
                ((Player) entity).reduceSpecialSkillCooldown();
            }

            if (statusEffectManager.handleTurnStartStatus(entity, view)) {
                continue;
            }

            view.showEntityAttributes(entity);

            if (entity instanceof Player) {
                playerActionHandler.executePlayerTurn((Player) entity, battle);
            } else {
                enemyActionHandler.executeEnemyTurn(entity, player);
            }

            announceDefeatedEnemies(enemies, defeatedThisRound);

            if (battle.isBattleOver()) {
                break;
            }

            Thread.sleep(1000);
        }

        battle.removeDefeatedEnemies();
    }

    public void updateRoundStatusEffects() {
        statusEffectManager.updateRoundStatusEffects(battle.getPlayer(), view);

        for (Entity enemy : battle.getEnemies()) {
            statusEffectManager.updateRoundStatusEffects(enemy, view);
        }
    }

    private ArrayList<Entity> getTurnOrder(Player player, ArrayList<Entity> enemies) {
        ArrayList<Entity> allCombatants = new ArrayList<Entity>();
        allCombatants.add(player);
        allCombatants.addAll(enemies);
        return turnOrderStrategy.determineTurnOrder(allCombatants);
    }

    private void announceDefeatedEnemies(ArrayList<Entity> enemies, ArrayList<Entity> defeatedThisRound) {
        for (Entity enemy : enemies) {
            if (!enemy.isAlive() && !defeatedThisRound.contains(enemy)) {
                view.showDefeated(enemy);
                defeatedThisRound.add(enemy);
            }
        }
    }
}
