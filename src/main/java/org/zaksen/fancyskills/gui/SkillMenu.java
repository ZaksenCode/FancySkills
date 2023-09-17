package org.zaksen.fancyskills.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zaksen.fancyskills.FancySkills;
import org.zaksen.fancyskills.skill.ISkill;
import org.zaksen.fancyskills.util.ItemBuilder;

import java.util.List;

public class SkillMenu extends Menu {

    private ISkill skill;
    public SkillMenu(ISkill skill) {
        super(FancySkills.getInstance().getConfig().getString("menus." + skill.getName() + "_menu.name"), 27);
        String startConfigName = "menus." + skill.getName() + "_menu"; // menus.combat_menu.cur_level.icon
        this.skill = skill;
        ItemStack fillPanel = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        ItemMeta fillMeta = fillPanel.getItemMeta();
        fillMeta.setDisplayName(" ");
        fillPanel.setItemMeta(fillMeta);
        for(int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, fillPanel);
        }
        createLevelItem(FancySkills.getInstance().getConfig().getInt(startConfigName + ".items.cur_level.slot"), startConfigName);
        createNextLevelItem(FancySkills.getInstance().getConfig().getInt(startConfigName + ".items.next_level.slot"), startConfigName);
    }

    private void createLevelItem(int slot, String configKey) {
        String name = FancySkills.getInstance().getConfig().getString(configKey + ".items.cur_level.name");
        List<String> lore = FancySkills.getInstance().getConfig().getStringList(configKey + ".items.cur_level.lore");
        for(int i = 0; i < lore.size(); i++) {
            lore.set(i, lore.get(i)
                    .replaceFirst("%level%", String.valueOf(skill.getCurLevel()))
                    .replaceFirst("%xp%", String.valueOf(skill.getCurXp()))
            );
        }
        ItemStack result = new ItemBuilder(Material.valueOf(FancySkills.getInstance().getConfig().getString(configKey + ".items.cur_level.icon"))).
                setName(name).setLore(lore).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build();
        inventory.setItem(slot, result);
    }

    private void createNextLevelItem(int slot, String configKey) {
        String name = FancySkills.getInstance().getConfig().getString(configKey + ".items.next_level.name");
        List<String> lore = FancySkills.getInstance().getConfig().getStringList(configKey + ".items.next_level.lore");
        String[] nextLevel = skill.getNextLevel();
        if(nextLevel[0] != null) {
            lore.replaceAll(s -> s
                    .replaceFirst("%level%", nextLevel[0])
                    .replaceFirst("%xp%", nextLevel[1]));
        }
        ItemStack result = new ItemBuilder(Material.valueOf(FancySkills.getInstance().getConfig().getString(configKey + ".items.next_level.icon"))).
                setName(name).setLore(lore).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build();
        inventory.setItem(slot, result);
    }
}