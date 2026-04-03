package model.characters.enemies;

import controller.battle.enemy.BasicAttackEnemyStrategy;
import model.characters.Enemy;

public class Wolf extends Enemy {

    public Wolf() {
        this("Wolf");
    }

    public Wolf(String name) {
        // The concrete enemy chooses its strategy so wolf behavior can change
        // later without changing the shared Enemy base class.
        super(name, 40, 45, 5, 35, new BasicAttackEnemyStrategy());
    }
}
