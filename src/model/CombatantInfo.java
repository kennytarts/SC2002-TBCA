package model;

import java.util.ArrayList;

public interface CombatantInfo {
    int getHp();
    int getAtk();
    int getDef();
    int getSpd();
    String getName();
    ArrayList<Status> getStatuses();
    boolean isAlive();
}
