package model;

public enum StatusEffects {
    STUN(2),
    DEFEND(2),
    INVULNERABLE(2);

    private final int duration;

    StatusEffects(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
}