package model;

import java.util.ArrayList;

public class SmokeBomb extends Item {
    public SmokeBomb() {
        super("Smoke Bomb");
    }

    public void use(Combatant user, ArrayList<Combatant> enemies) {
        user.addStatus(Status.invulnerable());
        consume();
    }
}
