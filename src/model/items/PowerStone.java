package model.items;

import java.util.ArrayList;

import model.characters.Combatant;

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
