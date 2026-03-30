package model;

import java.util.ArrayList;

public class SmokeBomb extends Item {
    public SmokeBomb() {
        super("Smoke Bomb");
    }

    public void use(Entity user, ArrayList<Entity> enemies) {
        user.addStatus(Status.invulnerable());
        consume();
    }
}
