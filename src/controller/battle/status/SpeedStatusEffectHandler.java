package controller.battle.status;

import model.characters.Combatant;
import model.status.Status;
import model.status.StatusEffects;
import view.display.BattleDisplay;

public class SpeedStatusEffectHandler implements StatusEffectHandler {
    @Override
    public StatusEffects getEffect() {
        return StatusEffects.SPEED;
    }

    @Override
    public void updateRoundStatus(Combatant entity, Status status, BattleDisplay view) {
        status.decrementDuration();
        if (status.isExpired()) {
            entity.changeSpeed(-15);
            entity.removeStatus(StatusEffects.SPEED);
            // view.showDefendWoreOff(entity);
        }
    }
}
