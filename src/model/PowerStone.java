package model;

import java.util.ArrayList;

public class PowerStone extends Item {
    public PowerStone() {
        super("Power Stone");
    }

    public void use(Entity user, ArrayList<Entity> enemies) {
        consume();
    }
}
