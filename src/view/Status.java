public class Status {
    private int duration = StatusEffects.NONE.getDuration();
    private StatusEffects status = StatusEffects.NONE;

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
}
