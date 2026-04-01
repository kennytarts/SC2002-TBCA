package model;

import java.util.ArrayList;

public interface LevelConfig {
    ArrayList<Combatant> createInitialEnemies();
    ArrayList<Combatant> createBackupEnemies();
    String getDifficultyName();
}
