package model;

import java.util.ArrayList;

public class Potion extends Item {
    public Potion() {
        super("Potion");
    }

    public void use(Entity user, ArrayList<Entity> enemies) {
        user.heal(100);
        consume();
    }
}
