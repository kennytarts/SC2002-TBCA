package model.characters;

import java.util.ArrayList;

import model.status.Status;
import model.status.StatusEffects;

public abstract class Entity implements Combatant {
    private int hp;
    private int maxHP;
    private int attack;
    private int defense;
    private int speed;
    private final String name;
    private ArrayList<Status> statuses = new ArrayList<Status>();

    protected Entity(String name, int maxHP, int attack, int defense, int speed) {
        this.name = name;
        this.hp = maxHP;
        this.maxHP = maxHP;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHP;
    }

    public int getAtk() {
        return attack;
    }

    public int getDef() {
        return defense;
    }

    public int getSpd() {
        return speed;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Status> getStatuses() {
        return statuses;
    }

    public boolean hasStatus(StatusEffects effect) {
        for (Status status : statuses) {
            if (status.getEffect() == effect) {
                return true;
            }
        }
        return false;
    }

    public Status getStatus(StatusEffects effect) {
        for (Status status : statuses) {
            if (status.getEffect() == effect) {
                return status;
            }
        }
        return null;
    }

    public void addStatus(Status newStatus) {
        Status existingStatus = getStatus(newStatus.getEffect());

        if (existingStatus != null) {
            existingStatus.refresh();
            return;
        }

        statuses.add(newStatus);
    }

    public void removeStatus(StatusEffects effect) {
        for (int i = 0; i < statuses.size(); i++) {
            if (statuses.get(i).getEffect() == effect) {
                statuses.remove(i);
                i--;
            }
        }
    }

    public int basicAttack(Combatant target) {
        int damage = Math.max(0, attack - target.getDef());
        return target.takeDamage(damage);
    }

    public int takeDamage(int damage) {
        int appliedDamage = Math.max(0, damage);
        hp = Math.max(0, hp - appliedDamage);
        return appliedDamage;
    }

    public void heal(int amount) {
        hp = Math.min(maxHP, hp + Math.max(0, amount));
    }

    public void changeAttack(int amount) {
        attack += amount;
    }

    public void changeDefense(int amount) {
        defense += amount;
    }

    public boolean isAlive() {
        return hp > 0;
    }
}
