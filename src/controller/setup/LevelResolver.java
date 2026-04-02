package controller.setup;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import model.levels.EasyMode;
import model.levels.HardMode;
import model.levels.LevelConfig;
import model.levels.MediumMode;

public class LevelResolver {
    private final Map<Integer, Supplier<LevelConfig>> levelCreators;

    public LevelResolver() {
        this.levelCreators = new HashMap<Integer, Supplier<LevelConfig>>();
        levelCreators.put(1, EasyMode::new);
        levelCreators.put(2, MediumMode::new);
        levelCreators.put(3, HardMode::new);
    }

    public LevelConfig resolveLevel(int selection) {
        Supplier<LevelConfig> levelCreator = levelCreators.get(selection);
        if (levelCreator == null) {
            return null;
        }

        return levelCreator.get();
    }
}
