package model;

import java.util.ArrayList;

public interface BattleContext {
    ArrayList<Combatant> getCombatants();
    ArrayList<Combatant> getEnemies();
    Combatant getPlayer();
    boolean hasAliveEnemies();
    boolean isBattleOver();
    void removeDefeatedEnemies();
    void setEnemies(ArrayList<Combatant> enemies);
}
