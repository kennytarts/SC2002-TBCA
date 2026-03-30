package controller.strategy;

import java.util.ArrayList;

import model.Combatant;

public interface TurnOrderStrategy {
    ArrayList<Combatant> determineTurnOrder(ArrayList<Combatant> combatants);
}
