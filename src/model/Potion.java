package model;

import java.util.ArrayList;

public class Potion extends Item {
    public Potion() {
        super("Potion");
    }

    @Override
    public Item copy() {
        return new Potion();
    }

    public void use(Combatant user, ArrayList<Combatant> enemies) {
        user.heal(100);
        consume();
    }
}
