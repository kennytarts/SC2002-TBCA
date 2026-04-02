package controller.setup;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import model.characters.Player;
import model.characters.players.Warrior;
import model.characters.players.Wizard;

public class PlayerResolver {
    private final Map<Integer, Supplier<Player>> playerCreators;

    public PlayerResolver() {
        this.playerCreators = new HashMap<Integer, Supplier<Player>>();
        playerCreators.put(1, Warrior::new);
        playerCreators.put(2, Wizard::new);
    }

    public Player resolvePlayer(int selection) {
        Supplier<Player> playerCreator = playerCreators.get(selection);
        if (playerCreator == null) {
            return null;
        }

        return playerCreator.get();
    }
}
