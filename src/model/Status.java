public class Status {
    private int duration = StatusEffects.NONE.getDuration();
    private StatusEffects status = StatusEffects.NONE;

    public Status(StatusEffects status) {
        this.status = status;
        this.duration = status.getDuration();
    }

    public StatusEffects getStatus() {
        return status;
    }

    public void setStatus(StatusEffects status) {
        this.status = status;
        duration = status.getDuration();
    }

    public int getDuration() {
        return duration;
    }

    public void decrementDuration() {
        if (duration > 0) {
            duration--;
        }
        if (duration == 0) {
            status = StatusEffects.NONE;
        }
    }

    public static Status stun() {
        return new Status(StatusEffects.STUN);
    }

    public static Status defend() {
        return new Status(StatusEffects.DEFEND);
    }
}
