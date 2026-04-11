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

    public void showLoadingScreenHeader() {
        System.out.println("\n========== LOADING SCREEN - INITIATION ==========");
    }

    public void showPlayersSection(ArrayList<Player> players) {
        System.out.println("\nList Players");
        int count = 1;
        for (Player player : players) {
            System.out.println(count + ". " + player.getName() + " - HP: " + player.getHp()
                    + ", Attack: " + player.getAtk()
                    + ", Defense: " + player.getDef()
                    + ", Speed: " + player.getSpd());
            count++;
        }
    }

    public void showItemsSection(ArrayList<String> itemNames) {
        System.out.println("\nOption to pick Item");
        System.out.println("Items Available (choose 2, duplicates allowed):");
        for (int i = 0; i < itemNames.size(); i++) {
            System.out.println((i + 1) + ". " + itemNames.get(i));
        }
    }

    public void showEnemiesSection(ArrayList<Combatant> enemies) {
        System.out.println("\nList Enemies");
        for (Combatant enemy : enemies) {
            System.out.println(enemy.getName() + " - HP: " + enemy.getHp()
                    + ", Attack: " + enemy.getAtk()
                    + ", Defense: " + enemy.getDef()
                    + ", Speed: " + enemy.getSpd());
        }
    }

    public void showLevelsSection(ArrayList<String> levelDescriptions) {
        System.out.println("\nList levels of difficulty");
        System.out.println("List number of enemy combatants by levels of difficulty");
        for (int i = 0; i < levelDescriptions.size(); i++) {
            System.out.println((i + 1) + ". " + levelDescriptions.get(i));
        }
        System.out.println();
    }

    public int choosePlayerSelection(ArrayList<Player> players) {
        System.out.println("Choose your character:");

        return InputHelper.readIntInRange(scanner, 1, players.size(), "Invalid choice. Please re-enter: ");
    }

    public int chooseItemsSelection(ArrayList<String> itemNames, int selectionNumber) {
        System.out.println("Choose item " + selectionNumber + " of 2:");

        return InputHelper.readIntInRange(scanner, 1, itemNames.size(), "Invalid choice. Please re-enter: ");
    }

    public int chooseLevelSelection(ArrayList<String> levelDescriptions) {
        System.out.println("Choose a difficulty level:");

        return InputHelper.readIntInRange(scanner, 1, levelDescriptions.size(), "Invalid choice. Please re-enter: ");
    }

    public void showRoundHeader(int round) {
        System.out.println("\n========== ROUND " + round + " ==========");
    }

    public void showRoundSummary(Player player, ArrayList<Combatant> enemies) {
        String specialSkillCD = player.getSpecialSkillCooldown() > 0 ? 
            "Cooldown: " + player.getSpecialSkillCooldown() + " turn(s) left" :
            "Available"; 
        System.out.println("\nRound Summary");
        System.out.println(player.getName() + " HP: " + player.getHp()
                + " | Status: " + formatStatuses(player)
                + " | Items: " + formatItems(player)
                + " | Special Skill " + specialSkillCD);

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
        System.out.println("Unused Items: " + formatItems(player));
    }

    public void showDefeat(int enemiesRemaining, int roundsSurvived, Player player) {
        System.out.println("\nDefeated. Don't give up, try again!");
        System.out.println("Enemies remaining: " + enemiesRemaining + " | Total Rounds Survived: " + roundsSurvived);
        System.out.println("Unused Items: " + formatItems(player));
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
            return "None";
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
