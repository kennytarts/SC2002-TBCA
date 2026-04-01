package view.input;

import java.util.ArrayList;

import model.characters.Combatant;
import model.characters.Player;

public interface BattleInput {
    Combatant chooseTarget(ArrayList<Combatant> enemies);
    int choosePlayerAction(Player player);
    int chooseItem(Player player);
}
