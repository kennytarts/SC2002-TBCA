package model.characters.enemies;

import java.util.HashMap;

import controller.battle.enemy.BasicAttackEnemyStrategy;
import model.characters.Enemy;
import model.data.EntityDataService;

public class Wolf extends Enemy {
    private static final HashMap<String, Integer> DATA = EntityDataService.getData("../data/wolf");

    public Wolf() {
        this("Wolf");
    }

    public Wolf(String name) {
        // The concrete enemy chooses its strategy so wolf behavior can change
        // later without changing the shared Enemy base class.
        super(name, DATA.get("hp"), DATA.get("attack"), DATA.get("defense"), DATA.get("speed"),
                new BasicAttackEnemyStrategy());
    }
}
