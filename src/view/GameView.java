package view;

import model.Player;

public class GameView {

    public void showInvalidPlayerSelection() {
        System.out.println("Invalid player selection.");
    }

    public void showInvalidLevelSelection() {
        System.out.println("Invalid level selection.");
    }

    public void showRoundHeader(int round) {
        System.out.println("\n========== ROUND " + round + " ==========");
    }

    public void showDefeat(Player player) {
        System.out.println("Defeat. " + player.getName() + " has fallen.");
    }

    public void showBackupEnemiesArrived() {
        System.out.println("\nBackup enemies have arrived!");
    }

    public void showVictory() {
        System.out.println("\nVictory! All enemies are defeated.");
    }
}