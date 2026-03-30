package controller.battle;

import model.BattleContext;
import model.Combatant;

public interface CombatantTurnHandler {
    void executeTurn(Combatant actor, BattleContext battle);
    boolean supports(Combatant actor);
}
