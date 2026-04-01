package controller.battle;

import model.status.StatusAffected;
import model.status.Status;
import model.status.StatusEffects;
import view.display.BattleDisplay;

public class StatusEffectManager implements StatusEffectProcessor {

    public void updateRoundStatusEffects(StatusAffected entity, BattleDisplay view) {
        if (!entity.isAlive()) {
            return;
        }

        updateDefend(entity, view);
    }

    public boolean handleTurnStartStatus(StatusAffected entity, BattleDisplay view) {
        Status stun = entity.getStatus(StatusEffects.STUN);

        if (stun == null) {
            return false;
        }

        view.showStunned(entity);
        stun.decrementDuration();

        if (stun.isExpired()) {
            entity.removeStatus(StatusEffects.STUN);
        }

        return true;
    }

    public void handleTurnEndStatus(StatusAffected entity, BattleDisplay view) {
        if (!entity.isAlive()) {
            return;
        }

        updateInvulnerable(entity, view);
    }

    private void updateDefend(StatusAffected entity, BattleDisplay view) {
        Status defend = entity.getStatus(StatusEffects.DEFEND);

        if (defend != null) {
            defend.decrementDuration();

            if (defend.isExpired()) {
                entity.changeDefense(-10);
                entity.removeStatus(StatusEffects.DEFEND);
                view.showDefendWoreOff(entity);
            }
        }
    }

    private void updateInvulnerable(StatusAffected entity, BattleDisplay view) {
        Status invulnerable = entity.getStatus(StatusEffects.INVULNERABLE);

        if (invulnerable != null) {
            invulnerable.decrementDuration();

            if (invulnerable.isExpired()) {
                entity.removeStatus(StatusEffects.INVULNERABLE);
                view.showNoLongerInvulnerable(entity);
            }
        }
    }
}
