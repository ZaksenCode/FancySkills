package org.zaksen.fancyskills.util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtil {

    public static void sendMessage(Player player, String message) {
        sendMessage(player, message, false);
    }

    public static void sendMessage(Player player, String message, boolean actionBar) {
        if(actionBar) {
            player.spigot().sendMessage(
                    ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message))
            );
            return;
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}