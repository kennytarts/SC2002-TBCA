package model.levels;

import model.characters.Combatant;

public interface EnemyFactory {
    Combatant createGoblin(String name);
    Combatant createWolf(String name);
    Combatant createOgre(String name);
}
