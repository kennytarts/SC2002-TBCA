package controller.battle;

import java.util.ArrayList;

import model.Battle;
import model.Entity;
import model.Item;
import model.Player;
import model.PowerStone;
import view.BattleView;
import model.Wizard;
import model.Warrior;

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
                executeSpecialSkill(player, battle, true);
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

    private ArrayList<Entity> handleSpecialSkillEnemy(Player player, ArrayList<Entity> enemies, BattleView view) {
        if (player instanceof Warrior) {
            Entity target = view.chooseTarget(enemies);
            ArrayList<Entity> targets = new ArrayList<Entity>();
            targets.add(target);
            view.showShieldBash(player, target);
            return targets;

        }
        else if (player instanceof Wizard) {
            view.showArcaneBlast(player);
            return enemies;
        }
        return enemies;

    } 

    private void executeSpecialSkill(Player player, Battle battle, boolean cooldown) {
        if (cooldown && !player.canUseSpecialSkill()) {
            view.showSkillCooldown(player);
            return;
        }
        
        ArrayList<Entity> enemies = handleSpecialSkillEnemy(player, battle.getEnemies(), view);
        boolean used = player.useSpecialSkill(enemies);

        if (cooldown && used) {
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
            // boolean used = player.useSpecialSkill(enemies, view);
            executeSpecialSkill(player, battle, false);
        }

        item.use(player, enemies);
        view.showItemUsed(item.getName());
        player.removeConsumedItems();
    }
}