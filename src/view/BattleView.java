package view;

import java.util.ArrayList;
import java.util.Scanner;

import model.Entity;
import model.Item;
import model.Player;
import model.Status;

public class BattleView {
    private final Scanner scanner;

    public BattleView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showTurnHeader(Entity entity) {
        System.out.println("\n--- " + entity.getName() + "'s Turn ---");
    }

    public void showStunned(Entity entity) {
        System.out.println(entity.getName() + " is stunned and cannot move!");
    }

    public void showNoLongerInvulnerable(Entity entity) {
        System.out.println(entity.getName() + " smoke bomb effect wore off and is no longer invulnerable!");
    }

    public void showDefendWoreOff(Entity entity) {
        System.out.println(entity.getName() + " stopped defending!");
    }

    public void showDefeated(Entity entity) {
        System.out.println(entity.getName() + " is defeated!");
    }

    public void showEntityAttributes(Entity entity) {
        System.out.println(entity.getName());
        System.out.println("HP: " + entity.getHp());
        System.out.println("Attack: " + entity.getAtk());
        System.out.println("Defense: " + entity.getDef());
        System.out.println("Speed: " + entity.getSpd());
        System.out.println("Status: " + formatStatuses(entity));
        System.out.println();
    }

    public void showNoValidTargets() {
        System.out.println("No valid targets.");
    }

    public void showInvalidChoiceDefaultTarget() {
        System.out.println("Invalid choice. Defaulting to first alive target.");
    }

    public Entity chooseTarget(ArrayList<Entity> enemies) {
        ArrayList<Entity> aliveTargets = new ArrayList<Entity>();

        for (Entity enemy : enemies) {
            if (enemy.isAlive()) {
                aliveTargets.add(enemy);
            }
        }

        if (aliveTargets.isEmpty()) {
            return null;
        }

        System.out.println("Select a target:");
        for (int i = 0; i < aliveTargets.size(); i++) {
            Entity enemy = aliveTargets.get(i);
            System.out.println((i + 1) + ". " + enemy.getName() + " (HP: " + enemy.getHp() + ")");
        }

        int choice = scanner.nextInt() - 1;

        if (choice >= 0 && choice < aliveTargets.size()) {
            return aliveTargets.get(choice);
        }

        showInvalidChoiceDefaultTarget();
        return aliveTargets.get(0);
    }

    public int choosePlayerAction(Player player) {
        System.out.println("Choose action:");
        System.out.println("1. Basic Attack");
        System.out.println("2. " + player.getSpecialSkillName()
                + " (Cooldown: " + player.getSpecialSkillCooldown() + ")");
        System.out.println("3. Defend");
        System.out.println("4. Use Item");
        return scanner.nextInt();
    }

    public void showBasicAttack(Player player, Entity target, int damage) {
        System.out.println(player.getName() + " dealt " + damage + " damage to " + target.getName() + "!");
    }

    public void showSkillCooldown(Player player) {
        System.out.println(player.getSpecialSkillName() + " is on cooldown for "
                + player.getSpecialSkillCooldown() + " more round(s).");
    }

    public void showSpecialSkillUsedOnTarget(Player player, Entity target) {
        System.out.println(player.getName() + " used " + player.getSpecialSkillName()
                + " on " + target.getName() + "!");
    }

    public void showSpecialSkillUsed(Player player) {
        System.out.println(player.getName() + " used " + player.getSpecialSkillName() + "!");
    }

    public void showDefending(Player player) {
        System.out.println(player.getName() + " is defending!");
    }

    public void showInvalidAction() {
        System.out.println("Invalid action.");
    }

    public void showEnemyInvulnerableBlocked(Player player, Entity enemy) {
        System.out.println(player.getName() + " is invulnerable! "
                + enemy.getName() + " dealt 0 damage.");
    }

    public void showEnemyAttack(Entity enemy, Player player, int damage) {
        System.out.println(enemy.getName() + " dealt " + damage + " damage to " + player.getName() + "!");
    }

    public void showItems(Player player) {
        ArrayList<Item> items = player.getItems();
        System.out.println("Choose an item:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getName());
        }
    }

    public int chooseItem(Player player) {
        showItems(player);
        return scanner.nextInt() - 1;
    }

    public void showItemUsed(String itemName) {
        System.out.println(itemName + " used!");
    }

    public void showNoItems() {
        System.out.println("No items available.");
    }

    private String formatStatuses(Entity entity) {
        ArrayList<Status> statuses = entity.getStatuses();

        if (statuses.isEmpty()) {
            return "NONE";
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < statuses.size(); i++) {
            Status status = statuses.get(i);
            builder.append(status.getEffect()).append("(").append(status.getDuration()).append(")");
            if (i < statuses.size() - 1) {
                builder.append(", ");
            }
        }

        return builder.toString();
    }
}
