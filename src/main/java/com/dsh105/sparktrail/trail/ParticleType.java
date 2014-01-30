package com.dsh105.sparktrail.trail;

import com.dsh105.sparktrail.SparkTrailPlugin;
import com.dsh105.sparktrail.trail.EffectHolder.EffectType;
import com.dsh105.sparktrail.trail.type.*;
import com.dsh105.sparktrail.trail.type.Void;
import com.dsh105.sparktrail.trail.type.Sound;
import com.dsh105.dshutils.logger.Logger;
import com.dsh105.dshutils.util.EnumUtil;
import com.dsh105.dshutils.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;


public enum ParticleType {
    BLOCKBREAK(BlockBreak.class, 20, Material.IRON_PICKAXE, (short) 0, "Block Break", true, true),
    //BLOCKCRACK(BlockCrack.class, 10, Material.getMaterial(20), (short) 0, "Block Crack", false),
    CLOUD(Cloud.class, 20, Material.getMaterial(351), (short) 15, "Cloud", false, true),
    COOKIE(Cookie.class, 20, Material.COOKIE, (short) 0, "Cookie", false, true),
    CRITICAL(Critical.class, 20, Material.IRON_SWORD, (short) 0, "Critical", true, true),
    DUST(Dust.class, 20, Material.SULPHUR, (short) 0, "Dust", true, true),
    EMBER(Ember.class, 20, Material.TORCH, (short) 0, "Ember", false, true),
    ENDER(Ender.class, 20, Material.EYE_OF_ENDER, (short) 0, "Ender", false, true),
    EXPLOSION(Explosion.class, 20, Material.TNT, (short) 0, "Explosion", false, true),
    ITEMSPRAY(ItemSpray.class, 20, Material.DIAMOND, (short) 0, "ItemSpray", false, true),
    FIRE(Fire.class, 20, Material.FIRE, (short) 0, "Fire", false, true),
    FIREWORK(Firework.class, 20, Material.FIREWORK, (short) 0, "Firework", true, true),
    FOOTSTEP(FootStep.class, 20, Material.CHAINMAIL_BOOTS, (short) 0, "FootStep", false, true),
    HEART(Heart.class, 20, Material.NAME_TAG, (short) 0, "Heart", false, true),
    LAVADRIP(LavaDrip.class, 20, Material.LAVA, (short) 0, "Lava Drip", false, true),
    MAGIC(Magic.class, 20, Material.ENCHANTED_BOOK, (short) 0, "Magic", false, true),
    NOTE(Note.class, 20, Material.getMaterial(2259), (short) 0, "Rainbow Note", false, true),
    PORTAL(Portal.class, 20, Material.PORTAL, (short) 0, "Portal", false, true),
    POTION(Potion.class, 20, Material.getMaterial(373), (short) 8193, "Potion", true, true),
    RAINBOWSWIRL(RainbowSwirl.class, 20, Material.getMaterial(373), (short) 16385, "Rainbow Swirl", false, true),
    RUNES(Runes.class, 20, Material.ENCHANTMENT_TABLE, (short) 0, "Runes", false, true),
    SLIME(Slime.class, 20, Material.SLIME_BALL, (short) 0, "Slime", false, true),
    SMOKE(Smoke.class, 20, Material.COAL, (short) 0, "Smoke", true, true),
    SNOW(Snow.class, 20, Material.SNOW, (short) 0, "Snow", false, true),
    SNOWBALL(Snowball.class, 20, Material.SNOW_BALL, (short) 0, "Snowball", false, true),
    SPARKLE(Sparkle.class, 20, Material.EMERALD, (short) 0, "Sparkle", false, true),
    SPLASH(Splash.class, 20, Material.WATER, (short) 0, "Splash", false, true),
    SWIRL(Swirl.class, 20, Material.BEACON, (short) 0, "Swirl", true, true, EffectType.LOCATION),
    VOID(Void.class, 20, Material.ENDER_PORTAL, (short) 0, "Void", false, true),
    WATERDRIP(WaterDrip.class, 20, Material.WATER_LILY, (short) 0, "Water Drip", false, true),

    SOUND(Sound.class, 20, Material.JUKEBOX, (short) 0, "Sound", false, false);

    private Class<? extends Effect> effectClass;
    private int frequency;
    private Material material;
    private short data;
    private String name;
    private boolean requiresDataMenu;
    private boolean includeInMenu;
    private ArrayList<EffectType> incompatibleTypes = new ArrayList<EffectType>();

    ParticleType(Class<? extends Effect> effectClass, int frequency, Material material, short data, String name, boolean requiresDataMenu, boolean includeInMenu, EffectType... incompatibleTypes) {
        this.effectClass = effectClass;
        this.frequency = frequency;
        this.material = material;
        this.data = data;
        this.name = name;
        this.requiresDataMenu = requiresDataMenu;
        this.includeInMenu = includeInMenu;

        for (EffectType et : incompatibleTypes) {
            this.incompatibleTypes.add(et);
        }
    }

    public int getFrequency() {
        return SparkTrailPlugin.getInstance().getConfig(SparkTrailPlugin.ConfigType.MAIN).getInt("effects." + this.toString().toLowerCase() + ".frequency", this.frequency);
    }

    public boolean requiresDataMenu() {
        return this.requiresDataMenu;
    }

    public boolean includeInMenu() {
        return this.includeInMenu;
    }

    public ArrayList<EffectType> getIncompatibleTypes() {
        return this.incompatibleTypes;
    }

    public DisplayType getDisplayType() {
        String s = SparkTrailPlugin.getInstance().getConfig(SparkTrailPlugin.ConfigType.MAIN).getString("effects." + this.toString().toLowerCase() + ".playType", "normal");
        if (EnumUtil.isEnumType(DisplayType.class, s.toUpperCase())) {
            DisplayType dt = DisplayType.valueOf(s.toUpperCase());
            if (dt != null) {
                return dt;
            }
        }
        return null;
    }

    public ItemStack getMenuItem() {
        ItemStack i = new ItemStack(this.material, 1, this.data);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + this.name);
        meta.setLore(Arrays.asList(ChatColor.YELLOW + "Frequency: " + ChatColor.GREEN + this.getFrequency(), ChatColor.YELLOW + "Display: " + ChatColor.GREEN + StringUtil.capitalise(this.getDisplayType().toString())));
        i.setItemMeta(meta);
        return i;
    }

    public ItemStack getMenuItem(boolean nameFlag) {
        ItemStack i = new ItemStack(this.material, 1, this.data);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + this.name + (nameFlag ? ChatColor.GREEN + " [TOGGLE ON]" : ChatColor.RED + " [TOGGLE OFF]"));
        meta.setLore(Arrays.asList(ChatColor.YELLOW + "Frequency: " + ChatColor.GREEN + this.getFrequency(), ChatColor.YELLOW + "Display: " + ChatColor.GREEN + StringUtil.capitalise(this.getDisplayType().toString())));
        i.setItemMeta(meta);
        return i;
    }

    public String getName() {
        return this.name;
    }

    public Effect getEffectInstance(EffectHolder effectHolder) {
        Effect effect = null;
        try {
            Object o = this.effectClass.getConstructor(EffectHolder.class).newInstance(effectHolder);
            if (o instanceof Effect) {
                effect = (Effect) o;
            }
        } catch (Exception e) {
            Logger.log(Logger.LogLevel.SEVERE, "Failed to create new Effect instance [" + this.toString() + "].", e, true);
        }
        return effect;
    }
}