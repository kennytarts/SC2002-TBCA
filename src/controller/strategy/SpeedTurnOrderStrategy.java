package controller.strategy;

import java.util.ArrayList;
import java.util.List;
import model.Entity;

/**
 * Concrete strategy: determines turn order based on combatant speed.
 * Higher speed = earlier turn (descending order)
 */
public class SpeedTurnOrderStrategy implements TurnOrderStrategy {

    @Override
    public List<Entity> determineTurnOrder(List<Entity> combatants) {
        List<Entity> order = new ArrayList<Entity>(combatants);

        // Sort by speed descending (higher speed goes first)
        order.sort((a, b) -> Integer.compare(b.getSpd(), a.getSpd()));

        return order;
    }
}
