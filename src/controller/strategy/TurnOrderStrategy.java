package controller.strategy;

import java.util.ArrayList;

import model.Entity;

public interface TurnOrderStrategy {
    ArrayList<Entity> determineTurnOrder(ArrayList<Entity> combatants);
}
