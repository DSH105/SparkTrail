package com.github.dsh105.sparktrail.menu;

import com.github.dsh105.sparktrail.SparkTrail;
import com.github.dsh105.sparktrail.chat.MenuChatListener;
import com.github.dsh105.sparktrail.chat.WaitingData;
import com.github.dsh105.sparktrail.data.EffectCreator;
import com.github.dsh105.sparktrail.data.EffectHandler;
import com.github.dsh105.sparktrail.logger.ConsoleLogger;
import com.github.dsh105.sparktrail.logger.Logger;
import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.ParticleDetails;
import com.github.dsh105.sparktrail.particle.ParticleType;
import com.github.dsh105.sparktrail.particle.type.*;
import com.github.dsh105.sparktrail.util.EnumUtil;
import com.github.dsh105.sparktrail.util.Lang;
import com.github.dsh105.sparktrail.util.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

		if (inv.getItem(slot) == null) {
			return;
		}

		if (title.startsWith("Trail GUI")) {
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
					if (inv.getItem(slot).equals(pt.getMenuItem()) || inv.getItem(slot).equals(pt.getMenuItem(false)) || inv.getItem(slot).equals(pt.getMenuItem(true))) {
						if (!pt.requiresDataMenu()) {
							if (inv.getItem(slot).equals(pt.getMenuItem(false))) {
								if (Permission.hasEffectPerm(player, true, pt, menu.effectType)) {
									if (removeEffect(player, menu.effectType, pt, menu, s)) {
										Lang.sendTo(player, Lang.EFFECT_REMOVED.toString().replace("%effect%", pt.getName()));
										inv.setItem(slot, pt.getMenuItem(true));
									}
								}
							}
							else if (inv.getItem(slot).equals(pt.getMenuItem(true))) {
								if (Permission.hasEffectPerm(player, true, pt, menu.effectType)) {
									if (addEffect(player, menu.effectType, pt, menu, s)) {
										Lang.sendTo(player, Lang.EFFECT_ADDED.toString().replace("%effect%", pt.getName()));
										inv.setItem(slot, pt.getMenuItem(false));
									}
								}
							}
						}
						else {
							if (pt == ParticleType.BLOCKBREAK || pt == ParticleType.FIREWORK) {
								if (inv.getItem(slot).equals(pt.getMenuItem(false))) {
									removeEffect(player, menu.effectType, pt, menu, s);
									inv.setItem(slot, pt.getMenuItem(true));
									event.setCancelled(true);
									return;
								}
								WaitingData wd = new WaitingData(menu.effectType, pt);
								wd.location = menu.location;
								wd.mobUuid = menu.mobUuid;
								wd.playerName = menu.playerName;
								MenuChatListener.AWAITING_DATA.put(player.getName(), wd);
								if (pt == ParticleType.BLOCKBREAK) {
									Lang.sendTo(player, Lang.ENTER_BLOCK.toString());
								}
								else if (pt == ParticleType.FIREWORK) {
									Lang.sendTo(player, Lang.ENTER_FIREWORK.toString());
								}
								player.closeInventory();
								event.setCancelled(true);
								ParticleMenu.openMenus.remove(player.getName());
								event.setCancelled(true);
								return;
							}
							else {
								player.closeInventory();
								event.setCancelled(true);
								ParticleMenu.openMenus.remove(player.getName());
								DataMenu dm = null;
								if (menu.effectType == EffectHolder.EffectType.PLAYER) {
									dm = new DataMenu(player, menu.playerName, pt);
								}
								else if (menu.effectType == EffectHolder.EffectType.LOCATION) {
									dm = new DataMenu(player, menu.location, pt);
								}
								else if (menu.effectType == EffectHolder.EffectType.MOB) {
									dm = new DataMenu(player, menu.mobUuid, pt);
								}
								if (dm != null) {
									if (dm.fail) {
										Lang.sendTo(player, Lang.MENU_ERROR.toString());
									}
									else {
										dm.open(false);
									}
								}
								return;
							}
						}
					}
				}
				event.setCancelled(true);
				return;
			}
		}
		else if (title.contains(" - Trail GUI - ")) {
			if (slot <= 26) {
				DataMenu menu = DataMenu.openMenus.get(player.getName());
				if (menu == null) {
					return;
				}
				String[] split = title.split(" - ");
				String particle = split[0];
				String data = split[2];

				if (inv.getItem(slot) != null && inv.getItem(slot).equals(DataMenu.BACK)) {
					player.closeInventory();
					event.setCancelled(true);
					DataMenu.openMenus.remove(player.getName());

					ParticleMenu pm = null;
					if (menu.effectType == EffectHolder.EffectType.PLAYER) {
						pm = new ParticleMenu(player, menu.playerName);
					}
					else if (menu.effectType == EffectHolder.EffectType.LOCATION) {
						pm = new ParticleMenu(player, menu.location);
					}
					else if (menu.effectType == EffectHolder.EffectType.MOB) {
						pm = new ParticleMenu(player, menu.mobUuid);
					}
					if (pm != null) {
						if (pm.fail) {
							Lang.sendTo(player, Lang.MENU_ERROR.toString());
						}
						else {
							pm.open(false);
						}
					}
					return;
				}

				if (EnumUtil.isEnumType(ParticleType.class, particle.toString().toUpperCase())) {
					ParticleType pt = ParticleType.valueOf(particle.toUpperCase());
					if (pt != null) {
						for (ParticleDataItem pdi : ParticleDataItem.values()) {
							boolean b = inv.getItem(slot).getItemMeta().getDisplayName().contains("OFF");
							if (inv.getItem(slot).equals(pdi.getMenuItem(false)) || inv.getItem(slot).equals(pdi.getMenuItem(true))) {
								if (pt == ParticleType.CRITICAL) {
									if (EnumUtil.isEnumType(Critical.CriticalType.class, pdi.toString().toUpperCase())) {
										Critical.CriticalType criticalType = Critical.CriticalType.valueOf(pdi.toString().toUpperCase());
										ParticleDetails pd = new ParticleDetails(pt);
										pd.criticalType = criticalType;
										if (Permission.hasEffectPerm(player, true, pt, criticalType.toString().toLowerCase(),menu.effectType)) {
											if (b) {
												if (removeEffect(player, pd, menu.effectType, menu, data)) {
													Lang.sendTo(player, Lang.EFFECT_REMOVED.toString().replace("%effect%", "Critical"));
													inv.setItem(slot, pdi.getMenuItem(b));
												}
											}
											else {
												if (addEffect(player, pd, menu.effectType, menu, data)) {
													Lang.sendTo(player, Lang.EFFECT_ADDED.toString().replace("%effect%", "Critical"));
													inv.setItem(slot, pdi.getMenuItem(b));
												}
											}
										}
									}
								}
								/*else if (pt == ParticleType.NOTE) {
									if (EnumUtil.isEnumType(Note.NoteType.class, pdi.toString().toUpperCase())) {
										Note.NoteType noteType = Note.NoteType.valueOf(pdi.toString().toUpperCase());
										ParticleDetails pd = new ParticleDetails(pt);
										pd.noteType = noteType;
										if (b) {
											if (removeEffect(player, pd, menu.effectType, menu, data)) {
												inv.setItem(slot, pdi.getMenuItem(b));
											}
										}
										else {
											if (addEffect(player, pd, menu.effectType, menu, data)) {
												inv.setItem(slot, pdi.getMenuItem(b));
											}
										}
									}
								}*/
								else if (pt == ParticleType.POTION) {
									if (EnumUtil.isEnumType(Potion.PotionType.class, pdi.toString().toUpperCase())) {
										Potion.PotionType potionType = Potion.PotionType.valueOf(pdi.toString().toUpperCase());
										ParticleDetails pd = new ParticleDetails(pt);
										pd.potionType = potionType;
										if (Permission.hasEffectPerm(player, true, pt, potionType.toString().toLowerCase(), menu.effectType)) {
											if (b) {
												if (removeEffect(player, pd, menu.effectType, menu, data)) {
													Lang.sendTo(player, Lang.EFFECT_REMOVED.toString().replace("%effect%", "Potion"));
													inv.setItem(slot, pdi.getMenuItem(b));
												}
											}
											else {
												if (addEffect(player, pd, menu.effectType, menu, data)) {
													Lang.sendTo(player, Lang.EFFECT_ADDED.toString().replace("%effect%", "Potion"));
													inv.setItem(slot, pdi.getMenuItem(b));
												}
											}
										}
									}
								}
								else if (pt == ParticleType.SMOKE) {
									if (EnumUtil.isEnumType(Smoke.SmokeType.class, pdi.toString().toUpperCase())) {
										Smoke.SmokeType smokeType = Smoke.SmokeType.valueOf(pdi.toString().toUpperCase());
										ParticleDetails pd = new ParticleDetails(pt);
										pd.smokeType = smokeType;
										if (Permission.hasEffectPerm(player, true, pt, smokeType.toString().toLowerCase(), menu.effectType)) {
											if (b) {
												if (removeEffect(player, pd, menu.effectType, menu, data)) {
													Lang.sendTo(player, Lang.EFFECT_REMOVED.toString().replace("%effect%", "Smoke"));
													inv.setItem(slot, pdi.getMenuItem(b));
												}
											}
											else {
												if (addEffect(player, pd, menu.effectType, menu, data)) {
													Lang.sendTo(player, Lang.EFFECT_ADDED.toString().replace("%effect%", "Smoke"));
													inv.setItem(slot, pdi.getMenuItem(b));
												}
											}
										}
									}
								}
								else if (pt == ParticleType.SWIRL) {
									if (EnumUtil.isEnumType(Swirl.SwirlType.class, pdi.toString().toUpperCase())) {
										Swirl.SwirlType swirlType = Swirl.SwirlType.valueOf(pdi.toString().toUpperCase());
										ParticleDetails pd = new ParticleDetails(pt);
										pd.swirlType = swirlType;
										pd.setPlayer(player.getName(), player.getUniqueId());
										if (Permission.hasEffectPerm(player, true, pt, swirlType.toString().toLowerCase(), menu.effectType)) {
											if (b) {
												if (removeEffect(player, pd, menu.effectType, menu, data)) {
													Lang.sendTo(player, Lang.EFFECT_REMOVED.toString().replace("%effect%", "Swirl"));
													inv.setItem(slot, pdi.getMenuItem(b));
												}
											}
											else {
												if (addEffect(player, pd, menu.effectType, menu, data)) {
													Lang.sendTo(player, Lang.EFFECT_ADDED.toString().replace("%effect%", "Swirl"));
													inv.setItem(slot, pdi.getMenuItem(b));
												}
											}
										}
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

	private boolean addEffect(Player player, ParticleDetails pd, EffectHolder.EffectType effectType, Menu menu, String data) {
		EffectHolder eh = getHolder(player, effectType, pd.getParticleType(), menu, data);

		if (eh == null) {
			Logger.log(Logger.LogLevel.SEVERE, "Effect creation failed (" + data + ") while adding Particle Type (" + pd.getParticleType().toString() + ") [Reported from MenuListener].", true);
			return false;
		}

		eh.addEffect(pd);

		return true;
	}

	private boolean removeEffect(Player player, ParticleDetails pd, EffectHolder.EffectType effectType, Menu menu, String data) {
		EffectHolder eh = getHolder(player, effectType, pd.getParticleType(), menu, data);

		if (eh == null) {
			Logger.log(Logger.LogLevel.SEVERE, "Effect creation failed (" + data + ") while adding Particle Type (" + pd.getParticleType().toString() + ") [Reported from MenuListener].", true);
			return false;
		}

		eh.removeEffect(pd);

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
			Player p = Bukkit.getPlayerExact(menu.playerName);
			if (p == null) {
				Logger.log(Logger.LogLevel.SEVERE, "Effect creation failed (" + data + ") while adding Particle Type (" + particleType.toString() + ") [Reported from MenuListener].", true);
				return false;
			}
			pd.setPlayer(menu.playerName, p.getUniqueId());
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
			eh = EffectHandler.getInstance().getEffect(menu.playerName);
		}
		else if (effectType == EffectHolder.EffectType.MOB) {
			eh = EffectHandler.getInstance().getEffect(menu.mobUuid);
		}

		if (eh == null) {
			HashSet<ParticleDetails> hashSet = new HashSet<ParticleDetails>();
			if (effectType == EffectHolder.EffectType.PLAYER) {
				Player p = Bukkit.getPlayerExact(menu.playerName);
				if (p == null) {
					Logger.log(Logger.LogLevel.SEVERE, "Failed to create Player Effect (" + data + ") while finding Effect Holder (" + particleType.toString() + ") [Reported from MenuListener].", true);
					return null;
				}
				eh = EffectCreator.createPlayerHolder(hashSet, effectType, menu.playerName);
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