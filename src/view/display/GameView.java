package view.display;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import model.characters.Combatant;
import model.characters.Player;
import model.items.Item;
import model.status.Status;
import view.input.InputHelper;

public class GameView {
    private final Scanner scanner;

    public GameView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showLoadingScreen(ArrayList<Player> players, ArrayList<Item> items, ArrayList<Combatant> enemies) {
        System.out.println("\n========== TURN-BASED COMBAT ARENA ==========");
        System.out.println("Players:");
        for (Player player : players) {
            System.out.println(player.getName() + " - HP: " + player.getHp()
                    + ", Attack: " + player.getAtk()
                    + ", Defense: " + player.getDef()
                    + ", Speed: " + player.getSpd());
        }

        System.out.println("\nItems (choose 2, duplicates allowed):");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getName());
        }

        System.out.println("\nEnemies:");
        for (Combatant enemy : enemies) {
            System.out.println(enemy.getName() + " - HP: " + enemy.getHp()
                    + ", Attack: " + enemy.getAtk()
                    + ", Defense: " + enemy.getDef()
                    + ", Speed: " + enemy.getSpd());
        }

        System.out.println("\nDifficulty Levels:");
        System.out.println("1. Easy   - 3 enemies (3 Goblins)");
        System.out.println("2. Medium - 2 initial enemies (1 Goblin, 1 Wolf), 2 backup enemies (2 Wolves)");
        System.out.println("3. Hard   - 2 initial enemies (2 Goblins), 3 backup enemies (1 Goblin, 2 Wolves)");
        System.out.println();
    }

    public int choosePlayerSelection() {
        System.out.println("Choose your character:");
        System.out.println("1. Warrior");
        System.out.println("2. Wizard");

        return InputHelper.readIntInRange(scanner, 1, 2, "Invalid choice. Please re-enter: ");
    }

    public int chooseItemsSelection(ArrayList<Item> items) {
        System.out.println("Choose your item:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getName());
        }

        return InputHelper.readIntInRange(scanner, 1, items.size(), "Invalid choice. Please re-enter: ") - 1;
    }

    public int chooseLevelSelection() {
        System.out.println("Choose a difficulty level:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");

        return InputHelper.readIntInRange(scanner, 1, 3, "Invalid choice. Please re-enter: ");
    }

    public void showRoundHeader(int round) {
        System.out.println("\n========== ROUND " + round + " ==========");
    }

    public void showRoundSummary(Player player, ArrayList<Combatant> enemies) {
        System.out.println("\nRound Summary");
        System.out.println(player.getName() + " HP: " + player.getHp()
                + " | Status: " + formatStatuses(player)
                + " | Items: " + formatItems(player)
                + " | Skill Cooldown: " + player.getSpecialSkillCooldown());

        for (Combatant enemy : enemies) {
            String state = enemy.isAlive() ? "Alive" : "Eliminated";
            System.out.println(enemy.getName() + " HP: " + enemy.getHp()
                    + " | Status: " + formatStatuses(enemy)
                    + " | " + state);
        }
    }

    public void showBackupEnemiesArrived() {
        System.out.println("\nBackup enemies have arrived!");
    }

    public void showVictory(Player player, int totalRounds) {
        System.out.println("\nCongratulations, you have defeated all your enemies.");
        System.out.println("Remaining HP: " + player.getHp() + " | Total Rounds: " + totalRounds);
    }

    public void showDefeat(int enemiesRemaining, int roundsSurvived) {
        System.out.println("\nDefeated. Don't give up, try again!");
        System.out.println("Enemies remaining: " + enemiesRemaining + " | Total Rounds Survived: " + roundsSurvived);
    }

    public int choosePostGameOption() {
        System.out.println("\nChoose an option:");
        System.out.println("1. Replay with the same settings");
        System.out.println("2. Start a new game");
        System.out.println("3. Exit");

        return InputHelper.readIntInRange(scanner, 1, 3, "Invalid choice. Please re-enter: ");
    }

    private String formatItems(Player player) {
        if (player.getItems().isEmpty()) {
            return "NONE";
        }

        Map<String, Integer> itemCounts = new LinkedHashMap<String, Integer>();
        for (Item item : player.getItems()) {
            itemCounts.put(item.getName(), itemCounts.getOrDefault(item.getName(), 0) + 1);
        }

        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, Integer> entry : itemCounts.entrySet()) {
            if (!first) {
                builder.append(", ");
            }
            builder.append(entry.getKey()).append(" x").append(entry.getValue());
            first = false;
        }
        return builder.toString();
    }

    private String formatStatuses(Combatant combatant) {
        ArrayList<Status> statuses = combatant.getStatuses();
        if (statuses.isEmpty()) {
            return "NONE";
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < statuses.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(statuses.get(i).getEffect()).append("(").append(statuses.get(i).getDuration()).append(")");
        }
        return builder.toString();
    }
}
