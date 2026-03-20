import java.util.ArrayList;

public class Warrior extends Player {
    public Warrior() {
        setHp(260);
        setAtk(40);
        setDef(20);
        setSpd(30);
        setName("Warrior");
    }

    // Shield Bash
    //TODO cooldown 3 turns
    public void specialSkill(ArrayList<Entity> e) {
        for (Entity enemy: e) {
            basicAttack(enemy);

            //TODO implement stunt on enemy
            enemy.setStatus(Status.stun());
        }
    }


}
