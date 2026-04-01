package controller.battle;

import model.StatusAffected;
import view.BattleDisplay;

public interface StatusEffectProcessor {
    void updateRoundStatusEffects(StatusAffected entity, BattleDisplay view);
    boolean handleTurnStartStatus(StatusAffected entity, BattleDisplay view);
    void handleTurnEndStatus(StatusAffected entity, BattleDisplay view);
}
