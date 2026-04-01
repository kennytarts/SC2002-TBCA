package view;

import java.util.ArrayList;
import java.util.Scanner;

import model.Combatant;
import model.Item;
import model.Player;

public class BattleInputView implements BattleInput {
    private final Scanner scanner;

    public BattleInputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public Combatant chooseTarget(ArrayList<Combatant> enemies) {
        ArrayList<Combatant> aliveTargets = new ArrayList<Combatant>();

        for (Combatant enemy : enemies) {
            if (enemy.isAlive()) {
                aliveTargets.add(enemy);
            }
        }

        if (aliveTargets.isEmpty()) {
            return null;
        }

        System.out.println("Select a target:");
        for (int i = 0; i < aliveTargets.size(); i++) {
            Combatant enemy = aliveTargets.get(i);
            System.out.println((i + 1) + ". " + enemy.getName() + " (HP: " + enemy.getHp() + ")");
        }

        int choice = scanner.nextInt() - 1;

        if (choice >= 0 && choice < aliveTargets.size()) {
            return aliveTargets.get(choice);
        }

        System.out.println("Invalid choice. Defaulting to first alive target.");
        return aliveTargets.get(0);
    }

    public int choosePlayerAction(Player player) {
        System.out.println("Choose action:");
        System.out.println("1. Basic Attack");
        System.out.println("2. " + player.getSpecialSkillName() + " (Cooldown: " + player.getSpecialSkillCooldown() + ")");
        System.out.println("3. Defend");
        System.out.println("4. Use Item");

        int action = scanner.nextInt();
        while (action < 1 || action > 4) {
            System.out.println("Invalid choice. Please re-enter");
            action = scanner.nextInt();
        }
        return action;
    }

    public int chooseItem(Player player) {
        ArrayList<Item> items = player.getItems();
        System.out.println("Choose an item:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getName());
        }
        int selected = scanner.nextInt();
        while (selected < 1 || selected > items.size()) {
            System.out.println("Invalid choice. Please re-enter");
            selected = scanner.nextInt();
        } 
        return selected - 1;
    }
}
