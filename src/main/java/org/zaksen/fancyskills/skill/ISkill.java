package org.zaksen.fancyskills.skill;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.zaksen.fancyskills.FancySkills;
import org.zaksen.fancyskills.data.PlayerData;
import org.zaksen.fancyskills.database.DatabaseManager;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public abstract class ISkill implements Skill {
    private boolean firstTime = true;
    protected final String name;
    protected final Player player;
    protected int level, totalXp;

    public ISkill(String name, int totalXp, Player player) {
        this.name = name;
        this.level = 0;
        this.totalXp = totalXp;
        this.player = player;
        recountLevel();
    }

    public void addXp(int amount) {
        totalXp += amount;
        updateXp();
    }

    public void setXp(int amount) {
        totalXp = amount;
        updateXp();
    }

    public void withdrawXp(int amount) {
        totalXp -= amount;
        updateXp();
    }

    private void updateXp() {
        DatabaseManager.instance.setPlayerData(player, name + "Xp", totalXp);
        recountLevel();
    }

    private void recountLevel() {
        Map<String, Object> levels = FancySkills.getInstance().getConfig().getConfigurationSection("skills." + name).getValues(false);
        levels.forEach((cfgLevel, cfgXp) -> {
            if(cfgXp instanceof Integer) {
                int levelXp = (Integer) cfgXp;
                if(totalXp >= levelXp) {
                    int newLevel = Integer.parseInt(cfgLevel);
                    if(level < newLevel) {
                        level = newLevel;
                        if(!firstTime) {
                            PlayerData data = DatabaseManager.instance.getPlayerData(player);
                            if(data != null) {
                                DatabaseManager.instance.setPlayerData(player, "tokens", data.getTokens() + 1);
                            }
                            if(FancySkills.getInstance().getConfig().getBoolean("do_level_up_messages")) {
                                notifyPlayer();
                            }
                        }
                    }
                }
            }
        });
        firstTime = false;
    }

    @Nullable
    public String[] getNextLevel() {
        final String[] result = {null, null};
        int nextLevel = level + 1;
        Map<String, Object> levels = FancySkills.getInstance().getConfig().getConfigurationSection("skills." + name).getValues(false);
        for(Map.Entry<String, Object> level : levels.entrySet()) {
            if(level.getValue() instanceof Integer) {
                int levelXp = (Integer) level.getValue();
                int newLevel = Integer.parseInt(level.getKey());
                if(nextLevel == newLevel) {
                    result[0] = String.valueOf(nextLevel);
                    result[1] = String.valueOf(levelXp);
                } else if(this.level == newLevel) {
                    result[0] = String.valueOf(this.level);
                    result[1] = String.valueOf(levelXp);
                }
            }
        }
        return result;
    }

    protected void notifyPlayer() {
        player.sendMessage("New skill level!");
    }

    @Override
    public int getCurLevel() {
        return level;
    }

    @Override
    public int getCurXp() {
        return totalXp;
    }

    public String getName() {
        return name;
    }
}