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

        int choice = InputHelper.readIntInRange(scanner, 1, aliveTargets.size(), "Invalid choice. Please re-enter") - 1;
        return aliveTargets.get(choice);
    }

    public int choosePlayerAction(Player player) {
        System.out.println("Choose action:");
        System.out.println("1. Basic Attack");
        System.out.println("2. " + player.getSpecialSkillName() + " (Cooldown: " + player.getSpecialSkillCooldown() + ")");
        System.out.println("3. Defend");
        System.out.println("4. Use Item");

        while (true) {
            int action = InputHelper.readIntInRange(scanner, 1, 4, "Invalid choice. Please re-enter");

            if (action == 2 && !player.canUseSpecialSkill()) {
                System.out.println(player.getSpecialSkillName() + " is on cooldown. Please choose another action.");
                continue;
            }

            if (action == 4 && player.getItems().isEmpty()) {
                System.out.println("No items left to use. Please choose another action.");
                continue;
            }

            return action;
        }
    }

    public int chooseItem(Player player) {
        ArrayList<Item> items = player.getItems();
        System.out.println("Choose an item:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getName());
        }
        return InputHelper.readIntInRange(scanner, 1, items.size(), "Invalid choice. Please re-enter") - 1;
    }
}
