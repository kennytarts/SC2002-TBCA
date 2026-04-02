package model.characters.enemies;

import controller.battle.BasicAttackEnemyStrategy;
import model.characters.Enemy;

public class Wolf extends Enemy {

    public Wolf() {
        this("Wolf");
    }

    public Wolf(String name) {
        super(name, 40, 45, 5, 35, new BasicAttackEnemyStrategy());
    }
}
