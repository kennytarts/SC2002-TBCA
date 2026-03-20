import java.util.ArrayList;

public abstract class Player extends Entity implements ISpecialSkill{

    //TODO Integet is placeholder for Item class
    private ArrayList<Integer> itemList;

    // TODO set for 2 rounds only
    public void defend() {
        setDef(getDef() + 10);
        setStatus(Status.defend());
    }

}