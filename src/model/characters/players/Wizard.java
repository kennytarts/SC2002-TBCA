package model.characters.players;

import java.util.ArrayList;

import model.characters.Combatant;
import model.characters.Player;

public class Wizard extends Player {
    public Wizard() {
        super("Wizard", 200, 50, 10, 20);
    }

    @Override
    public String getSpecialSkillName() {
        return "Arcane Blast";
    }

    @Override
    public boolean needsSpecialSkillTarget() {
        return false;
    }

    @Override
    public boolean useSpecialSkill(Combatant target, ArrayList<Combatant> enemies) {
        boolean used = false;

        for (Combatant enemy : enemies) {
            if (!enemy.isAlive()) {
                continue;
            }

            used = true;
            basicAttack(enemy);

            if (!enemy.isAlive()) {
                changeAttack(10);
            }
        }

        return used;
    }
}
