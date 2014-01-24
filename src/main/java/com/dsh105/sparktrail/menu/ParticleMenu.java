package com.dsh105.sparktrail.menu;

import com.dsh105.sparktrail.SparkTrailPlugin;
import com.dsh105.sparktrail.api.event.MenuOpenEvent;
import com.dsh105.sparktrail.chat.MenuChatListener;
import com.dsh105.sparktrail.data.EffectManager;
import com.dsh105.sparktrail.particle.Effect;
import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.ParticleType;
import com.dsh105.sparktrail.util.Lang;
import com.dsh105.sparktrail.util.Serialise;
import io.github.dsh105.dshutils.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;


public class ParticleMenu extends Menu {

    public static HashMap<String, ParticleMenu> openMenus = new HashMap<String, ParticleMenu>();
    protected MenuIcon[] endItems;

    Inventory inv;
    private int size;
    public EffectHolder.EffectType effectType;

    public ParticleMenu(Player viewer, UUID mobUuid) {
        this(viewer, EffectHolder.EffectType.MOB, "Trail GUI - " + StringUtil.capitalise(Serialise.getMob(mobUuid).getType().toString()));
        this.mobUuid = mobUuid;
        setItems();
    }

    public ParticleMenu(Player viewer, String playerName) {
        this(viewer, EffectHolder.EffectType.PLAYER, "Trail GUI - " + playerName);
        this.playerName = playerName;
        Player p = Bukkit.getPlayerExact(playerName);
        if (p != null) {
            this.mobUuid = p.getUniqueId();
        }
        setItems();
    }

    public ParticleMenu(Player viewer, Location location) {
        this(viewer, EffectHolder.EffectType.LOCATION, "Trail GUI - " + StringUtil.capitalise(location.getWorld().getName()) + ", " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ());
        this.location = location;
        setItems();
    }

    private ParticleMenu(Player viewer, EffectHolder.EffectType effectType, String name) {
        this.size = 45;
        this.effectType = effectType;
        this.viewer = viewer.getName();
        this.inv = Bukkit.createInventory(viewer, size, name);

        endItems = new MenuIcon[]{

                new MenuIcon(this, Material.BOOK, (short) 0, ChatColor.GOLD + "Close"),

                new MenuIcon(this, Material.WATCH, (short) 0, ChatColor.GOLD + "Timeout") {
                    @Override
                    public void onClick() {
                        if (this.getViewer() != null) {
                            Lang.sendTo(this.getViewer(), Lang.ENTER_TIMEOUT.toString());
                            MenuChatListener.AWAITING_TIMEOUT_INPUT.add(this.getViewer().getName());
                        }
                    }
                },

                new MenuIcon(this, Material.WOOL, (short) 5, ChatColor.GOLD + "Start") {
                    @Override
                    public void onClick() {
                        if (this.getViewer() != null) {
                            EffectHolder eh = EffectManager.getInstance().createFromFile(this.getViewer().getName());
                            if (eh == null || eh.getEffects().isEmpty()) {
                                Lang.sendTo(this.getViewer(), Lang.NO_EFFECTS_TO_LOAD.toString());
                                EffectManager.getInstance().clear(eh);
                                return;
                            }
                            Lang.sendTo(this.getViewer(), Lang.EFFECTS_LOADED.toString());
                        }
                    }
                },

                new MenuIcon(this, Material.WOOL, (short) 14, ChatColor.GOLD + "Stop") {
                    @Override
                    public void onClick() {
                        EffectHolder eh = EffectManager.getInstance().getEffect(this.getViewer().getName());
                        if (eh == null) {
                            Lang.sendTo(this.getViewer(), Lang.NO_ACTIVE_EFFECTS.toString());
                            return;
                        }
                        EffectManager.getInstance().remove(eh);
                        Lang.sendTo(this.getViewer(), Lang.EFFECTS_STOPPED.toString());
                    }
                },

                new MenuIcon(this, Material.WOOL, (short) 0, ChatColor.GOLD + "Clear") {
                    @Override
                    public void onClick() {
                        EffectHolder eh = EffectManager.getInstance().getEffect(this.getViewer().getName());
                        if (eh == null) {
                            Lang.sendTo(this.getViewer(), Lang.NO_ACTIVE_EFFECTS.toString());
                            return;
                        }
                        EffectManager.getInstance().clear(eh);
                        Lang.sendTo(this.getViewer(), Lang.EFFECTS_CLEARED.toString());
                    }
                }};
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
        for (ParticleType pt : ParticleType.values()) {
            if (pt.requiresDataMenu() && pt != ParticleType.BLOCKBREAK && pt != ParticleType.FIREWORK) {
                inv.setItem(i++, pt.getMenuItem());
            } else {
                boolean hasEffect = false;
                if (eh != null && !(eh.getEffects() == null || eh.getEffects().isEmpty())) {
                    for (Effect e : eh.getEffects()) {
                        if (e != null) {
                            if (e.getParticleType() == pt) {
                                hasEffect = true;
                            }
                        }
                    }
                }
                inv.setItem(i++, pt.getMenuItem(!hasEffect));
            }
        }

        int i2 = 0;
        for (int i3 = size - 1; i3 < endItems.length; i3--) {
            inv.setItem(i3, endItems[i2].getStack());
        }
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