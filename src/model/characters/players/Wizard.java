package model.characters.players;

import java.util.ArrayList;
import java.util.HashMap;

import model.characters.Combatant;
import model.characters.Player;
import model.data.EntityDataService;

public class Wizard extends Player {
    public Wizard() {
        HashMap<String, Integer> data = EntityDataService.getData("../data/wizard");
        super("Wizard", data.get("hp"), data.get("attack"), data.get("defense"), data.get("speed"));
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
