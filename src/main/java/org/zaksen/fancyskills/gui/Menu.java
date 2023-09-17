package org.zaksen.fancyskills.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.zaksen.fancyskills.gui.items.IClickableItem;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private static final List<Menu> menus = new ArrayList<>();
    protected final List<IClickableItem> clickableItems = new ArrayList<>();
    @Nullable
    public IClickableItem getClickItemBy(int slot) {
        for(IClickableItem item : clickableItems) {
            if(item.getSlot() == slot) {
                return item;
            }
        }
        return null;
    }
    protected final String title;
    protected final int size;
    protected final Inventory inventory;
    protected final List<Player> viewers = new ArrayList<>();

    @Nullable
    public static Menu getBy(Player player) {
        for(Menu menu : menus) {
            if(menu.viewers.contains(player)) {
                return menu;
            }
        }
        return null;
    }

    public Menu(String title, int size) {
        this.title = title;
        this.size = size;
        this.inventory = Bukkit.createInventory(null, size, title);
        menus.add(this);
    }

    public void openMenu(Player player) {
        player.openInventory(inventory);
        viewers.add(player);
    }

    public void onClose(Player player) {
        viewers.remove(player);
        if(viewers.size() <= 0) {
            menus.remove(this);
        }
    }

    public void addClickItem(IClickableItem item, ItemStack clickItem) {
        clickableItems.add(item);
        inventory.setItem(item.getSlot(), clickItem);
    }
}