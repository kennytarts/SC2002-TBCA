package model;

import java.util.ArrayList;

public class Warrior extends Player {
    public Warrior() {
        setHp(260);
        setAtk(40);
        setDef(20);
        setSpd(30);
        setName("Warrior");
    }

    public void specialSkill(ArrayList<Entity> enemies) {
        for (Entity enemy : enemies) {
            if (!enemy.isAlive()) {
                continue;
            }

            basicAttack(enemy);

            if (enemy.isAlive()) {
                enemy.addStatus(Status.stun());
            }
        }
    }
}