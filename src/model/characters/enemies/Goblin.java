package model.characters.enemies;

import java.util.HashMap;

import controller.battle.enemy.BasicAttackEnemyStrategy;
import model.characters.Enemy;
import model.data.EntityDataService;

public class Goblin extends Enemy {
    public Goblin() {
        this("Goblin");
    }

    public Goblin(String name) {
        HashMap<String, Integer> data = EntityDataService.getData("../data/goblin");
        super(name, data.get("hp"), data.get("attack"), data.get("defense"), data.get("speed"),
                new BasicAttackEnemyStrategy());
    }
}
