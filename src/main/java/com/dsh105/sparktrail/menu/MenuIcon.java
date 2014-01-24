package com.dsh105.sparktrail.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class MenuIcon {

    private ParticleMenu menu;
    private ItemStack stack;

    public MenuIcon(ParticleMenu menu, Material mat, short data, String name, String... lore) {
        this.menu = menu;
        ItemStack item = new ItemStack(mat, 1, data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        this.stack = item;
    }

    public Player getViewer() {
        return this.getMenu().getViewer();
    }

    public ParticleMenu getMenu() {
        return menu;
    }

    public ItemStack getStack() {
        return stack;
    }

    public void onClick() {
    }
}