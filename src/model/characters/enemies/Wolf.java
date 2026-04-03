package model.characters.enemies;

import java.util.HashMap;

import controller.battle.enemy.BasicAttackEnemyStrategy;
import model.characters.Enemy;
import model.data.EntityDataService;

public class Wolf extends Enemy {
    public Wolf() {
        this("Wolf");
    }

    public Wolf(String name) {
        // The concrete enemy chooses its strategy so wolf behavior can change
        // later without changing the shared Enemy base class.
        HashMap<String, Integer> data = EntityDataService.getData("../data/wolf");
        super(name, data.get("hp"), data.get("attack"), data.get("defense"), data.get("speed"),
                new BasicAttackEnemyStrategy());
    }
}
