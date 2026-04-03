package controller.setup;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class EntityDataSyncService {
    private static final String[] ENTITY_FILES = { "warrior", "wizard", "goblin", "wolf", "ogre" };
    private static final Path DATA_DIRECTORY = Path.of("src", "data");
    private static final String BASE_URL = "https://raw.githubusercontent.com/kennytarts/SC2002-TBCA/refs/heads/glenn/data-fetch/src/data";

    public void syncEntityData() {
        HttpClient httpClient = HttpClient.newHttpClient();
        Gson gson = new Gson();

        try {
            Files.createDirectories(DATA_DIRECTORY);
        } catch (IOException e) {
            return;
        }

        if (!BASE_URL.isBlank()) {
            for (String entityFile : ENTITY_FILES) {
                fetchAndWriteEntityFile(httpClient, gson, entityFile);
            }
        }
    }

    public boolean areAllEntityFilesPresent() {
        for (String entityFile : ENTITY_FILES) {
            Path filePath = DATA_DIRECTORY.resolve(entityFile + ".json");
            if (!Files.exists(filePath)) {
                return false;
            }
        }
        return true;
    }

    private void fetchAndWriteEntityFile(HttpClient httpClient, Gson gson, String entityFile) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + entityFile + ".json"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300 || response.body().isBlank()) {
                return;
            }

            JsonObject json = gson.fromJson(response.body(), JsonObject.class);
            if (json == null || !json.has("stats")) {
                return;
            }

            Files.writeString(DATA_DIRECTORY.resolve(entityFile + ".json"), gson.toJson(json), StandardCharsets.UTF_8);
        } catch (Exception e) {
            // Keep existing local file if fetch fails.
        }
    }
}
