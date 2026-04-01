package model;

import java.util.ArrayList;

public class HardMode implements LevelConfig {
    @Override
    public ArrayList<Combatant> createInitialEnemies() {
        ArrayList<Combatant> enemies = new ArrayList<Combatant>();
        enemies.add(new Goblin("Goblin A"));
        enemies.add(new Goblin("Goblin B"));
        return enemies;
    }

    @Override
    public ArrayList<Combatant> createBackupEnemies() {
        ArrayList<Combatant> enemies = new ArrayList<Combatant>();
        enemies.add(new Goblin("Goblin C"));
        enemies.add(new Wolf("Wolf A"));
        enemies.add(new Wolf("Wolf B"));
        return enemies;
    }

    @Override
    public String getDifficultyName() {
        return "Hard";
    }
}
