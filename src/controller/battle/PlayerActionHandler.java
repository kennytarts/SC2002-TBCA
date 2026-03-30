package controller.battle;

import java.util.ArrayList;

import model.Battle;
import model.Entity;
import model.Item;
import model.Player;
import model.PowerStone;
import view.BattleView;

public class PlayerActionHandler {
    private BattleView view;

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
                executeSpecialSkill(player, battle);
                break;
            case 3:
                player.defend();
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
        view.showBasicAttack(player, target, damage);
    }

    private void executeSpecialSkill(Player player, Battle battle) {
        if (!player.canUseSpecialSkill()) {
            view.showSkillCooldown(player);
            return;
        }

        boolean used = player.useSpecialSkill(battle.getEnemies(), view);

        if (used) {
            player.startSpecialSkillCooldown();
        }
    }

    private void executeUseItem(Player player, Battle battle) {
        ArrayList<Entity> enemies = battle.getEnemies();

        if (player.getItems().isEmpty()) {
            view.showNoItems();
            return;
        }

        int itemIndex = view.chooseItem(player);

        if (itemIndex < 0 || itemIndex >= player.getItems().size()) {
            view.showInvalidAction();
            return;
        }

        Item item = player.getItems().get(itemIndex);

        if (item instanceof PowerStone) {
            boolean used = player.useSpecialSkill(enemies, view);

            if (used) {
                item.use(player, enemies);
                view.showItemUsed(item.getName());
                player.removeConsumedItems();
            }
            return;
        }

        item.use(player, enemies);
        view.showItemUsed(item.getName());
        player.removeConsumedItems();
    }
}