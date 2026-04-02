package model.characters;

import java.util.ArrayList;

public interface SpecialSkill {
    boolean needsSpecialSkillTarget();
    boolean useSpecialSkill(Combatant target, ArrayList<Combatant> enemies);
}
