package model;

import java.util.ArrayList;

public interface CombatantInfo {
    int getHp();
    int getMaxHP();
    int getAtk();
    int getDef();
    int getSpd();
    String getName();
    ArrayList<Status> getStatuses();
    boolean isAlive();
}
