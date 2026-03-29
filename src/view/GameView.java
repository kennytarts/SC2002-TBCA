package view;

import model.Player;

/**
 * GameView: Handles game-level UI messages (player selection, level selection,
 * game flow)
 */
public class GameView {

    public void showInvalidPlayerSelection() {
        System.out.println("Invalid player selection. Please choose 1 (Warrior) or 2 (Wizard).");
    }

    public void showInvalidLevelSelection() {
        System.out.println("Invalid level selection. Please choose 1, 2, or 3.");
    }

    public void showRoundHeader(int round) {
        System.out.println("\n========== ROUND " + round + " ==========");
    }

    public void showDefeat(Player player) {
        System.out.println("\n" + player.getName() + " has been defeated! Game Over!");
    }

    public void showBackupEnemiesArrived() {
        System.out.println("\nBackup enemies have arrived!");
    }

    public void showVictory() {
        System.out.println("\nVictory! All enemies have been defeated!");
    }
}
