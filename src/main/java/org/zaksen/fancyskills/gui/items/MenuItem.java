package org.zaksen.fancyskills.gui.items;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.zaksen.fancyskills.gui.Menu;

public class MenuItem extends IClickableItem {

    private final Menu menu;

    public MenuItem(int slot, Menu menu) {
        super(slot);
        this.menu = menu;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        menu.openMenu((Player) event.getWhoClicked());
    }
}