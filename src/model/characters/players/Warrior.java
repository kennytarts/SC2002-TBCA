package model.characters.players;

import java.util.ArrayList;
import java.util.HashMap;

import model.characters.Combatant;
import model.characters.Player;
import model.data.EntityDataService;
import model.status.Status;

public class Warrior extends Player {
    private static final HashMap<String, Integer> DATA = EntityDataService.getData("../data/warrior");

    public Warrior() {
        super("Warrior", DATA.get("hp"), DATA.get("attack"), DATA.get("defense"), DATA.get("speed"));
    }

    @Override
    public String getSpecialSkillName() {
        return "Shield Bash";
    }

    @Override
    public boolean needsSpecialSkillTarget() {
        return true;
    }

    @Override
    public boolean useSpecialSkill(Combatant target, ArrayList<Combatant> enemies) {
        if (target == null || !target.isAlive()) {
            return false;
        }

        basicAttack(target);

        if (target.isAlive()) {
            target.addStatus(Status.stun());
        }

        return true;
    }
}
