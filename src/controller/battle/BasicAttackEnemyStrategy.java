package controller.battle;

import model.BattleContext;
import model.Combatant;
import model.StatusEffects;
import view.BattleDisplay;

public class BasicAttackEnemyStrategy implements EnemyActionStrategy {
    @Override
    public void execute(Combatant enemy, BattleContext battle, BattleDisplay view) {
        Combatant player = battle.getPlayer();

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
