package model;

import java.util.ArrayList;

/** Potion */
public class PowerStone extends Item {
  // Trigger the special skill effect once, but it does not start or change the
  // cooldown timer. In
  // short, free extra use of the skill.
  public PowerStone() {
    super("Power Stone");
  }

  // TODO: Add Player Param
  public void use(Entity user, ArrayList<Entity> enemies) {
    // TODO: Do player specialSkill either directly or invoking controller
    // p.specialSkill()
    // BattleView view = new BattleView();
    // if (user instanceof Warrior) {
    // Entity target = view.chooseTarget(enemies);
    // if (target == null) {
    // view.showNoValidTargets();
    // return;
    // }

    // ArrayList<Entity> targets = new ArrayList<Entity>();
    // targets.add(target);
    // ((Warrior) user).specialSkill(targets);
    // view.showShieldBash(user, target);
    // }

    // else if (user instanceof Wizard) {
    // ((Wizard) user).specialSkill(enemies);
    // view.showArcaneBlast(user);
    // }
    consumed();
  }
}
