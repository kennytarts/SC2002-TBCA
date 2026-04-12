package model.characters;

import java.util.ArrayList;

public interface SpecialSkill {
    String getSpecialSkillName();

    int getSpecialSkillCooldown();
    void setSpecialSkillCooldown(int cooldown);

    default boolean canUseSpecialSkill() {
        return getSpecialSkillCooldown() == 0;
    }

    default void startSpecialSkillCooldown() {
        setSpecialSkillCooldown(3);
    }

    default void reduceSpecialSkillCooldown() {
        if (getSpecialSkillCooldown() > 0) {
            setSpecialSkillCooldown(getSpecialSkillCooldown() - 1);
        }
    }

    boolean needsSpecialSkillTarget();
    boolean useSpecialSkill(Combatant target, ArrayList<Combatant> enemies);
}
