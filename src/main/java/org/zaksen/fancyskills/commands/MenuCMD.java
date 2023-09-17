package org.zaksen.fancyskills.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.zaksen.fancyskills.data.PlayerData;
import org.zaksen.fancyskills.database.DatabaseManager;
import org.zaksen.fancyskills.gui.SkillsMenu;

public class MenuCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        PlayerData playerData = DatabaseManager.instance.getPlayerData(player);
        new SkillsMenu(playerData).openMenu(player);
        // TODO - open skills menu
        return true;
    }

}