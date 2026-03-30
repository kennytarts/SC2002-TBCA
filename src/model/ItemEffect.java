package model;

import java.util.ArrayList;

public interface ItemEffect {
    String getName();
    boolean isConsumed();
    void use(Combatant user, ArrayList<Combatant> enemies);
}
