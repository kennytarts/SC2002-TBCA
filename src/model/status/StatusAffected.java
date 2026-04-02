package model.status;

public interface StatusAffected {
    Status getStatus(StatusEffects effect);
    void removeStatus(StatusEffects effect);
    void changeDefense(int amount);
}
