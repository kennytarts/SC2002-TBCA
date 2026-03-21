package model;

import java.util.ArrayList;

public abstract class Player extends Entity implements ISpecialSkill {
    private ArrayList<Integer> itemList;
    private int specialSkillCooldown = 0;

    public void defend() {
        if (!hasStatus(StatusEffects.DEFEND)) {
            setDef(getDef() + 10);
        }
        addStatus(Status.defend());
    }

    public int getSpecialSkillCooldown() {
        return specialSkillCooldown;
    }

    public boolean canUseSpecialSkill() {
        return specialSkillCooldown == 0;
    }

    public void startSpecialSkillCooldown() {
        specialSkillCooldown = 3;
    }

    public void reduceSpecialSkillCooldown() {
        if (specialSkillCooldown > 0) {
            specialSkillCooldown--;
        }
    }
}