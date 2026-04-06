package controller.setup;

import model.characters.Combatant;
import model.characters.enemies.Goblin;
import model.characters.enemies.Ogre;
import model.characters.enemies.Wolf;
import model.levels.EnemyFactory;

public class EnemyResolver implements EnemyFactory {
    @Override
    public Combatant createGoblin(String name) {
        return new Goblin(name, 55, 35, 15, 25);
    }

    @Override
    public Combatant createWolf(String name) {
        return new Wolf(name, 40, 45, 5, 35);
    }

    @Override
    public Combatant createOgre(String name) {
        return new Ogre(name, 80, 30, 20, 10);
    }
}
