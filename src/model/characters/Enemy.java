package model.characters;

import controller.battle.EnemyActionStrategy;

public abstract class Enemy extends Entity {
    private final EnemyActionStrategy actionStrategy;

    protected Enemy(String name, int maxHP, int attack, int defense, int speed, EnemyActionStrategy actionStrategy) {
        super(name, maxHP, attack, defense, speed);
        this.actionStrategy = actionStrategy;
    }

    public EnemyActionStrategy getActionStrategy() {
        return actionStrategy;
    }
}
