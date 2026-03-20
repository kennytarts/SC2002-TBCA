import java.util.ArrayList;

public class Wizard extends Player {
    public Wizard() {
        setHp(200);
        setAtk(50);
        setDef(10);
        setSpd(20);
        setName("Wizard");
    }

    // Arcane Blast
    //TODO cooldown 3 turns
    public void specialSkill(ArrayList<Entity> e) {
        for (Entity enemy: e) {
            basicAttack(enemy);

            // +10hp for each my killed
            if (enemy.getHp() == 0) {
                setHp(getHp()+10);
            }
        }
         
    }
}
