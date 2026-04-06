package controller.battle.enemy;

import model.battle.BattleContext;
import model.characters.Combatant;
import view.display.BattleDisplay;

public class BasicAttackEnemyStrategy extends EnemyActionStrategy {
    @Override
    protected void executeAction(Combatant enemy, Combatant player, BattleContext battle, BattleDisplay view) {
        int damage = enemy.basicAttack(player);
        view.showEnemyAttack(enemy, player, damage);
    }
}
