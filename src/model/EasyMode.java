package model;

import java.util.ArrayList;

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

    @Override
    public String getDifficultyName() {
        return "Easy";
    }
}
