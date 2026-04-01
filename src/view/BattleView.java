package view;

import java.util.ArrayList;

import model.CombatantInfo;
import model.Player;
import model.Status;

public class BattleView implements BattleDisplay {
    public BattleView() {
    }

    public void showTurnHeader(CombatantInfo entity) {
        System.out.println("\n--- " + entity.getName() + "'s Turn ---");
    }

    public void showStunned(CombatantInfo entity) {
        System.out.println(entity.getName() + " is stunned and cannot move!");
    }

    public void showNoLongerInvulnerable(CombatantInfo entity) {
        System.out.println(entity.getName() + " smoke bomb effect wore off and is no longer invulnerable!");
    }

    public void showDefendWoreOff(CombatantInfo entity) {
        System.out.println(entity.getName() + " stopped defending!");
    }

    public void showDefeated(CombatantInfo entity) {
        System.out.println(entity.getName() + " is defeated!");
    }

    public void showEntityAttributes(CombatantInfo entity) {
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

    public void showBasicAttack(Player player, CombatantInfo target, int damage) {
        System.out.println(player.getName() + " dealt " + damage + " damage to " + target.getName() + "!");
    }

    public void showSkillCooldown(Player player) {
        System.out.println(player.getSpecialSkillName() + " is on cooldown for "
                + player.getSpecialSkillCooldown() + " more round(s).");
    }

    public void showSpecialSkillUsedOnTarget(Player player, CombatantInfo target) {
        System.out.println(player.getName() + " used " + player.getSpecialSkillName() + " on " + target.getName() + "!");
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

    public void showEnemyInvulnerableBlocked(CombatantInfo player, CombatantInfo enemy) {
        System.out.println(player.getName() + " is invulnerable! "
                + enemy.getName() + " dealt 0 damage.");
    }

    public void showEnemyAttack(CombatantInfo enemy, CombatantInfo player, int damage) {
        System.out.println(enemy.getName() + " dealt " + damage + " damage to " + player.getName() + "!");
    }

    public void showItemUsed(String itemName) {
        System.out.println(itemName + " used!");
    }

    public void showNoItems() {
        System.out.println("No items available.");
    }

    private String formatStatuses(CombatantInfo entity) {
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
