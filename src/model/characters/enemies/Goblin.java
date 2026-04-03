package model.characters.enemies;

import java.util.HashMap;

import controller.battle.enemy.BasicAttackEnemyStrategy;
import model.characters.Enemy;
import model.data.EntityDataService;

public class Goblin extends Enemy {
    private static final HashMap<String, Integer> DATA = EntityDataService.getData("../data/goblin");

    public Goblin() {
        this("Goblin");
    }

    public Goblin(String name) {
        super(name, DATA.get("hp"), DATA.get("attack"), DATA.get("defense"), DATA.get("speed"),
                new BasicAttackEnemyStrategy());
    }
}
