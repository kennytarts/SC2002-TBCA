package model.characters.players;

import java.util.ArrayList;
import java.util.HashMap;

import model.characters.Combatant;
import model.characters.Player;
import model.data.EntityDataService;

public class Wizard extends Player {
    private static final HashMap<String, Integer> DATA = EntityDataService.getData("../data/wizard");

    public Wizard() {
        super("Wizard", DATA.get("hp"), DATA.get("attack"), DATA.get("defense"), DATA.get("speed"));
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
