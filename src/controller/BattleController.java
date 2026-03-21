package controller;

import java.util.ArrayList;

import model.Entity;
import model.Player;
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

        if (player != null && player.isAlive()) {
            order.add(player);
        }

        for (Entity e : currentEnemies) {
            if (e != null && e.isAlive()) {
                order.add(e);
            }
        }

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

    private void updateEndOfTurnStatuses(Entity e) {
        Status invulnerable = e.getStatus(StatusEffects.INVULNERABLE);
        if (invulnerable != null) {
            invulnerable.decrementDuration();

            if (invulnerable.isExpired()) {
                e.removeStatus(StatusEffects.INVULNERABLE);
                view.showNoLongerInvulnerable(e);
            }
        }
    }

    public void updateDefendStatusesAtStartOfRound() {
        updateDefendForEntity(player);

        for (Entity enemy : currentEnemies) {
            updateDefendForEntity(enemy);
        }
    }

    private void updateDefendForEntity(Entity e) {
        if (e == null || !e.isAlive()) {
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
    }

    private void announceDefeatedEnemies(ArrayList<Entity> defeatedThisRound) {
        for (Entity enemy : currentEnemies) {
            if (!enemy.isAlive() && !defeatedThisRound.contains(enemy)) {
                view.showDefeated(enemy);
                defeatedThisRound.add(enemy);
            }
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
                } else if (player instanceof Wizard) {
                    if (!hasAliveEnemies()) {
                        view.showNoValidTargets();
                        return;
                    }

                    ((Wizard) player).specialSkill(currentEnemies);
                    view.showArcaneBlast(player);
                } else {
                    view.showNoSpecialSkill();
                    return;
                }

                player.startSpecialSkillCooldown();
                break;
            }

            case 3:
                player.defend();
                view.showDefending(player);
                break;

            case 4:
                view.showItemsNotImplemented();
                break;

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

            if (handleStun(e)) {
                updateEndOfTurnStatuses(e);
                continue;
            }

            view.showEntityAttributes(e);

            if (e instanceof Player) {
                playerTurn();
            } else {
                enemyTurn(e);
            }

            announceDefeatedEnemies(defeatedThisRound);

            if (!player.isAlive() || !hasAliveEnemies()) {
                break;
            }

            updateEndOfTurnStatuses(e);

            Thread.sleep(1000);
        }

        currentEnemies.removeAll(defeatedThisRound);
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
}