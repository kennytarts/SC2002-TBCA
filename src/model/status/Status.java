package model.status;

public class Status {
    private StatusEffects effect;
    private int duration;

    public Status(StatusEffects effect) {
        this.effect = effect;
        this.duration = effect.getDuration();
    }

    public StatusEffects getEffect() {
        return effect;
    }

    public int getDuration() {
        return duration;
    }

    public void decrementDuration() {
        if (duration > 0) {
            duration--;
        }
    }

    public boolean isExpired() {
        return duration <= 0;
    }

    public void refresh() {
        this.duration = effect.getDuration();
    }

    public static Status stun() {
        return new Status(StatusEffects.STUN);
    }

    public static Status defend() {
        return new Status(StatusEffects.DEFEND);
    }

    public static Status invulnerable() {
        return new Status(StatusEffects.INVULNERABLE);
    }

    public static Status speed() {
        return new Status(StatusEffects.SPEED);
    }
}
