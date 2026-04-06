package model.characters.enemies;

import controller.battle.enemy.BasicAttackEnemyStrategy;
import model.characters.Enemy;

public class Goblin extends Enemy {
    public Goblin(String name, int hp, int attack, int defense, int speed) {
        super(name, hp, attack, defense, speed,
                new BasicAttackEnemyStrategy());
    }
}
