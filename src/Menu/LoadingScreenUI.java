package Menu;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class GameConfig {
    String playerClass;
    List<String> selectedItems = new ArrayList<>();
    int difficultyLevel;
}

public class LoadingScreenUI {
    private Scanner scanner;

    public LoadingScreenUI() {
        this.scanner = new Scanner(System.in);
    }

    public GameConfig startSetup() {
        GameConfig config = new GameConfig();
        System.out.println("=========================================");
        System.out.println("    WELCOME TO THE TURN-BASED ARENA      ");
        System.out.println("=========================================\n");

        config.playerClass = selectPlayer();
        config.selectedItems = selectItems();
        config.difficultyLevel = selectDifficulty();

        System.out.println("\n[System] Game Setup Complete. Initializing Battle...");
        return config;
    }

    private String selectPlayer() {
        System.out.println("--- STEP 1: SELECT YOUR HERO ---");
        System.out.println("1. Warrior");
        System.out.println("   HP: 260 | Attack: 40 | Defense: 20 | Speed: 30");
        System.out.println("   Special Skill: Shield Bash (Stuns enemy for 2 turns)");
        System.out.println("2. Wizard");
        System.out.println("   HP: 200 | Attack: 50 | Defense: 10 | Speed: 20");
        System.out.println("   Special Skill: Arcane Blast (AoE damage, +10 ATK per kill)");
        
        int choice = getValidInput("Enter 1 for Warrior or 2 for Wizard: ", 1, 2);
        return choice == 1 ? "Warrior" : "Wizard";
    }

    private List<String> selectItems() {
        List<String> items = new ArrayList<>();
        System.out.println("\n--- STEP 2: SELECT YOUR ITEMS ---");
        System.out.println("You can pick 2 single-use items (duplicates allowed).");
        System.out.println("1. Potion (Heals 100 HP)");
        System.out.println("2. Power Stone (Free trigger of special skill effect)");
        System.out.println("3. Smoke Bomb (Enemy attacks do 0 damage for 2 turns)");

        for (int i = 1; i <= 2; i++) {
            int choice = getValidInput("Select Item " + i + " (1-3): ", 1, 3);
            switch (choice) {
                case 1 -> items.add("Potion");
                case 2 -> items.add("Power Stone");
                case 3 -> items.add("Smoke Bomb");
            }
        }
        return items;
    }

    private int selectDifficulty() {
        System.out.println("\n--- STEP 3: SELECT DIFFICULTY ---");
        System.out.println("Enemy Attributes:");
        System.out.println(" - Goblin -> HP: 55 | Attack: 35 | Defense: 15 | Speed: 25");
        System.out.println(" - Wolf   -> HP: 40 | Attack: 45 | Defense: 5  | Speed: 35\n");
        
        System.out.println("Levels:");
        System.out.println("1. Easy   (Initial Spawn: 3 Goblins)");
        System.out.println("2. Medium (Initial Spawn: 1 Goblin, 1 Wolf | Backup: 2 Wolves)");
        System.out.println("3. Hard   (Initial Spawn: 2 Goblins | Backup: 1 Goblin, 2 Wolves)");

        return getValidInput("Select Difficulty (1-3): ", 1, 3);
    }

    private int getValidInput(String prompt, int min, int max) {
        int input = -1;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input >= min && input <= max) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a number between " + min + " and " + max + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
            }
        }
        return input;
    }
}