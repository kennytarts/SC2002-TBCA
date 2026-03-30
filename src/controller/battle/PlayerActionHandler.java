package controller.battle;

import java.util.ArrayList;

import model.Battle;
import model.Entity;
import model.Item;
import model.Player;
import model.PowerStone;
import view.BattleView;

public class PlayerActionHandler {
    private final BattleView view;

    public PlayerActionHandler(BattleView view) {
        this.view = view;
    }

    public void executePlayerTurn(Player player, Battle battle) {
        ArrayList<Entity> enemies = battle.getEnemies();
        int action = view.choosePlayerAction(player);

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
                view.showDefending(player);
                break;
            case 4:
                executeUseItem(player, battle);
                break;
            default:
                view.showInvalidAction();
        }
    }

    private void executeBasicAttack(Player player, ArrayList<Entity> enemies) {
        Entity target = view.chooseTarget(enemies);

        if (target == null) {
            view.showNoValidTargets();
            return;
        }

        int damage = player.basicAttack(target);
        player.reduceSpecialSkillCooldown();
        view.showBasicAttack(player, target, damage);
    }

    private Entity chooseSpecialSkillTarget(Player player, ArrayList<Entity> enemies) {
        if (!player.needsSpecialSkillTarget()) {
            view.showSpecialSkillUsed(player);
            return null;
        }

        Entity target = view.chooseTarget(enemies);

        if (target == null) {
            view.showNoValidTargets();
            return null;
        }

        view.showSpecialSkillUsedOnTarget(player, target);
        return target;
    }

    private boolean executeSpecialSkill(Player player, Battle battle, boolean applyCooldown) {
        if (applyCooldown && !player.canUseSpecialSkill()) {
            view.showSkillCooldown(player);
            return false;
        }

        ArrayList<Entity> enemies = battle.getEnemies();
        Entity target = chooseSpecialSkillTarget(player, enemies);

        if (player.needsSpecialSkillTarget() && target == null) {
            return false;
        }

        boolean used = player.useSpecialSkill(target, enemies);

        if (applyCooldown && used) {
            player.startSpecialSkillCooldown();
        }

        return used;
    }

    private void executeUseItem(Player player, Battle battle) {
        if (player.getItems().isEmpty()) {
            view.showNoItems();
            return;
        }

        int itemIndex = view.chooseItem(player);

        if (itemIndex < 0 || itemIndex >= player.getItems().size()) {
            view.showInvalidAction();
            return;
        }

        Item item = player.getItem(itemIndex);

        if (item instanceof PowerStone) {
            executeSpecialSkill(player, battle, false);
        }

        item.use(player, battle.getEnemies());
        view.showItemUsed(item.getName());
        player.removeConsumedItems();
    }
}
