package com.tqqn.minigames.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
        itemMeta = itemStack.getItemMeta();
    }

    private void updateItemMeta() {
        itemStack.setItemMeta(itemMeta);
    }

    public ItemBuilder setDisplayName(String name) {
        itemMeta.displayName(ChatUtils.format(name));
        return this;
    }

    public ItemBuilder setLore(String... lines) {
        itemMeta.setLore(Arrays.asList(ChatUtils.translateLegacy(lines)));
        return this;
    }

    public void setGlow() {
        itemMeta.addEnchant(Enchantment.DURABILITY, 0, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public void setUnbreakable() {
        itemMeta.setUnbreakable(true);
    }

    public ItemStack build() {
        updateItemMeta();
        return itemStack;
    }
}
