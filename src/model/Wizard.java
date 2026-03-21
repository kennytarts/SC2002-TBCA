package model;

import java.util.ArrayList;

public class Wizard extends Player {
    public Wizard() {
        setCurrentHP(200);
        setMaxHP(200);
        setAtk(50);
        setDef(10);
        setSpd(20);
        setName("Wizard");
    }

    public void specialSkill(ArrayList<Entity> enemies) {
        for (Entity enemy : enemies) {
            if (!enemy.isAlive()) {
                continue;
            }

            basicAttack(enemy);

            if (!enemy.isAlive()) {
                setAtk(getAtk() + 10);
            }
        }
    }
}
