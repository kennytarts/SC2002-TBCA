package model;

import java.util.ArrayList;

public interface ISpecialSkill {
    boolean needsSpecialSkillTarget();
    boolean useSpecialSkill(Combatant target, ArrayList<Combatant> enemies);
}
