package model.items;

import java.util.ArrayList;

import model.characters.Combatant;
import model.status.Status;
import model.status.StatusEffects;

public class AgilityBoots extends Item {
    public AgilityBoots() {
        super("Agility Boots");
    }

    @Override
    public Item copy() {
        return new AgilityBoots();
    }

    @Override
    public void use(Combatant user, ArrayList<Combatant> enemies) {
        if (!user.hasStatus(StatusEffects.SPEED)) {
            user.changeSpeed(15);
        }

        user.addStatus(Status.speed());
        consume();
    }
}
