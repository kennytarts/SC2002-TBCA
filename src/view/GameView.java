package view;

import java.util.Scanner;

import model.Player;

public class GameView {
    private final Scanner scanner;

    public GameView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int choosePlayerSelection() {
        System.out.println("Choose your character:");
        System.out.println("1. Warrior");
        System.out.println("2. Wizard");
        return scanner.nextInt();
    }

    public int chooseLevelSelection() {
        System.out.println("Choose a level:");
        System.out.println("1. Level 1");
        System.out.println("2. Level 2");
        System.out.println("3. Level 3");
        return scanner.nextInt();
    }

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
        System.out.println("\n" + player.getName() + " has been defeated! Game Over!");
    }

    public void showBackupEnemiesArrived() {
        System.out.println("\nBackup enemies have arrived!");
    }

    public void showVictory() {
        System.out.println("\nVictory! All enemies have been defeated!");
    }
}
