package controller.setup;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import model.characters.Combatant;
import model.levels.EasyMode;
import model.levels.ExtremeMode;
import model.levels.HardMode;
import model.levels.LevelConfig;
import model.levels.MediumMode;

public class LevelResolver {
    private final Map<Integer, Supplier<LevelConfig>> levelCreators;
    private final Map<Integer, String> levelDescriptions;
    private final EnemyResolver enemyResolver;

    public LevelResolver() {
        this.levelCreators = new LinkedHashMap<Integer, Supplier<LevelConfig>>();
        this.levelDescriptions = new LinkedHashMap<Integer, String>();
        this.enemyResolver = new EnemyResolver();
        register(1, "Easy - 3 enemies (3 Goblins)", EasyMode::new);
        register(2, "Medium - 2 initial enemies (1 Goblin, 1 Wolf), 2 backup enemies (2 Wolves)", MediumMode::new);
        register(3, "Hard - 2 initial enemies (2 Goblins), 3 backup enemies (2 Wolves)", HardMode::new);
        register(4, "Extrene - 3 initial enemies (1 Ogre, 2 Goblins), 2 backup enemies (2 Wolves)", ExtremeMode::new);

    }

    public LevelConfig resolveLevel(int selection) {
        Supplier<LevelConfig> levelCreator = levelCreators.get(selection);
        if (levelCreator == null) {
            return null;
        }

        return levelCreator.get();
    }

    public ArrayList<String> getLevelDescriptions() {
        return new ArrayList<String>(levelDescriptions.values());
    }

    public ArrayList<Combatant> getEnemyPreviews() {
        LinkedHashMap<String, Combatant> previewEnemies = new LinkedHashMap<String, Combatant>();

        for (Supplier<LevelConfig> levelCreator : levelCreators.values()) {
            LevelConfig level = levelCreator.get();
            addPreviewEnemies(previewEnemies, level.createInitialEnemies(enemyResolver));
            addPreviewEnemies(previewEnemies, level.createBackupEnemies(enemyResolver));
        }

        return new ArrayList<Combatant>(previewEnemies.values());
    }

    public EnemyResolver getEnemyResolver() {
        return enemyResolver;
    }

    private void register(int selection, String description, Supplier<LevelConfig> levelCreator) {
        levelCreators.put(selection, levelCreator);
        levelDescriptions.put(selection, description);
    }

    private void addPreviewEnemies(Map<String, Combatant> previewEnemies, ArrayList<Combatant> enemies) {
        for (Combatant enemy : enemies) {
            String enemyType = enemy.getClass().getSimpleName();
            if (!previewEnemies.containsKey(enemyType)) {
                previewEnemies.put(enemyType, enemy);
            }
        }
    }
}
