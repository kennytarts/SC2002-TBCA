package model.characters;

import java.util.ArrayList;

import model.items.Item;
import model.status.Status;
import model.status.StatusEffects;

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
        itemList.removeIf(Item::isConsumed);
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

    public void setSpecialSkillCooldown(int cooldown) {
        specialSkillCooldown = cooldown;
    }
}
