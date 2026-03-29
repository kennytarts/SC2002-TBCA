package view;

import java.util.ArrayList;
import java.util.Scanner;

import model.Entity;
import model.Player;

public class GameCLI {
    private Scanner scanner;

    public GameCLI() {
        this.scanner = new Scanner(System.in);
    }

    public void showTurnHeader(Entity e) {
        System.out.println("\n--- " + e.getName() + "'s Turn ---");
    }

    public void showNoLongerStunned(Entity e) {
        System.out.println(e.getName() + " is no longer stunned next turn!");
    }

    public void showStunned(Entity e) {
        System.out.println(e.getName() + " is stunned and cannot move!");
    }

    public void showNoLongerInvulnerable(Entity e) {
        System.out.println(e.getName() + " smoke bomb effect wore off and is no longer invulnerable!");
    }

    public void showDefendWoreOff(Entity e) {
        System.out.println(e.getName() + " stopped defending!");
    }

    public void showDefeated(Entity e) {
        System.out.println(e.getName() + " is defeated!");
    }

    public void showPlayerDefeated(Player player) {
        System.out.println(player.getName() + " has been defeated!");
    }

    public void showEntityAttributes(Entity e) {
        e.viewAttr();
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
        System.out.println("2. Special Skill (Cooldown: " + player.getSpecialSkillCooldown() + ")");
        System.out.println("3. Defend");
        System.out.println("4. Use Item");
        return scanner.nextInt();
    }

    public void showBasicAttack(Player player, Entity target, int damage) {
        System.out.println(player.getName() + " dealt " + damage + " damage to " + target.getName() + "!");
    }

    public void showSkillCooldown(Player player) {
        System.out.println("Special skill is on cooldown for "
                + player.getSpecialSkillCooldown() + " more round(s).");
    }

    public void showShieldBash(Entity player, Entity target) {
        System.out.println(player.getName() + " used Shield Bash on " + target.getName() + "!");
    }

    public void showArcaneBlast(Entity player) {
        System.out.println(player.getName() + " used Arcane Blast!");
    }

    public void showNoSpecialSkill() {
        System.out.println("This player has no special skill.");
    }

    public void showDefending(Player player) {
        System.out.println(player.getName() + " is defending!");
    }

    public void showItemsNotImplemented() {
        System.out.println("Items not implemented yet.");
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
        System.out.println("Choose an item:");
        for (int i = 0; i < player.getItems().size(); i++) {
            System.out.println((i + 1) + ". " + player.getItems().get(i).getName());
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
}