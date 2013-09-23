package com.github.dsh105.sparktrail.particle;

import com.github.dsh105.sparktrail.SparkTrail;
import com.github.dsh105.sparktrail.config.options.ConfigOptions;
import com.github.dsh105.sparktrail.logger.Logger;
import com.github.dsh105.sparktrail.particle.EffectHolder.EffectType;
import com.github.dsh105.sparktrail.particle.type.*;
import com.github.dsh105.sparktrail.particle.type.Void;
import com.github.dsh105.sparktrail.util.EnumUtil;
import com.github.dsh105.sparktrail.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * Project by DSH105
 */

public enum ParticleType {
	BLOCKBREAK(BlockBreak.class, 20, Material.IRON_PICKAXE, (short) 0, true),
	BLOCKCRACK(BlockCrack.class, 10, Material.getMaterial(20), (short) 0, false),
	CLOUD(Cloud.class, 20, Material.getMaterial(351), (short) 15, false),
	COOKIE(Cookie.class, 20, Material.COOKIE, (short) 0, false),
	CRITICAL(Critical.class, 20, Material.IRON_SWORD, (short) 0, false),
	ENDER(Ender.class, 20, Material.EYE_OF_ENDER, (short) 0, false),
	EXPLOSION(Explosion.class, 20, Material.TNT, (short) 0, false),
	FIRE(Fire.class, 20, Material.FIRE, (short) 0, false),
	FIREWORK(Firework.class, 20, Material.FIREWORK, (short) 0, true),
	HEART(Heart.class, 20, Material.NAME_TAG, (short) 0, false),
	LAVADRIP(LavaDrip.class, 20, Material.LAVA, (short) 0, false),
	MAGIC(Magic.class, 20, Material.ENCHANTED_BOOK, (short) 0, false),
	NOTE(Note.class, 20, Material.NOTE_BLOCK, (short) 0, true),
	PORTAL(Portal.class, 20, Material.PORTAL, (short) 0, false),
	POTION(Potion.class, 20, Material.getMaterial(373), (short) 8193, true),
	RAINBOWSWIRL(RainbowSwirl.class, 20, Material.getMaterial(373), (short) 16385, false),
	RUNES(Runes.class, 20, Material.ENCHANTMENT_TABLE, (short) 0, false),
	SLIME(Slime.class, 20, Material.SLIME_BALL, (short) 0, false),
	SMOKE(Smoke.class, 20, Material.COAL, (short) 0, true),
	SNOW(Snow.class, 20, Material.SNOW, (short) 0, false),
	SNOWBALL(Snowball.class, 20, Material.SNOW_BALL, (short) 0, false),
	SPARK(Spark.class, 20, Material.TORCH, (short) 0, false),
	SPARKLE(Sparkle.class, 20, Material.EMERALD, (short) 0, false),
	SPLASH(Splash.class, 20, Material.WATER, (short) 0, false),
	SWIRL(Swirl.class, 20, Material.BEACON, (short) 0, true),
	VOID(Void.class, 20, Material.ENDER_PORTAL, (short) 0, false),
	WATERDRIP(WaterDrip.class, 20, Material.WATER_LILY, (short) 0, false);

	private Class<? extends Effect> effectClass;
	private int frequency;
	private Material material;
	private short data;
	private boolean requiresDataMenu;
	private ArrayList<EffectType> incompatibleTypes = new ArrayList<EffectType>();

	ParticleType(Class<? extends Effect> effectClass, int frequency, Material material, short data, boolean requiresDataMenu, EffectType... incompatibleTypes) {
		this.effectClass = effectClass;
		this.frequency = frequency;
		this.material = material;
		this.data = data;
		this.requiresDataMenu = requiresDataMenu;

		for (EffectType et : incompatibleTypes) {
			this.incompatibleTypes.add(et);
		}
	}

	public int getFrequency() {
		return SparkTrail.getInstance().getConfig(SparkTrail.ConfigType.MAIN).getInt("effects." + StringUtil.capitalise(this.toString()) + ".frequency", this.frequency);
	}

	public boolean requiresDataMenu() {
		return this.requiresDataMenu;
	}

	public ArrayList<EffectType> getIncompatibleTypes() {
		return this.incompatibleTypes;
	}

	public DisplayType getDisplayType() {
		String s = SparkTrail.getInstance().getConfig(SparkTrail.ConfigType.MAIN).getString("effects." + StringUtil.capitalise(this.toString()) + ".playType", "normal");
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
		meta.setDisplayName(ChatColor.GOLD + StringUtil.capitalise(this.toString()));
		meta.setLore(Arrays.asList(ChatColor.YELLOW + "Frequency: " + ChatColor.GREEN + this.getFrequency(), ChatColor.YELLOW + "Display: " + ChatColor.GREEN + StringUtil.capitalise(this.getDisplayType().toString())));
		i.setItemMeta(meta);
		return i;
	}

	public ItemStack getMenuItem(boolean nameFlag) {
		ItemStack i = new ItemStack(this.material, 1, this.data);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + StringUtil.capitalise(this.toString()) + (nameFlag ? ChatColor.GREEN + " [TOGGLE ON]" : ChatColor.RED + " [TOGGLE OFF]"));
		meta.setLore(Arrays.asList(ChatColor.YELLOW + "Frequency: " + ChatColor.GREEN + this.getFrequency(), ChatColor.YELLOW + "Display: " + ChatColor.GREEN + StringUtil.capitalise(this.getDisplayType().toString())));
		i.setItemMeta(meta);
		return i;
	}

	public Effect getEffectInstance(EffectHolder effectHolder) {
		Effect effect = null;
		try {
			Object o = this.effectClass.getConstructor(EffectHolder.class, ParticleType.class, EffectType.class).newInstance(effectHolder, this);
		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to create new Effect instance [" + this.toString() + "].", e, true);
		}
		return effect;
	}

	public BlockBreak getBlockBreakInstance(EffectHolder effectHolder, int id, int meta) {
		BlockBreak effect = null;
		try {
			Object o = this.effectClass.getConstructor(EffectHolder.class, ParticleType.class, EffectType.class, Integer.class, Integer.class).newInstance(effectHolder, this, id, meta);
		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to create new Effect instance [" + this.toString() + "].", e, true);
		}
		return effect;
	}

	public Firework getFireworkInstance(EffectHolder effectHolder, FireworkEffect fe) {
		Firework effect = null;
		try {
			Object o = this.effectClass.getConstructor(EffectHolder.class, ParticleType.class, EffectType.class, FireworkEffect.class).newInstance(effectHolder, this, fe);
		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to create new Effect instance [" + this.toString() + "].", e, true);
		}
		return effect;
	}

	public Note getNoteInstance(EffectHolder effectHolder, Note.NoteType noteType) {
		Note effect = null;
		try {
			Object o = this.effectClass.getConstructor(EffectHolder.class, ParticleType.class, EffectType.class, Note.NoteType.class).newInstance(effectHolder, this, noteType);
		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to create new Effect instance [" + this.toString() + "].", e, true);
		}
		return effect;
	}

	public Potion getPotionInstance(EffectHolder effectHolder, Potion.PotionType potionType) {
		Potion effect = null;
		try {
			Object o = this.effectClass.getConstructor(EffectHolder.class, ParticleType.class, EffectType.class, Potion.PotionType.class).newInstance(effectHolder, this, potionType);
		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to create new Effect instance [" + this.toString() + "].", e, true);
		}
		return effect;
	}

	public Smoke getSmokeInstance(EffectHolder effectHolder, Smoke.SmokeType smokeType) {
		Smoke effect = null;
		try {
			Object o = this.effectClass.getConstructor(EffectHolder.class, ParticleType.class, EffectType.class, Smoke.SmokeType.class).newInstance(effectHolder, this, smokeType);
		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to create new Effect instance [" + this.toString() + "].", e, true);
		}
		return effect;
	}

	public Swirl getSwirlInstance(EffectHolder effectHolder, Swirl.SwirlType swirlType, UUID uuid) {
		Swirl effect = null;
		try {
			Object o = this.effectClass.getConstructor(EffectHolder.class, ParticleType.class, EffectType.class, Swirl.SwirlType.class, UUID.class).newInstance(effectHolder, this, swirlType, uuid);
		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to create new Effect instance [" + this.toString() + "].", e, true);
		}
		return effect;
	}
}