package controller.battle.status;

import model.characters.Combatant;
import view.display.BattleDisplay;

public interface StatusEffectProcessor {
    void updateRoundStatusEffects(Combatant entity, BattleDisplay view);
    boolean handleTurnStartStatus(Combatant entity, BattleDisplay view);
    void handleTurnEndStatus(Combatant entity, BattleDisplay view);
}
