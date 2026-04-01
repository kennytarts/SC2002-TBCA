package controller.strategy;

import java.util.ArrayList;

import model.characters.Combatant;

public interface TurnOrderStrategy {
    ArrayList<Combatant> determineTurnOrder(ArrayList<Combatant> combatants);
}
