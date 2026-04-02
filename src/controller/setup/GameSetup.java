package controller.setup;

import java.util.ArrayList;

public class GameSetup {
    private final int playerSelection;
    private final ArrayList<Integer> itemSelections;
    private final int levelSelection;

    public GameSetup(int playerSelection, ArrayList<Integer> itemSelections, int levelSelection) {
        this.playerSelection = playerSelection;
        this.itemSelections = new ArrayList<Integer>(itemSelections);
        this.levelSelection = levelSelection;
    }

    public int getPlayerSelection() {
        return playerSelection;
    }

    public ArrayList<Integer> getItemSelections() {
        return new ArrayList<Integer>(itemSelections);
    }

    public int getLevelSelection() {
        return levelSelection;
    }
}
