package model.characters;

import model.status.Status;
import model.status.StatusAffected;
import model.status.StatusEffects;

public interface Combatant extends CombatantInfo, StatusAffected {
    boolean hasStatus(StatusEffects effect);
    void addStatus(Status newStatus);
    int basicAttack(Combatant target);
    int takeDamage(int damage);
    void heal(int amount);
    void changeAttack(int amount);
    void changeSpeed(int amount);
}
