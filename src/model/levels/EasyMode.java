package model.levels;

import java.util.ArrayList;

import model.characters.Combatant;
import model.characters.enemies.Goblin;

public class EasyMode implements LevelConfig {
    @Override
    public ArrayList<Combatant> createInitialEnemies(EnemyFactory enemyFactory) {
        ArrayList<Combatant> enemies = new ArrayList<Combatant>();
        enemies.add(enemyFactory.createGoblin("Goblin A"));
        enemies.add(enemyFactory.createGoblin("Goblin B"));
        enemies.add(enemyFactory.createGoblin("Goblin C"));
        return enemies;
    }

    @Override
    public ArrayList<Combatant> createBackupEnemies(EnemyFactory enemyFactory) {
        return new ArrayList<Combatant>();
    }
}
