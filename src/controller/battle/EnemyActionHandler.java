package controller.battle;

import model.Entity;
import model.Player;
import model.StatusEffects;
import view.BattleView;

/**
 * Handles enemy-side battle actions.
 */
public class EnemyActionHandler {
    private BattleView view;

    public EnemyActionHandler(BattleView view) {
        this.view = view;
    }

    public void executeEnemyTurn(Entity enemy, Player player) {
        if (!player.isAlive()) {
            return;
        }

        if (player.hasStatus(StatusEffects.INVULNERABLE)) {
            view.showEnemyInvulnerableBlocked(player, enemy);
            return;
        }

        int damage = enemy.basicAttack(player);
        view.showEnemyAttack(enemy, player, damage);
    }
}