package controller;

import java.util.ArrayList;

import controller.battle.CombatantTurnHandler;
import controller.battle.StatusEffectProcessor;
import controller.strategy.TurnOrderStrategy;
import model.BattleContext;
import model.Combatant;
import view.BattleDisplay;

public class BattleEngine {
    private final BattleContext battle;
    private final TurnOrderStrategy turnOrderStrategy;
    private final BattleDisplay view;
    private final StatusEffectProcessor statusEffectProcessor;
    private final ArrayList<CombatantTurnHandler> turnHandlers;

    public BattleEngine(BattleContext battle, TurnOrderStrategy turnOrderStrategy, BattleDisplay view,
            StatusEffectProcessor statusEffectProcessor, ArrayList<CombatantTurnHandler> turnHandlers) {
        this.battle = battle;
        this.turnOrderStrategy = turnOrderStrategy;
        this.view = view;
        this.statusEffectProcessor = statusEffectProcessor;
        this.turnHandlers = new ArrayList<CombatantTurnHandler>(turnHandlers);
    }

    public void executeRound() throws InterruptedException {
        ArrayList<Combatant> turnOrder = turnOrderStrategy.determineTurnOrder(battle.getCombatants());
        ArrayList<Combatant> defeatedThisRound = new ArrayList<Combatant>();

        for (Combatant combatant : turnOrder) {
            if (!combatant.isAlive()) {
                continue;
            }

            if (battle.isBattleOver()) {
                break;
            }

            view.showTurnHeader(combatant);

            boolean turnSkipped = statusEffectProcessor.handleTurnStartStatus(combatant, view);

            if (!turnSkipped) {
                view.showEntityAttributes(combatant);
                resolveTurnHandler(combatant).executeTurn(combatant, battle);
                announceDefeatedEnemies(defeatedThisRound);
            }

            statusEffectProcessor.handleTurnEndStatus(combatant, view);

            if (battle.isBattleOver()) {
                break;
            }

            Thread.sleep(1000);
        }

        battle.removeDefeatedEnemies();
    }

    public void updateRoundStatusEffects() {
        for (Combatant combatant : battle.getCombatants()) {
            statusEffectProcessor.updateRoundStatusEffects(combatant, view);
        }
    }

    private CombatantTurnHandler resolveTurnHandler(Combatant actor) {
        for (CombatantTurnHandler handler : turnHandlers) {
            if (handler.supports(actor)) {
                return handler;
            }
        }

        throw new IllegalStateException("No handler found for " + actor.getClass().getSimpleName());
    }

    private void announceDefeatedEnemies(ArrayList<Combatant> defeatedThisRound) {
        for (Combatant enemy : battle.getEnemies()) {
            if (!enemy.isAlive() && !defeatedThisRound.contains(enemy)) {
                view.showDefeated(enemy);
                defeatedThisRound.add(enemy);
            }
        }
    }
}
