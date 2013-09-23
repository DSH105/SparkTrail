package com.github.dsh105.sparktrail.menu;

import com.github.dsh105.sparktrail.SparkTrail;
import com.github.dsh105.sparktrail.api.event.MenuOpenEvent;
import com.github.dsh105.sparktrail.data.EffectHandler;
import com.github.dsh105.sparktrail.particle.Effect;
import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.ParticleType;
import com.github.dsh105.sparktrail.util.Lang;
import com.github.dsh105.sparktrail.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;

/**
 * Project by DSH105
 */

public class DataMenu extends Menu {

	public static HashMap<String, DataMenu> openMenus = new HashMap<String, DataMenu>();
	public static ItemStack BACK;

	static {
		ItemStack item = new ItemStack(Material.BOOK, 1, (short) 0);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Back");
		item.setItemMeta(meta);
		BACK = item;
	}

	Inventory inv;
	private int size;
	public EffectHolder.EffectType effectType;
	public ParticleType particleType;

	public DataMenu(Player viewer, UUID mobUuid, ParticleType particleType) {
		this(viewer, EffectHolder.EffectType.MOB, StringUtil.capitalise(particleType.toString()) + " - Particle Selector - " + mobUuid);
		this.particleType = particleType;
		this.mobUuid = mobUuid;
		setItems();
	}

	public DataMenu(Player viewer, String playerName, ParticleType particleType) {
		this(viewer, EffectHolder.EffectType.PLAYER, StringUtil.capitalise(particleType.toString()) + " - Particle Selector - " + playerName);
		this.particleType = particleType;
		setItems();
	}

	public DataMenu(Player viewer, Location location, ParticleType particleType) {
		this(viewer, EffectHolder.EffectType.LOCATION, StringUtil.capitalise(particleType.toString()) + " - Particle Selector - " + StringUtil.capitalise(location.getWorld().getName()) + ", " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ());
		this.particleType = particleType;
		this.location = location;
		setItems();
	}

	private DataMenu(Player viewer, EffectHolder.EffectType effectType, String name) {
		this.size = 27;
		this.effectType = effectType;
		this.viewer = viewer;
		this.inv = Bukkit.createInventory(viewer, size, name);
		openMenus.put(viewer.getName(), this);
	}

	public void setItems() {
		EffectHolder eh = null;
		if (effectType == EffectHolder.EffectType.PLAYER) {
			eh = EffectHandler.getInstance().getEffect(viewer.getName());
		}
		else if (effectType == EffectHolder.EffectType.LOCATION) {
			eh = EffectHandler.getInstance().getEffect(this.location);
		}
		else if (effectType == EffectHolder.EffectType.MOB) {
			eh = EffectHandler.getInstance().getEffect(this.mobUuid);
		}

		if (eh != null) {
			int i = 0;
			for (ParticleType pt : ParticleType.values()) {
				boolean hasEffect = false;
				if (eh != null) {
					for (Effect e : eh.getEffects()) {
						if (e.getParticleType() == pt) {
							hasEffect = true;
						}
					}
				}
				inv.setItem(i++, pt.getMenuItem(!hasEffect));
			}

			int book = size - 1;
			inv.setItem(book, BACK);

		}
	}

	public void open(boolean sendMessage) {
		MenuOpenEvent menuEvent = new MenuOpenEvent(this.viewer, MenuOpenEvent.MenuType.MAIN);
		SparkTrail.getInstance().getServer().getPluginManager().callEvent(menuEvent);
		if (menuEvent.isCancelled()) {
			return;
		}
		this.viewer.openInventory(this.inv);
		if (sendMessage) {
			Lang.sendTo(this.viewer, Lang.OPEN_MENU.toString());
		}
	}

}