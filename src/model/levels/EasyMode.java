package model.levels;

import java.util.ArrayList;

import model.characters.Combatant;
import model.characters.enemies.Goblin;

public class EasyMode implements LevelConfig {
    @Override
    public ArrayList<Combatant> createInitialEnemies() {
        ArrayList<Combatant> enemies = new ArrayList<Combatant>();
        enemies.add(new Goblin("Goblin A"));
        enemies.add(new Goblin("Goblin B"));
        enemies.add(new Goblin("Goblin C"));
        return enemies;
    }

    @Override
    public ArrayList<Combatant> createBackupEnemies() {
        return new ArrayList<Combatant>();
    }
}
