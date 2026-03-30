package controller.battle;

import java.util.ArrayList;

import model.BattleContext;
import model.Combatant;
import model.Item;
import model.Player;
import model.PowerStone;
import view.BattleDisplay;
import view.BattleInput;

public class PlayerActionHandler implements CombatantTurnHandler {
    private final BattleDisplay display;
    private final BattleInput input;

    public PlayerActionHandler(BattleDisplay display, BattleInput input) {
        this.display = display;
        this.input = input;
    }

    public void executeTurn(Combatant actor, BattleContext battle) {
        Player player = (Player) actor;
        ArrayList<Combatant> enemies = battle.getEnemies();
        int action = input.choosePlayerAction(player);

        switch (action) {
            case 1:
                executeBasicAttack(player, enemies);
                break;
            case 2:
                executeSpecialSkill(player, battle, true);
                player.reduceSpecialSkillCooldown();
                break;
            case 3:
                player.defend();
                player.reduceSpecialSkillCooldown();
                display.showDefending(player);
                break;
            case 4:
                executeUseItem(player, battle);
                break;
            default:
                display.showInvalidAction();
        }
    }

    private void executeBasicAttack(Player player, ArrayList<Combatant> enemies) {
        Combatant target = input.chooseTarget(enemies);

        if (target == null) {
            display.showNoValidTargets();
            return;
        }

        int damage = player.basicAttack(target);
        player.reduceSpecialSkillCooldown();
        display.showBasicAttack(player, target, damage);
    }

    private Combatant chooseSpecialSkillTarget(Player player, ArrayList<Combatant> enemies) {
        if (!player.needsSpecialSkillTarget()) {
            display.showSpecialSkillUsed(player);
            return null;
        }

        Combatant target = input.chooseTarget(enemies);

        if (target == null) {
            display.showNoValidTargets();
            return null;
        }

        display.showSpecialSkillUsedOnTarget(player, target);
        return target;
    }

    private boolean executeSpecialSkill(Player player, BattleContext battle, boolean applyCooldown) {
        if (applyCooldown && !player.canUseSpecialSkill()) {
            display.showSkillCooldown(player);
            return false;
        }

        ArrayList<Combatant> enemies = battle.getEnemies();
        Combatant target = chooseSpecialSkillTarget(player, enemies);

        if (player.needsSpecialSkillTarget() && target == null) {
            return false;
        }

        boolean used = player.useSpecialSkill(target, enemies);

        if (applyCooldown && used) {
            player.startSpecialSkillCooldown();
        }

        return used;
    }

    private void executeUseItem(Player player, BattleContext battle) {
        if (player.getItems().isEmpty()) {
            display.showNoItems();
            return;
        }

        int itemIndex = input.chooseItem(player);

        if (itemIndex < 0 || itemIndex >= player.getItems().size()) {
            display.showInvalidAction();
            return;
        }

        Item item = player.getItem(itemIndex);

        if (item instanceof PowerStone) {
            executeSpecialSkill(player, battle, false);
        }

        item.use(player, battle.getEnemies());
        display.showItemUsed(item.getName());
        player.removeConsumedItems();
    }

    public boolean supports(Combatant actor) {
        return actor instanceof Player;
    }
}
