package model.characters.enemies;

import controller.battle.BasicAttackEnemyStrategy;
import model.characters.Enemy;

public class Goblin extends Enemy {

    public Goblin() {
        this("Goblin");
    }

    public Goblin(String name) {
        super(name, 55, 35, 15, 25, new BasicAttackEnemyStrategy());
    }
}
