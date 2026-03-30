package model;

import java.util.ArrayList;

public interface IItem {
    String getName();
    boolean isConsumed();
    void use(Entity user , ArrayList<Entity> enemies);
}