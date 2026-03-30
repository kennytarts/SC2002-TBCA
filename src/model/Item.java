package model;

public abstract class Item implements IItem {
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
}
