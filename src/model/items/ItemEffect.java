package model.items;

import java.util.ArrayList;

import model.characters.Combatant;

public interface ItemEffect {
    String getName();
    boolean isConsumed();
    void use(Combatant user, ArrayList<Combatant> enemies);
}
