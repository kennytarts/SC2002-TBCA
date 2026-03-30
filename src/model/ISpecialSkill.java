package model;

import java.util.ArrayList;

public interface ISpecialSkill {
    boolean needsSpecialSkillTarget();
    boolean useSpecialSkill(Entity target, ArrayList<Entity> enemies);
}
