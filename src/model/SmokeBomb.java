package model;

import java.util.ArrayList;

public class SmokeBomb extends Item {
  // When used, Enemy attacks do 0 damage in the current turn and the next turn
    public SmokeBomb() {
      super("Smoke Bomb");
    }

  public void use(Entity user, ArrayList<Entity> enemies) {
      user.addStatus(Status.invulnerable());
      consumed();
  }
}
