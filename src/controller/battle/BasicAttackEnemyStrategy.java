package controller.battle;

import model.battle.BattleContext;
import model.characters.Combatant;
import model.status.StatusEffects;
import view.display.BattleDisplay;

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
