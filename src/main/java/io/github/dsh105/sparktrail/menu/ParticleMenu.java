package io.github.dsh105.sparktrail.menu;

import io.github.dsh105.sparktrail.SparkTrail;
import io.github.dsh105.sparktrail.api.event.MenuOpenEvent;
import io.github.dsh105.sparktrail.data.EffectHandler;
import io.github.dsh105.sparktrail.particle.Effect;
import io.github.dsh105.sparktrail.particle.EffectHolder;
import io.github.dsh105.sparktrail.particle.ParticleType;
import io.github.dsh105.sparktrail.util.Lang;
import io.github.dsh105.sparktrail.util.Serialise;
import io.github.dsh105.sparktrail.util.StringUtil;
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


public class ParticleMenu extends Menu {

    public static HashMap<String, ParticleMenu> openMenus = new HashMap<String, ParticleMenu>();
    public static ItemStack CLOSE;

    static {
        ItemStack item = new ItemStack(Material.BOOK, 1, (short) 0);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Close");
        item.setItemMeta(meta);
        CLOSE = item;
    }

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
        this.viewer = viewer;
        this.inv = Bukkit.createInventory(viewer, size, name);
    }

    public void setItems() {
        EffectHolder eh = null;
        if (effectType == EffectHolder.EffectType.PLAYER) {
            eh = EffectHandler.getInstance().getEffect(this.playerName);
        } else if (effectType == EffectHolder.EffectType.LOCATION) {
            eh = EffectHandler.getInstance().getEffect(this.location);
        } else if (effectType == EffectHolder.EffectType.MOB) {
            eh = EffectHandler.getInstance().getEffect(this.mobUuid);
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

        int book = size - 1;
        inv.setItem(book, CLOSE);
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
        openMenus.put(viewer.getName(), this);
    }
}