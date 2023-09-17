package org.zaksen.fancyskills.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zaksen.fancyskills.FancySkills;
import org.zaksen.fancyskills.skill.impls.CombatSkill;
import org.zaksen.fancyskills.skill.impls.MiningSkill;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SkillXpCMD implements TabExecutor {

    // skill combat add 100
    // skill combat set 0
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!sender.isOp()) {
            sender.sendMessage(FancySkills.getInstance().getConfig().getString("messages.error.no_permission"));
            return true;
        }
        if(args.length < 4) {
            return false;
        }
        int amount = Integer.parseInt(args[2]);
        Player player = Bukkit.getPlayer(args[3]);
        switch (args[0]) {
            case "combat": {
                CombatSkill skill = CombatSkill.getBy(player);
                if(skill != null) {
                    switch (args[1]) {
                        case "set": {
                            skill.setXp(amount);
                            break;
                        }
                        case "add": {
                            skill.addXp(amount);
                            break;
                        }
                        case "remove": {
                            skill.withdrawXp(amount);
                            break;
                        }
                    }
                    break;
                }
            }
            case "mining": {
                MiningSkill skill = MiningSkill.getBy(player);
                if(skill != null) {
                    switch (args[1]) {
                        case "set": {
                            skill.setXp(amount);
                            break;
                        }
                        case "add": {
                            skill.addXp(amount);
                            break;
                        }
                        case "remove": {
                            skill.withdrawXp(amount);
                            break;
                        }
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        switch(args.length) {
            case 1: return Arrays.asList("combat", "mining");
            case 2: return Arrays.asList("set", "add", "remove");
            case 3: return Collections.singletonList("0");
            default: return null;
        }
    }

}