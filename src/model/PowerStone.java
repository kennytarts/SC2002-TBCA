package model;

/** Potion */
public class PowerStone extends Item {
  // Trigger the special skill effect once, but it does not start or change the cooldown timer. In
  // short, free extra use of the skill.

  public void use(Entity e) {
    Player p = (Player) e;
    // TODO: Do player specialSkill either directly or invoking controller
    // p.specialSkill(e);
  }
}
