public class Status {
    private int duration = 0;
    private int status = 0; // 0 for normal, 1 for stun

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
