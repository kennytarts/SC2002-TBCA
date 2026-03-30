package model;

import java.util.ArrayList;

public class Wizard extends Player {
    public Wizard() {
        super("Wizard", 200, 50, 10, 20);
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
    public boolean useSpecialSkill(Entity target, ArrayList<Entity> enemies) {
        boolean used = false;

        for (Entity enemy : enemies) {
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
