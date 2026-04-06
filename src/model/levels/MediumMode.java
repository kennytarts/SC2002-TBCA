package model.levels;

import java.util.ArrayList;

import model.characters.Combatant;
public class MediumMode implements LevelConfig {
    @Override
    public ArrayList<Combatant> createInitialEnemies(EnemyFactory enemyFactory) {
        ArrayList<Combatant> enemies = new ArrayList<Combatant>();
        enemies.add(enemyFactory.createGoblin("Goblin"));
        enemies.add(enemyFactory.createWolf("Wolf"));
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
