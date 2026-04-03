package controller.battle;

import model.battle.BattleContext;
import model.characters.Combatant;
import view.display.BattleDisplay;

public abstract class EnemyActionStrategy {
    public final void execute(Combatant enemy, BattleContext battle, BattleDisplay view) {
        Combatant player = battle.getPlayer();

        if (!player.isAlive()) {
            return;
        }

        executeAction(enemy, player, battle, view);
    }

    protected abstract void executeAction(Combatant enemy, Combatant player, BattleContext battle,
            BattleDisplay view);
}
