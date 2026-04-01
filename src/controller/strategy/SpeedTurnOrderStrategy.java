package controller.strategy;

import java.util.ArrayList;

import model.Combatant;

public class SpeedTurnOrderStrategy implements TurnOrderStrategy {

    @Override
    public ArrayList<Combatant> determineTurnOrder(ArrayList<Combatant> combatants) {
        ArrayList<Combatant> order = new ArrayList<Combatant>(combatants);
        order.sort((a, b) -> Integer.compare(b.getSpd(), a.getSpd()));
        return order;
    }
}
