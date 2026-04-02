package controller.battle.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.characters.Combatant;
import model.status.Status;
import view.display.BattleDisplay;

public class StatusEffectManager implements StatusEffectProcessor {
    private final Map<model.status.StatusEffects, StatusEffectHandler> handlers;

    public StatusEffectManager() {
        this.handlers = new HashMap<model.status.StatusEffects, StatusEffectHandler>();
        // Each effect is delegated to its own handler so new status behavior can
        // be added with minimal change to the manager itself
        register(new StunStatusEffectHandler());
        register(new DefendStatusEffectHandler());
        register(new InvulnerableStatusEffectHandler());
    }

    public void updateRoundStatusEffects(Combatant entity, BattleDisplay view) {
        if (!entity.isAlive()) {
            return;
        }

        for (Status status : new ArrayList<Status>(entity.getStatuses())) {
            StatusEffectHandler handler = handlers.get(status.getEffect());
            if (handler != null) {
                handler.updateRoundStatus(entity, status, view);
            }
        }
    }

    public boolean handleTurnStartStatus(Combatant entity, BattleDisplay view) {
        for (Status status : new ArrayList<Status>(entity.getStatuses())) {
            StatusEffectHandler handler = handlers.get(status.getEffect());
            if (handler != null && handler.handleTurnStart(entity, status, view)) {
                return true;
            }
        }

        return false;
    }

    public void handleTurnEndStatus(Combatant entity, BattleDisplay view) {
        if (!entity.isAlive()) {
            return;
        }

        for (Status status : new ArrayList<Status>(entity.getStatuses())) {
            StatusEffectHandler handler = handlers.get(status.getEffect());
            if (handler != null) {
                handler.handleTurnEnd(entity, status, view);
            }
        }
    }

    private void register(StatusEffectHandler handler) {
        handlers.put(handler.getEffect(), handler);
    }
}
