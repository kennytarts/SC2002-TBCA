package controller.battle;

import model.battle.BattleContext;
import model.characters.Combatant;
import model.characters.Enemy;
import model.characters.Player;
import view.display.BattleDisplay;

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
