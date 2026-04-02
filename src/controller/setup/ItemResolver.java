package controller.setup;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import model.items.Item;
import model.items.Potion;
import model.items.PowerStone;
import model.items.SmokeBomb;

public class ItemResolver {
    private final Map<Integer, Supplier<Item>> itemCreators;
    private final Map<Integer, String> itemNames;

    public ItemResolver() {
        this.itemCreators = new LinkedHashMap<Integer, Supplier<Item>>();
        this.itemNames = new LinkedHashMap<Integer, String>();
        register(1, "Power Stone", PowerStone::new);
        register(2, "Potion", Potion::new);
        register(3, "Smoke Bomb", SmokeBomb::new);
    }

    public Item resolveItem(int selection) {
        Supplier<Item> itemCreator = itemCreators.get(selection);
        if (itemCreator == null) {
            return null;
        }

        return itemCreator.get();
    }

    public ArrayList<String> getItemNames() {
        return new ArrayList<String>(itemNames.values());
    }

    private void register(int selection, String itemName, Supplier<Item> itemCreator) {
        itemCreators.put(selection, itemCreator);
        itemNames.put(selection, itemName);
    }
}
