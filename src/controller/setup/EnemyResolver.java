package controller.setup;

import java.util.HashMap;

import model.characters.Combatant;
import model.characters.enemies.Goblin;
import model.characters.enemies.Ogre;
import model.characters.enemies.Wolf;
import model.data.EntityDataService;
import model.levels.EnemyFactory;

public class EnemyResolver implements EnemyFactory {
    @Override
    public Combatant createGoblin(String name) {
        HashMap<String, Integer> data = EntityDataService.getData("../data/goblin");
        return new Goblin(name, data.get("hp"), data.get("attack"), data.get("defense"), data.get("speed"));
    }

    @Override
    public Combatant createWolf(String name) {
        HashMap<String, Integer> data = EntityDataService.getData("../data/wolf");
        return new Wolf(name, data.get("hp"), data.get("attack"), data.get("defense"), data.get("speed"));
    }

    @Override
    public Combatant createOgre(String name) {
        HashMap<String, Integer> data = EntityDataService.getData("../data/ogre");
        return new Ogre(name, data.get("hp"), data.get("attack"), data.get("defense"), data.get("speed"));
    }
}
