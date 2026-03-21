package model;

import java.util.ArrayList;

public class Potion extends Item {
  // When used, Heal 100HP New HP=min(Current HP+Heal Amount, Max HP)
    public Potion() {
      super("Potion");
    }

  // TODO: Add Player Param
  public void use(Entity user, ArrayList<Entity> enemies) {
    // TODO: Change 1 to p.getHP (current HP) and 2 to maxHP
    int healAmt = 100;
    int newHP = Math.min(user.getMaxHP(), user.getHp() + healAmt);
    user.setHp(newHP);
    consumed();
  }
}
