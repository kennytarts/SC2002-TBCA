package controller.battle.status;

import model.characters.Combatant;
import model.status.Status;
import model.status.StatusEffects;
import view.display.BattleDisplay;

public class InvulnerableStatusEffectHandler implements StatusEffectHandler {
    @Override
    public StatusEffects getEffect() {
        return StatusEffects.INVULNERABLE;
    }

    @Override
    public void handleTurnEnd(Combatant entity, Status status, BattleDisplay view) {
        status.decrementDuration();

        if (status.isExpired()) {
            entity.removeStatus(StatusEffects.INVULNERABLE);
            view.showNoLongerInvulnerable(entity);
        }
    }
}
