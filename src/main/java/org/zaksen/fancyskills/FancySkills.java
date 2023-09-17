package org.zaksen.fancyskills;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.zaksen.fancyskills.commands.MenuCMD;
import org.zaksen.fancyskills.commands.SkillXpCMD;
import org.zaksen.fancyskills.data.PlayerData;
import org.zaksen.fancyskills.database.DatabaseManager;
import org.zaksen.fancyskills.events.MenuEvents;
import org.zaksen.fancyskills.events.WorldEvents;

public final class FancySkills extends JavaPlugin {

    private static FancySkills instance;

    public static FancySkills getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        // Create config if not exists
        saveDefaultConfig();
        // Register events
        Bukkit.getPluginManager().registerEvents(new MenuEvents(), this);
        Bukkit.getPluginManager().registerEvents(new WorldEvents(), this);
        // Register commands
        getCommand("skills").setExecutor(new MenuCMD());
        getCommand("skill_xp").setExecutor(new SkillXpCMD());
        getCommand("skill_xp").setTabCompleter(new SkillXpCMD());
        // Load online player skills
        loadPlayersSkills();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadPlayersSkills() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            loadSkillsFor(player);
        }
    }

    public void loadSkillsFor(Player player) {
        PlayerData playerData = DatabaseManager.instance.getPlayerData(player);
        if(playerData == null) {
            DatabaseManager.instance.addNewPlayer(player);
        }
    }
}