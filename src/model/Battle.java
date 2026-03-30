package model;

import java.util.ArrayList;

public class Battle {
    private final Player player;
    private ArrayList<Entity> enemies;
    private int roundNumber;

    public Battle(Player player, ArrayList<Entity> enemies) {
        this.player = player;
        this.enemies = new ArrayList<Entity>(enemies);
        this.roundNumber = 1;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Entity> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Entity> enemies) {
        this.enemies = new ArrayList<Entity>(enemies);
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
        for (Entity enemy : enemies) {
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
