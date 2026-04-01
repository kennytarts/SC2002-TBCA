package view;

import java.util.ArrayList;
import java.util.Scanner;

import model.Player;
import model.Item;

public class GameView {
    private final Scanner scanner;

    public GameView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int choosePlayerSelection() {
        System.out.println("Choose your character:");
        System.out.println("1. Warrior");
        System.out.println("2. Wizard");

        int in = scanner.nextInt();
        while (in < 1 || in > 2) {
            System.out.println("Invalid choice. Please re-enter: ");
            in = scanner.nextInt();
        }
        return in;
    }

    public int chooseItemsSelection(ArrayList<Item> items) {
        System.out.println("Choose your item: ");
        for (int i = 0; i < items.size(); i++) {
            System.out.println(i+1+". "+items.get(i).getName());
        }

        int in = scanner.nextInt();
        while (in < 1 || in > items.size()) {
            System.out.println("Invalid choice. Please re-enter: ");
            in = scanner.nextInt();
        }
        return in - 1;
    }

    public int chooseLevelSelection() {
        System.out.println("Choose a level:");
        System.out.println("1. Level 1");
        System.out.println("2. Level 2");
        System.out.println("3. Level 3");

        int in = scanner.nextInt();
        while (in < 1 || in > 3) {
            System.out.println("Invalid choice. Please re-enter: ");
            in = scanner.nextInt();
        }
        return in;
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
