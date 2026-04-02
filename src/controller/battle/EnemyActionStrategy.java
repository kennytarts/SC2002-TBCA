package controller.battle;

import model.battle.BattleContext;
import model.characters.Combatant;
import model.status.StatusEffects;
import view.display.BattleDisplay;

public abstract class EnemyActionStrategy {
    public final void execute(Combatant enemy, BattleContext battle, BattleDisplay view) {
        Combatant player = battle.getPlayer();

        if (!player.isAlive()) {
            return;
        }

        if (player.hasStatus(StatusEffects.INVULNERABLE)) {
            view.showEnemyInvulnerableBlocked(player, enemy);
            return;
        }

        executeAction(enemy, player, battle, view);
    }

    protected abstract void executeAction(Combatant enemy, Combatant player, BattleContext battle,
            BattleDisplay view);
}
