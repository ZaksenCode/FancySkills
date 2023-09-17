package org.zaksen.fancyskills.skill.impls;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.zaksen.fancyskills.FancySkills;
import org.zaksen.fancyskills.skill.ISkill;
import org.zaksen.fancyskills.util.ChatUtil;

import java.util.ArrayList;
import java.util.List;

public class CombatSkill extends ISkill {
    private static final List<CombatSkill> skills = new ArrayList<>();

    @Nullable
    public static CombatSkill getBy(Player player) {
        for(CombatSkill skill : skills) {
            if(skill.player == player) {
                return skill;
            }
        }
        return null;
    }

    public CombatSkill(int playerXp, Player player) {
        super("combat", playerXp, player);
        skills.add(this);
    }

    @Override
    protected void notifyPlayer() {
        ChatUtil.sendMessage(player,
                FancySkills.getInstance().getConfig().getString("messages.level_up.combat").replaceFirst("%level%", String.valueOf(level))
        );
    }
}