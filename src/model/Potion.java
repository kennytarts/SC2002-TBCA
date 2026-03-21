package model;

public class Potion extends Item {
  // When used, Heal 100HP New HP=min(Current HP+Heal Amount, Max HP)

  public void use(Entity e) {
    Player p = (Player) e;
    int healAmt = 100;
    int newHP = Math.min(p.getCurrentHP() + healAmt, p.getMaxHP());
    p.setCurrentHP(newHP);
  }
}
