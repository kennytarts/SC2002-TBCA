public abstract class Entity {
    private int hp;
    private int attack;
    private int defense;
    private int speed;
    private String name;
    private Status status = new Status(StatusEffects.NONE);

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int basicAttack(Entity e) {
        int damage = Math.max(0, this.attack - e.getDef());
        if (damage > e.getHp()) {
            e.setHp(0);
        }
        else{
            e.setHp(e.getHp()-damage);
        }
        
        return damage;
    }

    public void viewAttr() {
        System.out.println(name+'\n'+"HP: "+hp+'\n'+"Attack: "+attack+'\n'+"Defense: "+defense+'\n'+"Speed: "+speed+'\n'+"Status: "+status.getStatus()+'\n');
    }

}