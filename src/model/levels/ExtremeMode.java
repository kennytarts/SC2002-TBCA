package model.levels;

import java.util.ArrayList;

import model.characters.Combatant;
import model.characters.enemies.*;;

public class ExtremeMode implements LevelConfig {
    @Override
    public ArrayList<Combatant> createInitialEnemies() {
        ArrayList<Combatant> enemies = new ArrayList<Combatant>();
        enemies.add(new Ogre());
        enemies.add(new Goblin("Goblin A"));
        enemies.add(new Goblin("Goblin B"));
        return enemies;
    }

    @Override
    public ArrayList<Combatant> createBackupEnemies() {
        ArrayList<Combatant> enemies = new ArrayList<Combatant>();
        enemies.add(new Wolf("Wolf A"));
        enemies.add(new Wolf("Wolf B"));
        return enemies;
    }
}
