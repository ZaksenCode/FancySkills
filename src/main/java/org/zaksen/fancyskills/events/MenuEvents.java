package org.zaksen.fancyskills.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.zaksen.fancyskills.gui.Menu;
import org.zaksen.fancyskills.gui.items.IClickableItem;

public class MenuEvents implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Menu menu = Menu.getBy(player);
        if(menu != null) {
            IClickableItem clickItem = menu.getClickItemBy(event.getSlot());
            if(clickItem != null) {
                clickItem.onClick(event);
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Menu menu = Menu.getBy(player);
        if(menu != null) {
            menu.onClose(player);
        }
    }
}