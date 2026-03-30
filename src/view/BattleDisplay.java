package view;

import model.CombatantInfo;
import model.Player;

public interface BattleDisplay {
    void showTurnHeader(CombatantInfo entity);
    void showStunned(CombatantInfo entity);
    void showNoLongerInvulnerable(CombatantInfo entity);
    void showDefendWoreOff(CombatantInfo entity);
    void showDefeated(CombatantInfo entity);
    void showEntityAttributes(CombatantInfo entity);
    void showEnemyInvulnerableBlocked(CombatantInfo player, CombatantInfo enemy);
    void showEnemyAttack(CombatantInfo enemy, CombatantInfo player, int damage);
    void showNoValidTargets();
    void showBasicAttack(Player player, CombatantInfo target, int damage);
    void showSkillCooldown(Player player);
    void showSpecialSkillUsedOnTarget(Player player, CombatantInfo target);
    void showSpecialSkillUsed(Player player);
    void showDefending(Player player);
    void showInvalidAction();
    void showItemUsed(String itemName);
    void showNoItems();
}
