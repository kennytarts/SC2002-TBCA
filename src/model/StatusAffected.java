package model;

public interface StatusAffected extends CombatantInfo {
    Status getStatus(StatusEffects effect);
    void removeStatus(StatusEffects effect);
    void changeDefense(int amount);
}
