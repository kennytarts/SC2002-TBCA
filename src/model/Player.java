package model;

import java.util.ArrayList;

import view.BattleView;

public abstract class Player extends Entity implements ISpecialSkill {
    private ArrayList<Item> itemList = new ArrayList<Item>();
    private int specialSkillCooldown = 0;

    public ArrayList<Item> getItems() {
        return itemList;
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
            setDef(getDef() + 10);
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

    public abstract boolean useSpecialSkill(ArrayList<Entity> enemies, BattleView view);
}