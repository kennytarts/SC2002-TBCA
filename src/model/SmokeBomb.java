package model;

public class SmokeBomb extends Item {
  // When used, Enemy attacks do 0 damage in the current turn and the next turn

  public void use(Entity e) {
    Enemy enemy = (Enemy) e;
    // TODO: Set duration either controller or another variable or status?
    enemy.setAtk(0);
  }
}
