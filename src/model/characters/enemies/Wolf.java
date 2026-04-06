package model.characters.enemies;

import controller.battle.enemy.BasicAttackEnemyStrategy;
import model.characters.Enemy;

public class Wolf extends Enemy {
    public Wolf(String name, int hp, int attack, int defense, int speed) {
        // The concrete enemy chooses its strategy so wolf behavior can change
        // later without changing the shared Enemy base class.
        super(name, hp, attack, defense, speed,
                new BasicAttackEnemyStrategy());
    }
}
