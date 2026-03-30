package model;

public class Wolf extends Enemy {

    public Wolf() {
        this("Wolf");
    }

    public Wolf(String name) {
        super(name, 40, 45, 5, 35);
    }
}
