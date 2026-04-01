package model;

import java.util.ArrayList;

public class PowerStone extends Item {
    public PowerStone() {
        super("Power Stone");
    }

    @Override
    public Item copy() {
        return new PowerStone();
    }

    public void use(Combatant user, ArrayList<Combatant> enemies) {
        consume();
    }
}
