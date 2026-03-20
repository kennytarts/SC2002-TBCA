public enum StatusEffects {
    NONE(0),
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