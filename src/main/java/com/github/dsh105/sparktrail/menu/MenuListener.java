package com.github.dsh105.sparktrail.menu;

import com.github.dsh105.sparktrail.api.chat.MenuChatListener;
import com.github.dsh105.sparktrail.api.chat.WaitingData;
import com.github.dsh105.sparktrail.data.EffectCreator;
import com.github.dsh105.sparktrail.data.EffectHandler;
import com.github.dsh105.sparktrail.logger.Logger;
import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.ParticleDetails;
import com.github.dsh105.sparktrail.particle.ParticleType;
import com.github.dsh105.sparktrail.particle.type.Note;
import com.github.dsh105.sparktrail.particle.type.Potion;
import com.github.dsh105.sparktrail.particle.type.Smoke;
import com.github.dsh105.sparktrail.particle.type.Swirl;
import com.github.dsh105.sparktrail.util.EnumUtil;
import com.github.dsh105.sparktrail.util.Lang;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;
import java.util.UUID;

/**
 * Project by DSH105
 */

public class MenuListener implements Listener {

	@EventHandler
	public void onInvClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Inventory inv = event.getInventory();
		String title = event.getView().getTitle();
		int slot = event.getRawSlot();

		try {
			if (slot < 0 || slot > inv.getSize()) {
				return;
			}
		} catch (Exception e) {return;}

		if (title.startsWith("Particle Selection")) {
			if (slot <= 44 && inv.getItem(slot) != null) {
				ParticleMenu menu = ParticleMenu.openMenus.get(player.getName());
				if (menu == null) {
					return;
				}
				String s = title.split(" - ")[1];
				if (inv.getItem(slot).equals(ParticleMenu.CLOSE)) {
					player.closeInventory();
					event.setCancelled(true);
					ParticleMenu.openMenus.remove(player.getName());
					return;
				}

				for (ParticleType pt : ParticleType.values()) {
					if (!pt.requiresDataMenu()) {
						if (inv.getItem(slot).equals(pt.getMenuItem(false))) {
							removeEffect(player, menu.effectType, pt, menu, s);
							inv.setItem(slot, pt.getMenuItem(true));
						}
						else if (inv.getItem(slot).equals(pt.getMenuItem(true))) {
							addEffect(player, menu.effectType, pt, menu, s);
							inv.setItem(slot, pt.getMenuItem(false));
						}
					}
					else {
						if (pt == ParticleType.BLOCKBREAK || pt == ParticleType.FIREWORK) {
							WaitingData wd = new WaitingData(menu.effectType, pt);
							wd.location = menu.location;
							wd.mobUuid = menu.mobUuid;
							wd.playerName = player.getName();
							MenuChatListener.AWAITING_DATA.put(player.getName(), wd);
							if (pt == ParticleType.BLOCKBREAK) {
								Lang.sendTo(player, Lang.ENTER_BLOCK.toString());
							}
							else if (pt == ParticleType.FIREWORK) {
								Lang.sendTo(player, Lang.ENTER_FIREWORK.toString());
							}
						}
						else {
							if (menu.effectType == EffectHolder.EffectType.PLAYER) {
								DataMenu dm = new DataMenu(player, player.getName(), pt);
								dm.open(false);
								player.closeInventory();
								event.setCancelled(true);
								ParticleMenu.openMenus.remove(player.getName());
								return;
							}
							else if (menu.effectType == EffectHolder.EffectType.LOCATION) {
								DataMenu dm = new DataMenu(player, menu.location, pt);
								dm.open(false);
								player.closeInventory();
								event.setCancelled(true);
								ParticleMenu.openMenus.remove(player.getName());
								return;
							}
							else if (menu.effectType == EffectHolder.EffectType.MOB) {
								DataMenu dm = new DataMenu(player, menu.mobUuid, pt);
								dm.open(false);
								player.closeInventory();
								event.setCancelled(true);
								ParticleMenu.openMenus.remove(player.getName());
								return;
							}
						}
					}
				}
				event.setCancelled(true);
				return;
			}
		}
		else if (title.contains(" - Particle Selection - ")) {
			if (slot <= 26) {
				DataMenu menu = DataMenu.openMenus.get(player.getName());
				if (menu == null) {
					return;
				}
				String[] split = title.split(" - ");
				String particle = split[0];
				String data = split[2];

				if (inv.getItem(slot).equals(DataMenu.BACK)) {
					player.closeInventory();
					event.setCancelled(true);
					DataMenu.openMenus.remove(player.getName());
					return;
				}

				if (EnumUtil.isEnumType(ParticleType.class, particle.toString().toUpperCase())) {
					ParticleType pt = ParticleType.valueOf(particle.toUpperCase());
					if (pt != null) {
						for (ParticleDataItem pdi : ParticleDataItem.values()) {
							boolean b = inv.getItem(slot).getItemMeta().getDisplayName().contains("[OFF]");
							if (inv.getItem(slot).equals(pdi.getMenuItem(false)) || inv.getItem(slot).equals(pdi.getMenuItem(true))) {
								if (pt == ParticleType.NOTE) {
									if (EnumUtil.isEnumType(Note.NoteType.class, pdi.toString().toUpperCase())) {
										Note.NoteType noteType = Note.NoteType.valueOf(pdi.toString().toUpperCase());
										ParticleDetails pd = new ParticleDetails(pt);
										pd.noteType = noteType;
										addEffect(player, pd, menu.effectType, pt, menu, data);
										inv.setItem(slot, pt.getMenuItem(b));
									}
								}
								else if (pt == ParticleType.POTION) {
									if (EnumUtil.isEnumType(Potion.PotionType.class, pdi.toString().toUpperCase())) {
										Potion.PotionType potionType = Potion.PotionType.valueOf(pdi.toString().toUpperCase());
										ParticleDetails pd = new ParticleDetails(pt);
										pd.potionType = potionType;
										addEffect(player, pd, menu.effectType, pt, menu, data);
										inv.setItem(slot, pt.getMenuItem(b));
									}
								}
								else if (pt == ParticleType.SMOKE) {
									if (EnumUtil.isEnumType(Smoke.SmokeType.class, pdi.toString().toUpperCase())) {
										Smoke.SmokeType smokeType = Smoke.SmokeType.valueOf(pdi.toString().toUpperCase());
										ParticleDetails pd = new ParticleDetails(pt);
										pd.smokeType = smokeType;
										addEffect(player, pd, menu.effectType, pt, menu, data);
										inv.setItem(slot, pt.getMenuItem(b));
									}
								}
								else if (pt == ParticleType.SWIRL) {
									if (EnumUtil.isEnumType(Swirl.SwirlType.class, pdi.toString().toUpperCase())) {
										Swirl.SwirlType swirlType = Swirl.SwirlType.valueOf(pdi.toString().toUpperCase());
										ParticleDetails pd = new ParticleDetails(pt);
										pd.swirlType = swirlType;
										addEffect(player, pd, menu.effectType, pt, menu, data);
										inv.setItem(slot, pt.getMenuItem(b));
									}
								}
							}
						}
					}
				}

				event.setCancelled(true);
			}
		}
	}

	private boolean addEffect(Player player, ParticleDetails pd, EffectHolder.EffectType effectType, ParticleType particleType, Menu menu, String data) {
		EffectHolder eh = getHolder(player, effectType, particleType, menu, data);

		if (eh == null) {
			Logger.log(Logger.LogLevel.SEVERE, "Effect creation failed (" + data + ") while adding Particle Type (" + particleType.toString() + ") [Reported from MenuListener].", true);
			return false;
		}

		eh.addEffect(pd);

		return true;
	}

	private boolean addEffect(Player player, EffectHolder.EffectType effectType, ParticleType particleType, Menu menu, String data) {
		EffectHolder eh = getHolder(player, effectType, particleType, menu, data);

		if (eh == null) {
			Logger.log(Logger.LogLevel.SEVERE, "Effect creation failed (" + data + ") while adding Particle Type (" + particleType.toString() + ") [Reported from MenuListener].", true);
			return false;
		}

		HashSet<ParticleDetails> hashSet = new HashSet<ParticleDetails>();
		if (effectType == EffectHolder.EffectType.PLAYER) {
			ParticleDetails pd = new ParticleDetails(particleType);
			pd.setPlayer(player.getName(), player.getUniqueId());
			hashSet.add(pd);
		}
		else if (effectType == EffectHolder.EffectType.LOCATION) {
			ParticleDetails pd = new ParticleDetails(particleType);
			hashSet.add(pd);
		}
		else if (effectType == EffectHolder.EffectType.MOB) {
			ParticleDetails pd = new ParticleDetails(particleType);
			pd.setMob(menu.mobUuid);
			hashSet.add(pd);

		}

		eh.addEffect(particleType);
		return true;
	}

	private boolean removeEffect(Player player, EffectHolder.EffectType effectType, ParticleType particleType, Menu menu, String data) {
		EffectHolder eh = getHolder(player, effectType, particleType, menu, data);
		if (eh == null) {
			return false;
		}

		eh.removeEffect(particleType);

		return true;
	}

	private EffectHolder getHolder(Player player, EffectHolder.EffectType effectType, ParticleType particleType, Menu menu, String data) {
		EffectHolder eh = null;
		if (effectType == EffectHolder.EffectType.LOCATION) {
			try {
				eh = EffectHandler.getInstance().getEffect(menu.location);
			} catch (Exception e) {
				Logger.log(Logger.LogLevel.SEVERE, "Failed to create Location (" + data + ") whilst finding EffectHolder (" + particleType.toString() + ")", e, true);
				return null;
			}
		}
		else if (effectType == EffectHolder.EffectType.PLAYER) {
			eh = EffectHandler.getInstance().getEffect(menu.viewer.getName());
		}
		else if (effectType == EffectHolder.EffectType.MOB) {
			eh = EffectHandler.getInstance().getEffect(menu.mobUuid);
		}

		if (eh == null) {
			HashSet<ParticleDetails> hashSet = new HashSet<ParticleDetails>();
			if (effectType == EffectHolder.EffectType.PLAYER) {
				eh = EffectCreator.createPlayerHolder(hashSet, effectType, player.getName(), player.getUniqueId());
			}
			else if (effectType == EffectHolder.EffectType.LOCATION) {
				Location l;
				try {
					String[] s = data.split(", ");
					l = new Location(Bukkit.getWorld(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]));
				} catch (Exception e) {
					l = null;
				}
				if (l == null) {
					Logger.log(Logger.LogLevel.SEVERE, "Failed to create Location (" + data + ") whilst finding EffectHolder (" + particleType.toString() + ") [Reported from MenuListener].", true);
					return null;
				}
				eh = EffectCreator.createLocHolder(hashSet, effectType, l);
			}
			else if (effectType == EffectHolder.EffectType.MOB) {
				UUID uuid = UUID.fromString(data);
				eh = EffectCreator.createMobHolder(hashSet, effectType, uuid);
			}
		}

		return eh;
	}
}