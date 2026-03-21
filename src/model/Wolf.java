package model;

public class Wolf extends Enemy {

    public Wolf() {
        setHp(40);
        setMaxHP(40);
        setAtk(45);
        setDef(5);
        setSpd(35);
        setName("Wolf");
    }

    public Wolf(String name) {
        setHp(40);
        setMaxHP(40);
        setAtk(45);
        setDef(5);
        setSpd(35);
        setName(name);
    }
    
    
}
