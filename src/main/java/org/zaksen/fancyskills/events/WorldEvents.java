package org.zaksen.fancyskills.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.zaksen.fancyskills.FancySkills;
import org.zaksen.fancyskills.skill.impls.CombatSkill;
import org.zaksen.fancyskills.skill.impls.MiningSkill;
import org.zaksen.fancyskills.util.ChatUtil;

import java.util.Map;

public class WorldEvents implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            CombatSkill skill = CombatSkill.getBy(player);
            if(skill != null) {
                String playerHandItem = player.getInventory().getItemInMainHand().getType().toString();
                Map<String, Object> leveledItems = FancySkills.getInstance().getConfig().getConfigurationSection("requirements.combat").getValues(false);
                leveledItems.forEach((itemName, level) -> {
                    if(itemName.equalsIgnoreCase(playerHandItem)) {
                        int reqLevel = (Integer) level;
                        if(skill.getCurLevel() < reqLevel) {
                            event.setDamage(0.5);
                            String lowLevelMessage = FancySkills.getInstance().getConfig().getString("messages.low_level").replaceFirst("%level%", String.valueOf(reqLevel));
                            ChatUtil.sendMessage(player, lowLevelMessage, true);
                        } else {
                            int hitXp = FancySkills.getInstance().getConfig().getInt("xp.combat.hit");
                            skill.addXp(hitXp);
                            if(event.getEntity() instanceof LivingEntity) {
                                LivingEntity entity = (LivingEntity) event.getEntity();
                                if(event.getDamage() >= entity.getHealth()) {
                                    int killXp = FancySkills.getInstance().getConfig().getInt("xp.combat.kill");
                                    skill.addXp(killXp);
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        MiningSkill skill = MiningSkill.getBy(player);
        if(skill != null) {
            String playerHandItem = player.getInventory().getItemInMainHand().getType().toString();
            Map<String, Object> leveledItems = FancySkills.getInstance().getConfig().getConfigurationSection("requirements.mining").getValues(false);
            leveledItems.forEach((itemName, level) -> {
                if(itemName.equalsIgnoreCase(playerHandItem)) {
                    int reqLevel = (Integer) level;
                    if(skill.getCurLevel() < reqLevel) {
                        event.setCancelled(true);
                        String lowLevelMessage = FancySkills.getInstance().getConfig().getString("messages.low_level").replaceFirst("%level%", String.valueOf(reqLevel));
                        ChatUtil.sendMessage(player, lowLevelMessage, true);
                    } else {
                        int breakXp = FancySkills.getInstance().getConfig().getInt("xp.mining.block_break");
                        skill.addXp(1);
                    }
                }
            });
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        FancySkills.getInstance().loadSkillsFor(event.getPlayer());
    }
}