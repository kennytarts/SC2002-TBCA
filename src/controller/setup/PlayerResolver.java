package controller.setup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import model.characters.Player;
import model.characters.players.Warrior;
import model.characters.players.Warrior;
import model.characters.players.Wizard;
import model.data.EntityDataService;

public class PlayerResolver {
    private final Map<Integer, String> playerTypes;

    public PlayerResolver() {
        this.playerTypes = new LinkedHashMap<Integer, String>();
        playerTypes.put(1, "warrior");
        playerTypes.put(2, "wizard");
    }

    public Player resolvePlayer(int selection) {
        String playerType = playerTypes.get(selection);
        return createPlayer(playerType);
    }

    public ArrayList<Player> getPlayerOptions() {
        ArrayList<Player> players = new ArrayList<Player>();
        for (String playerType : playerTypes.values()) {
            Player player = createPlayer(playerType);
            if (player != null) {
                players.add(player);
            }
        }
        return players;
    }

    private Player createPlayer(String playerType) {
        if (playerType == null) {
            return null;
        }

        HashMap<String, Integer> data = EntityDataService.getData("../data/" + playerType);
        if ("warrior".equals(playerType)) {
            return new Warrior(data.get("hp"), data.get("attack"), data.get("defense"), data.get("speed"));
        }
        if ("wizard".equals(playerType)) {
            return new Wizard(data.get("hp"), data.get("attack"), data.get("defense"), data.get("speed"));
        }

        return null;
    }
}
