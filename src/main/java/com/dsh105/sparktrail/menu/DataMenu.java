/*
 * This file is part of SparkTrail 3.
 *
 * SparkTrail 3 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SparkTrail 3 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SparkTrail 3.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.dsh105.sparktrail.menu;

import com.dsh105.dshutils.logger.Logger;
import com.dsh105.dshutils.util.StringUtil;
import com.dsh105.sparktrail.SparkTrailPlugin;
import com.dsh105.sparktrail.api.event.MenuOpenEvent;
import com.dsh105.sparktrail.data.EffectManager;
import com.dsh105.sparktrail.trail.Effect;
import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.ParticleType;
import com.dsh105.sparktrail.trail.type.Critical;
import com.dsh105.sparktrail.trail.type.Potion;
import com.dsh105.sparktrail.trail.type.Smoke;
import com.dsh105.sparktrail.trail.type.Swirl;
import com.dsh105.sparktrail.util.Lang;
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
        this(viewer, EffectHolder.EffectType.MOB, StringUtil.capitalise(particleType.toString()) + " - Trail GUI");
        this.particleType = particleType;
        this.mobUuid = mobUuid;
        setItems();
    }

    public DataMenu(Player viewer, String playerName, ParticleType particleType) {
        this(viewer, EffectHolder.EffectType.PLAYER, StringUtil.capitalise(particleType.toString()) + " - Trail GUI");
        this.particleType = particleType;
        this.playerName = playerName;
        Player p = Bukkit.getPlayerExact(playerName);
        if (p != null) {
            this.mobUuid = p.getUniqueId();
        }
        setItems();
    }

    public DataMenu(Player viewer, Location location, ParticleType particleType) {
        this(viewer, EffectHolder.EffectType.LOCATION, StringUtil.capitalise(particleType.toString()) + " - Trail GUI");
        this.particleType = particleType;
        this.location = location;
        setItems();
    }

    private DataMenu(Player viewer, EffectHolder.EffectType effectType, String name) {
        this.size = 27;
        this.effectType = effectType;
        this.viewer = viewer.getName();
        this.inv = Bukkit.createInventory(viewer, size, name);
    }

    public void setItems() {
        EffectHolder eh = null;
        if (effectType == EffectHolder.EffectType.PLAYER) {
            eh = EffectManager.getInstance().getEffect(this.playerName);
        } else if (effectType == EffectHolder.EffectType.LOCATION) {
            eh = EffectManager.getInstance().getEffect(this.location);
        } else if (effectType == EffectHolder.EffectType.MOB) {
            eh = EffectManager.getInstance().getEffect(this.mobUuid);
        }

        int i = 0;
        for (ParticleDataItem pdi : ParticleDataItem.values()) {
            if (pdi.isType(this.particleType)) {
                boolean hasEffect = false;
                if (eh != null && !(eh.getEffects() == null || eh.getEffects().isEmpty())) {
                    for (Effect e : eh.getEffects()) {
                        if (e.getParticleType() == this.particleType) {
                            if (this.particleType == ParticleType.CRITICAL) {
                                try {
                                    if (((Critical) e).criticalType == Critical.CriticalType.valueOf(pdi.toString().toUpperCase())) {
                                        hasEffect = true;
                                    }
                                } catch (Exception ex) {
                                    Logger.log(Logger.LogLevel.WARNING, "Could not initialise Trail Data Menu [Player: " + this.viewer + "] for Particle [" + particleType.toString() + "].", ex, true);
                                    continue;
                                }
                            }
                            /*else if (this.particleType == ParticleType.NOTE) {
                                try {
									if (((Note) e).noteType == Note.NoteType.valueOf(pdi.toString().toUpperCase())) {
										hasEffect = true;
									}
								} catch (Exception ex) {
									Logger.log(Logger.LogLevel.WARNING, "Could not initialise Trail Data Menu [Player: " + this.viewer.getName() + "] for Particle [" + particleType.toString() + "].", ex, true);
									continue;
								}
							}*/
                            else if (this.particleType == ParticleType.POTION) {
                                try {
                                    if (((Potion) e).potionType == Potion.PotionType.valueOf(pdi.toString().toUpperCase())) {
                                        hasEffect = true;
                                    }
                                } catch (Exception ex) {
                                    Logger.log(Logger.LogLevel.WARNING, "Could not initialise Trail Data Menu [Player: " + this.viewer + "] for Particle [" + particleType.toString() + "].", ex, true);
                                    continue;
                                }
                            } else if (this.particleType == ParticleType.SMOKE) {
                                try {
                                    if (((Smoke) e).smokeType == Smoke.SmokeType.valueOf(pdi.toString().toUpperCase())) {
                                        hasEffect = true;
                                    }
                                } catch (Exception ex) {
                                    Logger.log(Logger.LogLevel.WARNING, "Could not initialise Trail Data Menu [Player: " + this.viewer + "] for Particle [" + particleType.toString() + "].", ex, true);
                                    continue;
                                }
                            } else if (this.particleType == ParticleType.SWIRL) {
                                try {
                                    if (((Swirl) e).swirlType == Swirl.SwirlType.valueOf(pdi.toString().toUpperCase())) {
                                        hasEffect = true;
                                    }
                                } catch (Exception ex) {
                                    Logger.log(Logger.LogLevel.WARNING, "Could not initialise Trail Data Menu [Player: " + this.viewer + "] for Particle [" + particleType.toString() + "].", ex, true);
                                    continue;
                                }
                            }
                        }
                    }
                }
                inv.setItem(i++, pdi.getMenuItem(!hasEffect));
            }
        }

        int book = size - 1;
        inv.setItem(book, BACK);
    }

    public void open(boolean sendMessage) {
        if (this.getViewer() == null) {
            return;
        }
        MenuOpenEvent menuEvent = new MenuOpenEvent(this.getViewer(), MenuOpenEvent.MenuType.MAIN);
        SparkTrailPlugin.getInstance().getServer().getPluginManager().callEvent(menuEvent);
        if (menuEvent.isCancelled()) {
            return;
        }
        this.getViewer().openInventory(this.inv);
        if (sendMessage) {
            Lang.sendTo(this.getViewer(), Lang.OPEN_MENU.toString());
        }
        openMenus.put(this.viewer, this);
    }

}