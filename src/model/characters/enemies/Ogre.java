package model.characters.enemies;
import controller.battle.BasicAttackEnemyStrategy;
import model.characters.Enemy;

public class Ogre extends Enemy {
    public Ogre() {
        this("Ogre");
    }

    public Ogre(String name) {
        super(name, 80, 25, 20, 10, new BasicAttackEnemyStrategy());
    }
    
}
