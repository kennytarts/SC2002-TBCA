package model.characters.enemies;

import java.util.HashMap;

import controller.battle.enemy.OgreAttackStrategy;
import model.characters.Enemy;
import model.data.EntityDataService;

public class Ogre extends Enemy {
    private static final HashMap<String, Integer> DATA = EntityDataService.getData("../data/ogre");

    public Ogre() {
        this("Ogre");
    }

    public Ogre(String name) {
        super(name, DATA.get("hp"), DATA.get("attack"), DATA.get("defense"), DATA.get("speed"),
                new OgreAttackStrategy());
    }
    
}
