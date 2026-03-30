package model;

public abstract class Enemy extends Entity {
    protected Enemy(String name, int maxHP, int attack, int defense, int speed) {
        super(name, maxHP, attack, defense, speed);
    }
}
