package controller.battle;

import model.BattleContext;
import model.Combatant;
import model.StatusEffects;
import view.BattleDisplay;

public class EnemyActionHandler implements CombatantTurnHandler {
    private final BattleDisplay view;

    public EnemyActionHandler(BattleDisplay view) {
        this.view = view;
    }

    public void executeTurn(Combatant enemy, BattleContext battle) {
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

    public boolean supports(Combatant actor) {
        return !(actor instanceof model.Player);
    }
}
