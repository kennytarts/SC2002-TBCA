package engine.strategy;

import java.util.List;
import model.Entity;

/**
 * Strategy pattern for determining turn order in battles.
 * New strategies can be added without modifying battle logic.
 */
public interface TurnOrderStrategy {
    /**
     * Determines the order in which combatants take turns.
     * 
     * @param combatants List of all alive combatants
     * @return Sorted list of combatants in turn order
     */
    List<Entity> determineTurnOrder(List<Entity> combatants);
}
