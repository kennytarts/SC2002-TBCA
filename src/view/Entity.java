public abstract class Entity {
    private int hp;
    private int attack;
    private int defense;
    private int speed;
    private String name;

    public int getHp() {
        return hp;
    }

    public void setHp(int val) {
        hp = val;
    }

    public int getAtk() {
        return attack;
    }

    public void setAtk(int val) {
        attack = val;
    }

    public int getDef() {
        return defense;
    }

    public void setDef(int val) {
        defense = val;
    }

    public int getSpd() {
        return speed;
    }

    public void setSpd(int val) {
        speed = val;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int takeDamage(int damage) {
        hp -= damage;
        return hp;
    }

    public int basicAttack(Entity e) {
        int damage = Math.max(0, this.attack - e.getDef());
        takeDamage(damage);
        return damage;
    }

}