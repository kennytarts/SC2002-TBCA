package controller.battle;

import model.battle.BattleContext;
import model.characters.Combatant;

public interface CombatantTurnHandler {
    void executeTurn(Combatant actor, BattleContext battle);
    boolean supports(Combatant actor);
}
