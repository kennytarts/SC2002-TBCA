package model.items;

import java.util.ArrayList;

import model.characters.Combatant;
import model.status.Status;

public class SmokeBomb extends Item {
    public SmokeBomb() {
        super("Smoke Bomb");
    }

    @Override
    public Item copy() {
        return new SmokeBomb();
    }

    public void use(Combatant user, ArrayList<Combatant> enemies) {
        user.addStatus(Status.invulnerable());
        consume();
    }
}
