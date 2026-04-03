package model.data;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public final class EntityDataService {
    private static final Gson GSON = new Gson();

    private EntityDataService() {
    }

    public static HashMap<String, Integer> getData(String filePath) {
        HashMap<String, Integer> data = createDefaultData();

        try {
            String json = Files.readString(resolvePath(filePath), StandardCharsets.UTF_8);
            JsonObject root = GSON.fromJson(json, JsonObject.class);
            if (root != null && root.has("stats") && root.get("stats").isJsonObject()) {
                JsonObject stats = root.getAsJsonObject("stats");
                data.put("hp", getInt(stats, "hp"));
                data.put("attack", getInt(stats, "attack"));
                data.put("defense", getInt(stats, "defense"));
                data.put("speed", getInt(stats, "speed"));
            }
        } catch (IOException e) {
            return data;
        }
        return data;
    }

    private static HashMap<String, Integer> createDefaultData() {
        HashMap<String, Integer> data = new HashMap<String, Integer>();
        data.put("hp", 0);
        data.put("attack", 0);
        data.put("defense", 0);
        data.put("speed", 0);
        return data;
    }

    private static int getInt(JsonObject json, String key) {
        if (json == null || !json.has(key) || json.get(key).isJsonNull()) {
            return 0;
        }

        try {
            return json.get(key).getAsInt();
        } catch (Exception e) {
            return 0;
        }
    }

    private static Path resolvePath(String filePath) {
        String normalized = filePath.replace("\\", "/");
        if (normalized.startsWith("../data/")) {
            normalized = "src/data/" + normalized.substring("../data/".length());
        }
        if (!normalized.endsWith(".json")) {
            normalized += ".json";
        }
        return Path.of(normalized);
    }
}
