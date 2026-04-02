package controller.battle.status;

import model.characters.Combatant;
import model.status.Status;
import model.status.StatusEffects;
import view.display.BattleDisplay;

public class StunStatusEffectHandler implements StatusEffectHandler {
    @Override
    public StatusEffects getEffect() {
        return StatusEffects.STUN;
    }

    @Override
    public boolean handleTurnStart(Combatant entity, Status status, BattleDisplay view) {
        view.showStunned(entity);
        status.decrementDuration();

        if (status.isExpired()) {
            entity.removeStatus(StatusEffects.STUN);
        }

        return true;
    }
}
