package view;

import java.util.ArrayList;
import java.util.Scanner;

import model.Entity;
import model.Player;

/**
 * GameCLI: Unified view for all game UI interactions.
 * Consolidates all menu displays, prompts, and messages in one place.
 * Easy to replace entirely if you want to switch to GUI or web UI.
 */
public class GameCLI {
    private Scanner scanner;

    public GameCLI() {
        this.scanner = new Scanner(System.in);
    }

    // ============== GAME FLOW MESSAGES ==============

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
        System.out.println("Defeat. " + player.getName() + " has fallen.");
    }

    public void showBackupEnemiesArrived() {
        System.out.println("\nBackup enemies have arrived!");
    }

    public void showVictory() {
        System.out.println("\nVictory! All enemies are defeated.");
    }

    // ============== TURN MESSAGES ==============

    public void showTurnHeader(Entity e) {
        System.out.println("\n--- " + e.getName() + "'s Turn ---");
    }

    public void showEntityAttributes(Entity e) {
        e.viewAttr();
    }

    // ============== STATUS EFFECT MESSAGES ==============

    public void showStunned(Entity e) {
        System.out.println(e.getName() + " is stunned and cannot move!");
    }

    public void showNoLongerStunned(Entity e) {
        System.out.println(e.getName() + " is no longer stunned!");
    }

    public void showNoLongerInvulnerable(Entity e) {
        System.out.println(e.getName() + " is no longer invulnerable!");
    }

    public void showDefendWoreOff(Entity e) {
        System.out.println(e.getName() + "'s defend wore off!");
    }

    public void showDefeated(Entity e) {
        System.out.println(e.getName() + " is defeated!");
    }

    public void showPlayerDefeated(Player player) {
        System.out.println(player.getName() + " has been defeated!");
    }

    // ============== ACTION PROMPTS ==============

    public int choosePlayerAction(Player player) {
        System.out.println("\nChoose action:");
        System.out.println("1. Basic Attack");
        System.out.println("2. Special Skill (Cooldown: " + player.getSpecialSkillCooldown() + ")");
        System.out.println("3. Defend");
        System.out.println("4. Use Item");
        System.out.print("> ");
        return scanner.nextInt();
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

        System.out.println("\nSelect a target:");
        for (int i = 0; i < aliveTargets.size(); i++) {
            Entity enemy = aliveTargets.get(i);
            System.out.println((i + 1) + ". " + enemy.getName() + " (HP: " + enemy.getHp() + ")");
        }
        System.out.print("> ");

        int choice = scanner.nextInt() - 1;

        if (choice >= 0 && choice < aliveTargets.size()) {
            return aliveTargets.get(choice);
        }

        System.out.println("Invalid choice. Defaulting to first target.");
        return aliveTargets.get(0);
    }

    public int chooseItem(Player player) {
        System.out.println("\nChoose an item:");
        for (int i = 0; i < player.getItems().size(); i++) {
            System.out.println((i + 1) + ". " + player.getItems().get(i).getName());
        }
        System.out.print("> ");
        return scanner.nextInt() - 1;
    }

    // ============== ACTION FEEDBACK ==============

    public void showBasicAttack(Player player, Entity target, int damage) {
        System.out.println(player.getName() + " dealt " + damage + " damage to " + target.getName() + "!");
    }

    public void showDefending(Player player) {
        System.out.println(player.getName() + " is defending!");
    }

    public void showSkillCooldown(Player player) {
        System.out.println("Special skill is on cooldown for " + player.getSpecialSkillCooldown() + " more round(s).");
    }

    public void showShieldBash(Entity player, Entity target) {
        System.out.println(player.getName() + " used Shield Bash on " + target.getName() + "!");
    }

    public void showArcaneBlast(Entity player) {
        System.out.println(player.getName() + " used Arcane Blast!");
    }

    public void showItemUsed(String itemName) {
        System.out.println(itemName + " was used!");
    }

    public void showEnemyAttack(Entity enemy, Player player, int damage) {
        System.out.println(enemy.getName() + " dealt " + damage + " damage to " + player.getName() + "!");
    }

    public void showEnemyInvulnerableBlocked(Player player, Entity enemy) {
        System.out.println(player.getName() + " is invulnerable! " + enemy.getName() + " dealt 0 damage.");
    }

    // ============== ERROR MESSAGES ==============

    public void showNoValidTargets() {
        System.out.println("No valid targets.");
    }

    public void showInvalidAction() {
        System.out.println("Invalid action.");
    }

    public void showNoItems() {
        System.out.println("No items available.");
    }

    public void showNoSpecialSkill() {
        System.out.println("This player has no special skill.");
    }
}
