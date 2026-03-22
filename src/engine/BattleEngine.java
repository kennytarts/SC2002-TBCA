package engine;

import java.util.ArrayList;
import java.util.List;

import model.Entity;
import model.Item;
import model.Player;
import model.PowerStone;
import model.Status;
import model.StatusEffects;
import model.Warrior;
import model.Wizard;
import model.Battle;
import engine.strategy.TurnOrderStrategy;
import view.GameCLI;

/**
 * BattleEngine: Core battle execution logic.
 * Responsibility: Process rounds, turns, status effects, damage
 * 
 * Does NOT handle:
 * - Game-level flow (GameController handles that)
 * - Multiple rounds across levels (GameController handles that)
 * - Player class selection (GameController handles that)
 */
public class BattleEngine {
    private Battle battle;
    private TurnOrderStrategy turnOrderStrategy;
    private GameCLI view;

    public BattleEngine(Battle battle, TurnOrderStrategy turnOrderStrategy, GameCLI view) {
        this.battle = battle;
        this.turnOrderStrategy = turnOrderStrategy;
        this.view = view;
    }

    /**
     * Execute a single round of combat
     */
    public void executeRound() throws InterruptedException {
        Player player = battle.getPlayer();
        ArrayList<Entity> enemies = battle.getEnemies();

        // Get turn order from strategy
        List<Entity> allCombatants = new ArrayList<Entity>();
        allCombatants.add(player);
        allCombatants.addAll(enemies);

        List<Entity> turnOrder = turnOrderStrategy.determineTurnOrder(allCombatants);

        List<Entity> defeatedThisRound = new ArrayList<Entity>();

        for (Entity entity : turnOrder) {
            // Skip dead combatants
            if (!entity.isAlive()) {
                continue;
            }

            // Stop if battle is over
            if (!player.isAlive() || !battle.hasAliveEnemies()) {
                break;
            }

            view.showTurnHeader(entity);

            // Reduce cooldown for player
            if (entity instanceof Player) {
                player.reduceSpecialSkillCooldown();
            }

            // Check and handle stun status
            if (handleStun(entity)) {
                continue;
            }

            view.showEntityAttributes(entity);

            // Execute turn
            if (entity instanceof Player) {
                executeTurn((Player) entity, enemies);
            } else {
                executeEnemyTurn((Entity) entity, player);
            }

            // Announce newly defeated enemies
            announceDefeatedEnemies(enemies, defeatedThisRound);

            // Stop if battle is over mid-round
            if (!player.isAlive() || !battle.hasAliveEnemies()) {
                break;
            }

            Thread.sleep(1000);
        }

        // Clean up defeated enemies
        battle.removeDefeatedEnemies();
    }

    /**
     * Update duration of status effects at start of round
     */
    public void updateRoundStatusEffects() {
        updateEntityStatusEffects(battle.getPlayer());
        for (Entity enemy : battle.getEnemies()) {
            updateEntityStatusEffects(enemy);
        }
    }

    // ============== PRIVATE HELPER METHODS ==============

    private void updateEntityStatusEffects(Entity entity) {
        if (!entity.isAlive()) {
            return;
        }

        // Handle DEFEND status
        Status defend = entity.getStatus(StatusEffects.DEFEND);
        if (defend != null) {
            defend.decrementDuration();
            if (defend.isExpired()) {
                entity.setDef(entity.getDef() - 10);
                entity.removeStatus(StatusEffects.DEFEND);
                view.showDefendWoreOff(entity);
            }
        }

        // Handle INVULNERABLE status
        Status invulnerable = entity.getStatus(StatusEffects.INVULNERABLE);
        if (invulnerable != null) {
            invulnerable.decrementDuration();
            if (invulnerable.isExpired()) {
                entity.removeStatus(StatusEffects.INVULNERABLE);
                view.showNoLongerInvulnerable(entity);
            }
        }
    }

    private boolean handleStun(Entity entity) {
        Status stun = entity.getStatus(StatusEffects.STUN);
        if (stun == null) {
            return false;
        }

        view.showStunned(entity);
        stun.decrementDuration();

        if (stun.isExpired()) {
            entity.removeStatus(StatusEffects.STUN);
        }

        return true;
    }

    private void executeTurn(Player player, ArrayList<Entity> enemies) {
        int action = view.choosePlayerAction(player);

        switch (action) {
            case 1: // Basic Attack
                executeBasicAttack(player, enemies);
                break;

            case 2: // Special Skill
                executeSpecialSkill(player, enemies);
                break;

            case 3: // Defend
                player.defend();
                view.showDefending(player);
                break;

            case 4: // Use Item
                executeUseItem(player, enemies);
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

    private void executeSpecialSkill(Player player, ArrayList<Entity> enemies) {
        if (!player.canUseSpecialSkill()) {
            view.showSkillCooldown(player);
            return;
        }

        if (player instanceof Warrior) {
            Entity target = view.chooseTarget(enemies);
            if (target == null) {
                view.showNoValidTargets();
                return;
            }

            ArrayList<Entity> targets = new ArrayList<Entity>();
            targets.add(target);
            ((Warrior) player).specialSkill(targets);
            view.showShieldBash(player, target);

        } else if (player instanceof Wizard) {
            if (!battle.hasAliveEnemies()) {
                view.showNoValidTargets();
                return;
            }

            ((Wizard) player).specialSkill(enemies);
            view.showArcaneBlast(player);

        } else {
            view.showNoSpecialSkill();
            return;
        }

        player.startSpecialSkillCooldown();
    }

    private void executeUseItem(Player player, ArrayList<Entity> enemies) {
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

        // Special handling for PowerStone - it grants a free special skill use
        if (item instanceof PowerStone) {
            executeSpecialSkill(player, enemies);
            item.use(player, enemies);
        } else {
            item.use(player, enemies);
        }

        view.showItemUsed(item.getName());
        player.removeConsumedItems();
    }

    private void executeEnemyTurn(Entity enemy, Player player) {
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

    private void announceDefeatedEnemies(ArrayList<Entity> enemies, List<Entity> defeatedThisRound) {
        for (Entity enemy : enemies) {
            if (!enemy.isAlive() && !defeatedThisRound.contains(enemy)) {
                view.showDefeated(enemy);
                defeatedThisRound.add(enemy);
            }
        }
    }
}
