package model.characters;

import java.util.ArrayList;

import model.status.Status;

public interface CombatantInfo {
    int getHp();
    int getMaxHp();
    int getAtk();
    int getDef();
    int getSpd();
    String getName();
    ArrayList<Status> getStatuses();
    boolean isAlive();
}
