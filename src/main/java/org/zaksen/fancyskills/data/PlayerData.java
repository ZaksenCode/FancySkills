package org.zaksen.fancyskills.data;

import org.bukkit.entity.Player;
import org.zaksen.fancyskills.skill.impls.CombatSkill;
import org.zaksen.fancyskills.skill.impls.MiningSkill;

public class PlayerData {
    private final Player player;
    private final CombatSkill combat;
    private final MiningSkill mining;
    private final int tokens;

    public PlayerData(Player player, int combatXp, int miningXp, int tokens) {
        this.player = player;
        this.combat = new CombatSkill(combatXp, player);
        this.mining = new MiningSkill(miningXp, player);
        this.tokens = tokens;
    }

    public CombatSkill getCombatSkill() {
        return combat;
    }

    public MiningSkill getMiningSkill() {
        return mining;
    }
    public int getTokens() {
        return tokens;
    }
    public Player getPlayer() {
        return player;
    }
}