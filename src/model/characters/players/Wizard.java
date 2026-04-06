package model.characters.players;

import java.util.ArrayList;

import model.characters.Combatant;
import model.characters.Player;

public class Wizard extends Player {
    public Wizard(int hp, int attack, int defense, int speed) {
        super("Wizard", hp, attack, defense, speed);
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
