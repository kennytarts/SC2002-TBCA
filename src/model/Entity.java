package model;

import java.util.ArrayList;

public abstract class Entity {
    private int hp;
    private int attack;
    private int defense;
    private int speed;
    private String name;
    private ArrayList<Status> statuses = new ArrayList<Status>();

    public int getHp() {
        return hp;
    }

    public void setHp(int val) {
        hp = val;
    }

    public int getAtk() {
        return attack;
    }

    public void setAtk(int val) {
        attack = val;
    }

    public int getDef() {
        return defense;
    }

    public void setDef(int val) {
        defense = val;
    }

    public int getSpd() {
        return speed;
    }

    public void setSpd(int val) {
        speed = val;
    }

    public String getName() {
        return name;
    }

    public void setName(String val) {
        name = val;
    }

    public ArrayList<Status> getStatuses() {
        return statuses;
    }

    public boolean hasStatus(StatusEffects effect) {
        for (Status s : statuses) {
            if (s.getEffect() == effect) {
                return true;
            }
        }
        return false;
    }

    public Status getStatus(StatusEffects effect) {
        for (Status s : statuses) {
            if (s.getEffect() == effect) {
                return s;
            }
        }
        return null;
    }

    public void addStatus(Status newStatus) {
        Status existing = getStatus(newStatus.getEffect());

        if (existing != null) {
            existing.refresh();
        } else {
            statuses.add(newStatus);
        }
    }

    public void removeStatus(StatusEffects effect) {
        for (int i = 0; i < statuses.size(); i++) {
            if (statuses.get(i).getEffect() == effect) {
                statuses.remove(i);
                i--;
            }
        }
    }

    public int basicAttack(Entity e) {
        int damage = Math.max(0, this.attack - e.getDef());
        e.setHp(Math.max(0, e.getHp() - damage));
        return damage;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void viewAttr() {
        System.out.println(getName());
        System.out.println("HP: " + getHp());
        System.out.println("Attack: " + getAtk());
        System.out.println("Defense: " + getDef());
        System.out.println("Speed: " + getSpd());

        if (statuses.isEmpty()) {
            System.out.println("Status: NONE");
        } else {
            System.out.print("Status: ");
            for (int i = 0; i < statuses.size(); i++) {
                Status s = statuses.get(i);
                System.out.print(s.getEffect() + "(" + s.getDuration() + ")");
                if (i < statuses.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }

        System.out.println();
    }
}