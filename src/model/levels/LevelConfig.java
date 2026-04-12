package model.levels;

import java.util.ArrayList;

import model.characters.Combatant;

public interface LevelConfig {
    ArrayList<Combatant> createInitialEnemies();
    ArrayList<Combatant> createBackupEnemies();
}
