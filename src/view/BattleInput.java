package view;

import java.util.ArrayList;

import model.Combatant;
import model.Player;

public interface BattleInput {
    Combatant chooseTarget(ArrayList<Combatant> enemies);
    int choosePlayerAction(Player player);
    int chooseItem(Player player);
}
