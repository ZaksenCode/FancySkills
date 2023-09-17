package org.zaksen.fancyskills.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zaksen.fancyskills.FancySkills;
import org.zaksen.fancyskills.data.PlayerData;
import org.zaksen.fancyskills.gui.items.MenuItem;
import org.zaksen.fancyskills.skill.ISkill;
import org.zaksen.fancyskills.util.ItemBuilder;

import java.util.List;

public class SkillsMenu extends Menu {
    private PlayerData data;
    public SkillsMenu(PlayerData playerData) {
        super(FancySkills.getInstance().getConfig().getString("menus.skills.name"), 27);
        this.data = playerData;
        ItemStack fillPanel = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta fillMeta = fillPanel.getItemMeta();
        fillMeta.setDisplayName(" ");
        fillPanel.setItemMeta(fillMeta);
        for(int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, fillPanel);
        }
        addMenuItem(FancySkills.getInstance().getConfig().getInt("menus.skills.combat_skill.slot"), data.getCombatSkill());
        addMenuItem(FancySkills.getInstance().getConfig().getInt("menus.skills.mining_skill.slot"), data.getMiningSkill());
        inventory.setItem(FancySkills.getInstance().getConfig().getInt("menus.skills.tokens.slot"),
                createConfigItem("tokens")
        );
    }

    private ItemStack createConfigItem(String configKey) {
        String name = FancySkills.getInstance().getConfig().getString("menus.skills." + configKey + ".name");
        List<String> lore = FancySkills.getInstance().getConfig().getStringList("menus.skills." + configKey + ".lore");
        for(int i = 0; i < lore.size(); i++) {
            lore.set(i, lore.get(i)
                    .replaceFirst("%tokens%", String.valueOf(data.getTokens()))
            );
        }
        return new ItemBuilder(Material.valueOf(FancySkills.getInstance().getConfig().getString("menus.skills." + configKey + ".icon")))
                .setName(name).setLore(lore).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build();
    }

    private void addMenuItem(int slot, ISkill skill) {
        MenuItem newMenuItem = new MenuItem(slot, new SkillMenu(skill));
        ItemStack skillStack = createSkillItem(skill.getName(), skill);
        addClickItem(newMenuItem, skillStack);
    }

    private ItemStack createSkillItem(String configKey, ISkill skill) {
        String name = FancySkills.getInstance().getConfig().getString("menus.skills." + configKey + "_skill.name");
        List<String> lore = FancySkills.getInstance().getConfig().getStringList("menus.skills." + configKey + "_skill.lore");
        for(int i = 0; i < lore.size(); i++) {
                lore.set(i, lore.get(i)
                        .replaceFirst("%level%", String.valueOf(skill.getCurLevel()))
                        .replaceFirst("%xp%", String.valueOf(skill.getCurXp()))
            );
        }
        return new ItemBuilder(Material.valueOf(FancySkills.getInstance().getConfig().getString("menus.skills." + configKey + "_skill.icon"))).setName(name).setLore(lore).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build();
    }
}