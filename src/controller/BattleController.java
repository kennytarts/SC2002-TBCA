package controller;

import java.util.ArrayList;

import model.Entity;
import model.Item;
import model.Player;
import model.PowerStone;
import model.Status;
import model.StatusEffects;
import model.Warrior;
import model.Wizard;
import view.BattleView;


public class BattleController {
    private Player player;
    private ArrayList<Entity> currentEnemies;
    private BattleView view;

    public BattleController(Player player, ArrayList<Entity> enemies, BattleView view) {
        this.player = player;
        this.currentEnemies = enemies;
        this.view = view;
    }

    private ArrayList<Entity> getTurnOrder() {
        ArrayList<Entity> order = new ArrayList<Entity>();

        order.add(player);

        for (Entity e : currentEnemies) {
            if (e != null && e.isAlive()) {
                order.add(e);
            }
        }

        // sort by speed descending
        order.sort((a, b) -> Integer.compare(b.getSpd(), a.getSpd()));
        return order;
    }

    private boolean hasAliveEnemies() {
        for (Entity enemy : currentEnemies) {
            if (enemy.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private boolean handleStun(Entity e) {
        Status stun = e.getStatus(StatusEffects.STUN);

        if (stun == null) {
            return false;
        }

        view.showStunned(e);
        stun.decrementDuration();

        if (stun.isExpired()) {
            e.removeStatus(StatusEffects.STUN);
            view.showNoLongerStunned(e);
        }

        return true;
    }

    public void updateRoundStatuses() {
        updateEntityStatus(player);

        for (Entity enemy : currentEnemies) {
            updateEntityStatus(enemy);
        }
    }

    private void updateEntityStatus(Entity e) {
        if (!e.isAlive()) {
            return;
        }

        Status defend = e.getStatus(StatusEffects.DEFEND);
        if (defend != null) {
            defend.decrementDuration();

            if (defend.isExpired()) {
                e.setDef(e.getDef() - 10);
                e.removeStatus(StatusEffects.DEFEND);
                view.showDefendWoreOff(e);
            }
        }

        Status invulnerable = e.getStatus(StatusEffects.INVULNERABLE);
        if (invulnerable != null) {
            invulnerable.decrementDuration();

            if (invulnerable.isExpired()) {
                e.removeStatus(StatusEffects.INVULNERABLE);
                view.showNoLongerInvulnerable(e);
            }
        }
    }

    private void showDefeatedEnemies(ArrayList<Entity> defeatedThisRound) {
        for (Entity enemy : currentEnemies) {
            if (!enemy.isAlive() && !defeatedThisRound.contains(enemy)) {
                view.showDefeated(enemy);
                defeatedThisRound.add(enemy);
            }
        }
    }

    private void useSpecialSkill() {
        if (player instanceof Warrior) {
            Entity target = view.chooseTarget(currentEnemies);
            if (target == null) {
                view.showNoValidTargets();
                return;
            }

            ArrayList<Entity> targets = new ArrayList<Entity>();
            targets.add(target);
            ((Warrior) player).specialSkill(targets);
            view.showShieldBash(player, target);
        } 
        
        else if (player instanceof Wizard) {
            if (!hasAliveEnemies()) {
                view.showNoValidTargets();
                return;
            }

            ((Wizard) player).specialSkill(currentEnemies);
            view.showArcaneBlast(player);
        } 
        
        else {
            view.showNoSpecialSkill();
            return;
        }
    }

    private void playerTurn() {
        int action = view.choosePlayerAction(player);

        switch (action) {
            case 1: {
                Entity target = view.chooseTarget(currentEnemies);
                if (target == null) {
                    view.showNoValidTargets();
                    return;
                }

                int damage = player.basicAttack(target);
                view.showBasicAttack(player, target, damage);
                break;
            }

            case 2: {
                if (!player.canUseSpecialSkill()) {
                    view.showSkillCooldown(player);
                    return;
                }
                useSpecialSkill();
                player.startSpecialSkillCooldown();
                break;
            }

            case 3:
                player.defend();
                view.showDefending(player);
                break;

            case 4: {
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
                    useSpecialSkill();
                    item.use(player, currentEnemies);
                }
                else {
                    item.use(player, currentEnemies);
                }

                view.showItemUsed(item.getName());
                player.removeConsumedItems();
                break;
            }

            default:
                view.showInvalidAction();
        }
    }

    private void enemyTurn(Entity enemy) {
        if (!player.isAlive()) {
            return;
        }

        if (player.hasStatus(StatusEffects.INVULNERABLE)) {
            view.showEnemyInvulnerableBlocked(player, enemy);
            return;
        }

        int damage = enemy.basicAttack(player);
        view.showEnemyAttack(enemy, player, damage);
    }

    public boolean enemiesRemaining() {
        return hasAliveEnemies();
    }

    public void setEnemies(ArrayList<Entity> enemies) {
        this.currentEnemies = enemies;
    }

    public ArrayList<Entity> getEnemies() {
        return currentEnemies;
    }

    public void executeRound() throws InterruptedException {
        ArrayList<Entity> turnOrder = getTurnOrder();
        ArrayList<Entity> defeatedThisRound = new ArrayList<Entity>();

        for (Entity e : turnOrder) {
            
            if (!e.isAlive()) {
                continue;
            }

            if (!player.isAlive() || !hasAliveEnemies()) {
                break;
            }

            view.showTurnHeader(e);

            if (e instanceof Player) {
                player.reduceSpecialSkillCooldown();
            }

            view.showEntityAttributes(e);

            if (handleStun(e)) {
                continue;
            }

            if (e instanceof Player) {
                playerTurn();
            } 
            
            else {
                enemyTurn(e);
            }

            showDefeatedEnemies(defeatedThisRound);

            if (!player.isAlive() || !hasAliveEnemies()) {
                break;
            }

            Thread.sleep(1500);
        }

        currentEnemies.removeAll(defeatedThisRound);
    }
}