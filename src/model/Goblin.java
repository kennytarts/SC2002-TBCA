package model;

public class Goblin extends Enemy {
    
    public Goblin() {
        setHp(55);
        setMaxHP(55);
        setAtk(35);
        setDef(15);
        setSpd(25);
        setName("Goblin");
    }

    public Goblin(String name) {
        setHp(55);
        setMaxHP(55);
        setAtk(35);
        setDef(15);
        setSpd(25);
        setName(name);
    }

    
}
