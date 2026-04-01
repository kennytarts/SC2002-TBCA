package model;

import java.util.ArrayList;

public abstract class Player extends Entity implements SpecialSkill {
    private final ArrayList<Item> itemList = new ArrayList<Item>();
    private int specialSkillCooldown = 0;

    protected Player(String name, int maxHP, int attack, int defense, int speed) {
        super(name, maxHP, attack, defense, speed);
    }

    public ArrayList<Item> getItems() {
        return itemList;
    }

    public Item getItem(int index) {
        return itemList.get(index);
    }

    public void addItem(Item item) {
        itemList.add(item);
    }

    public void removeConsumedItems() {
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).isConsumed()) {
                itemList.remove(i);
                i--;
            }
        }
    }

    public void defend() {
        if (!hasStatus(StatusEffects.DEFEND)) {
            changeDefense(10);
        }
        addStatus(Status.defend());
    }

    public int getSpecialSkillCooldown() {
        return specialSkillCooldown;
    }

    public boolean canUseSpecialSkill() {
        return specialSkillCooldown == 0;
    }

    public void startSpecialSkillCooldown() {
        specialSkillCooldown = 3;
    }

    public void reduceSpecialSkillCooldown() {
        if (specialSkillCooldown > 0) {
            specialSkillCooldown--;
        }
    }

    public abstract String getSpecialSkillName();
}
