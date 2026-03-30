package model;

import java.util.ArrayList;

public class Battle implements BattleContext {
    private final Player player;
    private ArrayList<Combatant> enemies;
    private int roundNumber;

    public Battle(Player player, ArrayList<Combatant> enemies) {
        this.player = player;
        this.enemies = new ArrayList<Combatant>(enemies);
        this.roundNumber = 1;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Combatant> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Combatant> enemies) {
        this.enemies = new ArrayList<Combatant>(enemies);
    }

    public ArrayList<Combatant> getCombatants() {
        ArrayList<Combatant> combatants = new ArrayList<Combatant>();
        combatants.add(player);
        combatants.addAll(enemies);
        return combatants;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void incrementRound() {
        roundNumber++;
    }

    public boolean isPlayerAlive() {
        return player.isAlive();
    }

    public boolean hasAliveEnemies() {
        for (Combatant enemy : enemies) {
            if (enemy.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public boolean isBattleOver() {
        return !isPlayerAlive() || !hasAliveEnemies();
    }

    public void removeDefeatedEnemies() {
        enemies.removeIf(enemy -> !enemy.isAlive());
    }
}
