package com.github.dsh105.sparktrail.menu;

import com.github.dsh105.sparktrail.particle.ParticleType;
import com.github.dsh105.sparktrail.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Project by DSH105
 */

public enum ParticleDataItem {

	AQUA(Material.WOOL, (short) 3, "Aqua", ParticleType.POTION), //light blue wool
	BLACK(Material.WOOL, (short) 15, "Black", ParticleType.SMOKE, ParticleType.POTION, ParticleType.SWIRL), //black wool
	BLUE(Material.getMaterial(351), (short) 4, "Blue", ParticleType.NOTE, ParticleType.POTION, ParticleType.SWIRL), //lapis lazuli
	CRIMSON(Material.getMaterial(351), (short) 1, "Crimson", ParticleType.NOTE, ParticleType.POTION), //rose red dye
	DARKBLUE(Material.WOOL, (short) 11, "Dark Blue", ParticleType.POTION, ParticleType.SWIRL), //blue wool
	DARKGREEN(Material.WOOL, (short) 13, "Dark Green", ParticleType.POTION, ParticleType.SWIRL), //green wool
	DARKRED(Material.STAINED_CLAY, (short) 14, "Dark Red", ParticleType.POTION, ParticleType.SWIRL), //red stained clay
	GOLD(Material.STAINED_CLAY, (short) 4, "Gold", ParticleType.POTION), //yellow stained clay
	GRAY(Material.WOOL, (short) 7, "Gray", ParticleType.POTION, ParticleType.SWIRL), //gray wool
	GREEN(Material.WOOL, (short) 5, "Green", ParticleType.NOTE, ParticleType.POTION, ParticleType.SWIRL), //lime wool
	LIGHTBLUE(Material.WOOL, (short) 3, "Light Blue", ParticleType.SWIRL), //light blue wool
	MAGENTA(Material.WOOL, (short) 2, "Magenta", ParticleType.NOTE), //magenta wool
	OLIVEGREEN(Material.WOOL, (short) 13, "Olive Green", ParticleType.NOTE), //green wool
	ORANGE(Material.WOOL, (short) 1, "Orange", ParticleType.NOTE, ParticleType.SWIRL), //orange wool
	PALERED(Material.STAINED_CLAY, (short) 6, "Pale Red", ParticleType.NOTE), //pink stained clay
	PINK(Material.WOOL, (short) 6, "Pink", ParticleType.POTION, ParticleType.SWIRL), //pink wool
	PURPLE(Material.WOOL, (short) 10, "Purple", ParticleType.SWIRL), //purple wool
	RAINBOW(Material.FIREWORK, (short) 0, "Rainbow", ParticleType.SMOKE), //firework
	RED(Material.WOOL, (short) 14, "Red", ParticleType.SMOKE, ParticleType.POTION, ParticleType.SWIRL), //red wool
	TURQUOISE(Material.WOOL, (short) 3, "Light Blue", ParticleType.NOTE), //light blue wool
	VIOLET(Material.WOOL, (short) 10, "Violet", ParticleType.NOTE), //purple wool
	YELLOW(Material.WOOL, (short) 4, "Yellow", ParticleType.NOTE, ParticleType.SWIRL), //yellow wool
	WHITE(Material.WOOL, (short) 0, "White", ParticleType.SWIRL); //white wool

	private Material material;
	private short data;
	private ArrayList<ParticleType> particleTypes = new ArrayList<ParticleType>();

	ParticleDataItem(Material material, short data, String name, ParticleType... particleType) {
		this.material = material;
		this.data = data;
		for (ParticleType pt : particleType) {
			this.particleTypes.add(pt);
		}
	}

	public ArrayList<ParticleType> getParticleTypes() {
		return this.particleTypes;
	}

	public ItemStack getMenuItem() {
		ItemStack i = new ItemStack(this.material, 1, this.data);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + StringUtil.capitalise(this.toString()));
		i.setItemMeta(meta);
		return i;
	}

	public ItemStack getMenuItem(boolean nameFlag) {
		ItemStack i = new ItemStack(this.material, 1, this.data);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + StringUtil.capitalise(this.toString()) + (nameFlag ? ChatColor.GREEN + " [TOGGLE ON]" : ChatColor.RED + " [TOGGLE OFF]"));
		i.setItemMeta(meta);
		return i;
	}
}