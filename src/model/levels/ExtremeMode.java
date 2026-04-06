package model.levels;

import java.util.ArrayList;

import model.characters.Combatant;

public class ExtremeMode implements LevelConfig {
    @Override
    public ArrayList<Combatant> createInitialEnemies(EnemyFactory enemyFactory) {
        ArrayList<Combatant> enemies = new ArrayList<Combatant>();
        enemies.add(enemyFactory.createOgre("Ogre"));
        enemies.add(enemyFactory.createGoblin("Goblin A"));
        enemies.add(enemyFactory.createGoblin("Goblin B"));
        return enemies;
    }

    @Override
    public ArrayList<Combatant> createBackupEnemies(EnemyFactory enemyFactory) {
        ArrayList<Combatant> enemies = new ArrayList<Combatant>();
        enemies.add(enemyFactory.createWolf("Wolf A"));
        enemies.add(enemyFactory.createWolf("Wolf B"));
        return enemies;
    }
}
