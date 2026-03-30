package model;

import java.util.ArrayList;

public class Warrior extends Player {
    public Warrior() {
        setHp(260);
        setMaxHP(260);
        setAtk(40);
        setDef(20);
        setSpd(30);
        setName("Warrior");
    }

    @Override
    public void specialSkill(ArrayList<Entity> enemies) {
        for (Entity enemy : enemies) {
            basicAttack(enemy);

            if (enemy.isAlive()) {
                enemy.addStatus(Status.stun());
            }
        }
    }

    @Override
    public boolean useSpecialSkill(ArrayList<Entity> enemies) {
        specialSkill(enemies);
        return true;
    }
}