package controller.battle;

import model.battle.BattleContext;
import model.characters.Combatant;
import view.display.BattleDisplay;

public interface EnemyActionStrategy {
    void execute(Combatant enemy, BattleContext battle, BattleDisplay view);
}
