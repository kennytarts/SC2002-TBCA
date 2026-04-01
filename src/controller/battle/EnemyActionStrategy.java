package controller.battle;

import model.BattleContext;
import model.Combatant;
import view.BattleDisplay;

public interface EnemyActionStrategy {
    void execute(Combatant enemy, BattleContext battle, BattleDisplay view);
}
