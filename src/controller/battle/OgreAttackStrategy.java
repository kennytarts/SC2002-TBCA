package controller.battle;

import model.battle.BattleContext;
import model.characters.Combatant;
import model.items.Potion;
import model.status.Status;
import view.display.BattleDisplay;

public class OgreAttackStrategy extends EnemyActionStrategy {
    private boolean potionUsed = false;

    @Override
    protected void executeAction(Combatant enemy, Combatant player, BattleContext battle, BattleDisplay view) {
        if (!potionUsed && enemy.getHp() * 100 <= enemy.getMaxHp() * 25) {
            Potion potion = new Potion();
            potion.use(enemy, battle.getEnemies());
            potionUsed = true;
            view.showItemUsed(enemy, potion.getName());
            return;
        }

        int damage = enemy.basicAttack(player);
        view.showEnemyAttack(enemy, player, damage);

        // 30% chance to stun
        if (player.isAlive() && Math.random() < 0.3) {
            player.addStatus(Status.stun());
        }
    }
}
