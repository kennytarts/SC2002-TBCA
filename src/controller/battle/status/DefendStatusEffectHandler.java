package controller.battle.status;

import model.characters.Combatant;
import model.status.Status;
import model.status.StatusEffects;
import view.display.BattleDisplay;

public class DefendStatusEffectHandler implements StatusEffectHandler {
    @Override
    public StatusEffects getEffect() {
        return StatusEffects.DEFEND;
    }

    @Override
    public void updateRoundStatus(Combatant entity, Status status, BattleDisplay view) {
        status.decrementDuration();

        if (status.isExpired()) {
            entity.changeDefense(-10);
            entity.removeStatus(StatusEffects.DEFEND);
            view.showDefendWoreOff(entity);
        }
    }
}
