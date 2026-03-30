package controller.strategy;

import java.util.ArrayList;

import model.Entity;

public class SpeedTurnOrderStrategy implements TurnOrderStrategy {

    @Override
    public ArrayList<Entity> determineTurnOrder(ArrayList<Entity> combatants) {
        ArrayList<Entity> order = new ArrayList<Entity>(combatants);
        order.sort((a, b) -> Integer.compare(b.getSpd(), a.getSpd()));
        return order;
    }
}
