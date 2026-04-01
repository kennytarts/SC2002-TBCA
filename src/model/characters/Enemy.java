package model.characters;

import controller.battle.BasicAttackEnemyStrategy;
import controller.battle.EnemyActionStrategy;

public abstract class Enemy extends Entity {
    private final EnemyActionStrategy actionStrategy;

    protected Enemy(String name, int maxHP, int attack, int defense, int speed) {
        super(name, maxHP, attack, defense, speed);
        this.actionStrategy = new BasicAttackEnemyStrategy();
    }

    public EnemyActionStrategy getActionStrategy() {
        return actionStrategy;
    }
}
