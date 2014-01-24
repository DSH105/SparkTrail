package com.dsh105.sparktrail.particle;

import com.dsh105.sparktrail.SparkTrailPlugin;
import com.dsh105.sparktrail.particle.EffectHolder.EffectType;
import com.dsh105.sparktrail.particle.type.*;
import com.dsh105.sparktrail.particle.type.Void;
import io.github.dsh105.dshutils.logger.Logger;
import io.github.dsh105.dshutils.util.EnumUtil;
import io.github.dsh105.dshutils.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;


public enum ParticleType {
    BLOCKBREAK(BlockBreak.class, 20, Material.IRON_PICKAXE, (short) 0, "Block Break", true),
    //BLOCKCRACK(BlockCrack.class, 10, Material.getMaterial(20), (short) 0, "Block Crack", false),
    CLOUD(Cloud.class, 20, Material.getMaterial(351), (short) 15, "Cloud", false),
    COOKIE(Cookie.class, 20, Material.COOKIE, (short) 0, "Cookie", false),
    CRITICAL(Critical.class, 20, Material.IRON_SWORD, (short) 0, "Critical", true),
    DUST(Dust.class, 20, Material.SULPHUR, (short) 0, "Dust", true),
    EMBER(Ember.class, 20, Material.TORCH, (short) 0, "Ember", false),
    ENDER(Ender.class, 20, Material.EYE_OF_ENDER, (short) 0, "Ender", false),
    EXPLOSION(Explosion.class, 20, Material.TNT, (short) 0, "Explosion", false),
    ITEMSPRAY(ItemSpray.class, 20, Material.DIAMOND, (short) 0, "ItemSpray", false),
    FIRE(Fire.class, 20, Material.FIRE, (short) 0, "Fire", false),
    FIREWORK(Firework.class, 20, Material.FIREWORK, (short) 0, "Firework", true),
    FOOTSTEP(FootStep.class, 20, Material.CHAINMAIL_BOOTS, (short) 0, "FootStep", false),
    HEART(Heart.class, 20, Material.NAME_TAG, (short) 0, "Heart", false),
    LAVADRIP(LavaDrip.class, 20, Material.LAVA, (short) 0, "Lava Drip", false),
    MAGIC(Magic.class, 20, Material.ENCHANTED_BOOK, (short) 0, "Magic", false),
    NOTE(Note.class, 20, Material.getMaterial(2259), (short) 0, "Rainbow Note", false),
    PORTAL(Portal.class, 20, Material.PORTAL, (short) 0, "Portal", false),
    POTION(Potion.class, 20, Material.getMaterial(373), (short) 8193, "Potion", true),
    RAINBOWSWIRL(RainbowSwirl.class, 20, Material.getMaterial(373), (short) 16385, "Rainbow Swirl", false),
    RUNES(Runes.class, 20, Material.ENCHANTMENT_TABLE, (short) 0, "Runes", false),
    SLIME(Slime.class, 20, Material.SLIME_BALL, (short) 0, "Slime", false),
    SMOKE(Smoke.class, 20, Material.COAL, (short) 0, "Smoke", true),
    SNOW(Snow.class, 20, Material.SNOW, (short) 0, "Snow", false),
    SNOWBALL(Snowball.class, 20, Material.SNOW_BALL, (short) 0, "Snowball", false),
    SPARKLE(Sparkle.class, 20, Material.EMERALD, (short) 0, "Sparkle", false),
    SPLASH(Splash.class, 20, Material.WATER, (short) 0, "Splash", false),
    SWIRL(Swirl.class, 20, Material.BEACON, (short) 0, "Swirl", true, EffectType.LOCATION),
    VOID(Void.class, 20, Material.ENDER_PORTAL, (short) 0, "Void", false),
    WATERDRIP(WaterDrip.class, 20, Material.WATER_LILY, (short) 0, "Water Drip", false);

    private Class<? extends Effect> effectClass;
    private int frequency;
    private Material material;
    private short data;
    private String name;
    private boolean requiresDataMenu;
    private ArrayList<EffectType> incompatibleTypes = new ArrayList<EffectType>();

    ParticleType(Class<? extends Effect> effectClass, int frequency, Material material, short data, String name, boolean requiresDataMenu, EffectType... incompatibleTypes) {
        this.effectClass = effectClass;
        this.frequency = frequency;
        this.material = material;
        this.data = data;
        this.name = name;
        this.requiresDataMenu = requiresDataMenu;

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
            Object o = this.effectClass.getConstructor(EffectHolder.class, ParticleType.class).newInstance(effectHolder, this);
            if (o instanceof Effect) {
                effect = (Effect) o;
            }
        } catch (Exception e) {
            Logger.log(Logger.LogLevel.SEVERE, "Failed to create new Effect instance [" + this.toString() + "].", e, true);
        }
        return effect;
    }
}