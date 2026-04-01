package model.characters.players;

import java.util.ArrayList;

import model.characters.Combatant;
import model.characters.Player;
import model.status.Status;

public class Warrior extends Player {
    public Warrior() {
        super("Warrior", 260, 40, 20, 30);
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
