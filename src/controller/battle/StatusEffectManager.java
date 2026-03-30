package controller.battle;

import model.Entity;
import model.Status;
import model.StatusEffects;
import view.BattleView;

public class StatusEffectManager {

    public void updateRoundStatusEffects(Entity entity, BattleView view) {
        if (!entity.isAlive()) {
            return;
        }

        updateDefend(entity, view);
        updateInvulnerable(entity, view);
    }

    public boolean handleTurnStartStatus(Entity entity, BattleView view) {
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

    private void updateDefend(Entity entity, BattleView view) {
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

    private void updateInvulnerable(Entity entity, BattleView view) {
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
