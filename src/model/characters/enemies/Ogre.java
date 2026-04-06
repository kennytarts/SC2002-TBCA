package model.characters.enemies;

import controller.battle.enemy.OgreAttackStrategy;
import model.characters.Enemy;

public class Ogre extends Enemy {
    public Ogre(String name, int hp, int attack, int defense, int speed) {
        super(name, hp, attack, defense, speed,
                new OgreAttackStrategy());
    }
}
