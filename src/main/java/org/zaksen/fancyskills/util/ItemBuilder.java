package org.zaksen.fancyskills.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ItemBuilder {
    private ItemStack stack;
    private ItemMeta meta;

    public ItemBuilder(Material material) {
        stack = new ItemStack(material);
        meta = stack.getItemMeta();
    }

    public ItemBuilder setAmount(int value) {
        stack.setAmount(value);
        return this;
    }

    public ItemBuilder setName(String value) {
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', value));
        return this;
    }

    public ItemBuilder setNFName(String value) {
        meta.setDisplayName(value);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        meta.addItemFlags(flag);
        return this;
    }

    public ItemBuilder setLore(List<String> value) {
        for(int i = 0; i < value.size(); i++) {
            value.set(i, ChatColor.translateAlternateColorCodes('&', value.get(i)));
        }
        meta.setLore(value);
        return this;
    }

    public ItemBuilder setNFLore(List<String> value) {
        meta.setLore(value);
        return this;
    }

    public ItemBuilder addPersistentValue(String key, PersistentDataType type, Object value) {
        meta.getPersistentDataContainer().set(
                NamespacedKey.fromString(key),
                type,
                value
        );
        return this;
    }

    public ItemBuilder removePersistentValue(String key) {
        meta.getPersistentDataContainer().remove(
                NamespacedKey.fromString(key)
        );
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder removeEnchant(Enchantment enchantment) {
        meta.removeEnchant(enchantment);
        return this;
    }

    public ItemBuilder setCustomModelData(int value) {
        meta.setCustomModelData(value);
        return this;
    }

    public ItemStack build() {
        stack.setItemMeta(meta);
        return stack;
    }
}