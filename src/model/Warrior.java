package model;

import java.util.ArrayList;

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
    public boolean useSpecialSkill(Entity target, ArrayList<Entity> enemies) {
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
