package model;
import java.util.ArrayList;

/** Potion */
public class PowerStone extends Item {
  // Trigger the special skill effect once, but it does not start or change the cooldown timer. In
  // short, free extra use of the skill.
    public PowerStone() {
      super("Power Stone");
    }

  public void use(Entity user, ArrayList<Entity> enemies) {
      consumed();
    }
}
