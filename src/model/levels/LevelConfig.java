package model.levels;

import java.util.ArrayList;

import model.characters.Combatant;

public interface LevelConfig {
    ArrayList<Combatant> createInitialEnemies(EnemyFactory enemyFactory);
    ArrayList<Combatant> createBackupEnemies(EnemyFactory enemyFactory);
}
