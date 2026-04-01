package controller.battle;

import model.BattleContext;
import model.Combatant;
import model.Enemy;
import model.Player;
import view.BattleDisplay;

public class EnemyActionHandler implements CombatantTurnHandler {
    private final BattleDisplay view;

    public EnemyActionHandler(BattleDisplay view) {
        this.view = view;
    }

    public void executeTurn(Combatant enemy, BattleContext battle) {
        Enemy actingEnemy = (Enemy) enemy;
        actingEnemy.getActionStrategy().execute(actingEnemy, battle, view);
    }

    public boolean supports(Combatant actor) {
        return !(actor instanceof Player);
    }
}
