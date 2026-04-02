package controller.battle.status;

import model.characters.Combatant;
import model.status.Status;
import model.status.StatusEffects;
import view.display.BattleDisplay;

public interface StatusEffectHandler {
    StatusEffects getEffect();

    default void updateRoundStatus(Combatant entity, Status status, BattleDisplay view) {
    }

    default boolean handleTurnStart(Combatant entity, Status status, BattleDisplay view) {
        return false;
    }

    default void handleTurnEnd(Combatant entity, Status status, BattleDisplay view) {
    }
}
