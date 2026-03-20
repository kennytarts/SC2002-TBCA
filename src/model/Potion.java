package model;

public class Potion extends Item {
  // When used, Heal 100HP New HP=min(Current HP+Heal Amount, Max HP)

  // TODO: Add Player Param
  public void use() {
    // TODO: Change 1 to p.getHP (current HP) and 2 to maxHP
    int healAmt = 100;
    int newHP = Math.min(1 + healAmt, 2);
    // p.setHP(newHP);
  }
}
