package controller.battle;

import model.battle.BattleContext;
import model.characters.Combatant;
import model.status.Status;
import view.display.BattleDisplay;

public class OgreAttackStrategy extends EnemyActionStrategy {
    @Override
    protected void executeAction(Combatant enemy, Combatant player, BattleContext battle, BattleDisplay view) {
        int damage = enemy.basicAttack(player);
        view.showEnemyAttack(enemy, player, damage);

        // 30% chance to stun
        if (player.isAlive() && Math.random() < 0.3) {
            player.addStatus(Status.stun());
        }
    }
}
