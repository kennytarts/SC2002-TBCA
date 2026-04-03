package model.characters.enemies;

import java.util.HashMap;

import controller.battle.enemy.OgreAttackStrategy;
import model.characters.Enemy;
import model.data.EntityDataService;

public class Ogre extends Enemy {
    public Ogre() {
        this("Ogre");
    }

    public Ogre(String name) {
        HashMap<String, Integer> data = EntityDataService.getData("../data/ogre");
        super(name, data.get("hp"), data.get("attack"), data.get("defense"), data.get("speed"),
                new OgreAttackStrategy());
    }
    
}
