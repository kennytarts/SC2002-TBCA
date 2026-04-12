package model.levels;

import java.util.ArrayList;

import model.characters.Combatant;
import model.characters.enemies.Goblin;
import model.characters.enemies.Wolf;

public class MediumMode implements LevelConfig {
    @Override
    public ArrayList<Combatant> createInitialEnemies() {
        ArrayList<Combatant> enemies = new ArrayList<Combatant>();
        enemies.add(new Goblin("Goblin"));
        enemies.add(new Wolf("Wolf"));
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
