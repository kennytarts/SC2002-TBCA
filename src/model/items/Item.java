package model.items;

import java.util.ArrayList;

import model.characters.Combatant;

public abstract class Item {
    private final String name;
    private boolean consumed;

    public Item(String name) {
        this.name = name;
        this.consumed = false;
    }

    public String getName() {
        return name;
    }

    public boolean isConsumed() {
        return consumed;
    }

    protected void consume() {
        consumed = true;
    }

    public abstract void use(Combatant user, ArrayList<Combatant> enemies);

    public abstract Item copy();
}
