package model;

public class Goblin extends Enemy {

    public Goblin() {
        this("Goblin");
    }

    public Goblin(String name) {
        super(name, 55, 35, 15, 25);
    }
}
