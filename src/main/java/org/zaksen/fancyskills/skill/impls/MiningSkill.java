package org.zaksen.fancyskills.skill.impls;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.zaksen.fancyskills.FancySkills;
import org.zaksen.fancyskills.skill.ISkill;
import org.zaksen.fancyskills.util.ChatUtil;

import java.util.ArrayList;
import java.util.List;

public class MiningSkill extends ISkill {
    private static final List<MiningSkill> skills = new ArrayList<>();

    @Nullable
    public static MiningSkill getBy(Player player) {
        for(MiningSkill skill : skills) {
            if(skill.player == player) {
                return skill;
            }
        }
        return null;
    }

    public MiningSkill(int playerXp, Player player) {
        super("mining", playerXp, player);
        skills.add(this);
    }

    @Override
    protected void notifyPlayer() {
        ChatUtil.sendMessage(player,
                FancySkills.getInstance().getConfig().getString("messages.level_up.mining").replaceFirst("%level%", String.valueOf(level))
        );
    }
}