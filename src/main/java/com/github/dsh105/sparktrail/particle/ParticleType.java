package com.github.dsh105.sparktrail.particle;

import com.github.dsh105.sparktrail.logger.Logger;
import com.github.dsh105.sparktrail.particle.type.*;
import com.github.dsh105.sparktrail.particle.type.Void;
import org.bukkit.FireworkEffect;

import java.util.UUID;

/**
 * Project by DSH105
 */

public enum ParticleType {
	BLOCKBREAK(BlockBreak.class, 20),
	BLOCKCRACK(BlockCrack.class, 10),
	CLOUD(Cloud.class, 20),
	COOKIE(Cookie.class, 20),
	CRITICAL(Critical.class, 20),
	ENDER(Ender.class, 20),
	EXPLOSION(Explosion.class, 20),
	FIRE(Fire.class, 20),
	FIREWORK(Firework.class, 20),
	HEART(Heart.class, 20),
	LAVADRIP(LavaDrip.class, 20),
	MAGIC(Magic.class, 20),
	NOTE(Note.class, 20),
	PORTAL(Portal.class, 20),
	POTION(Potion.class, 20),
	RAINBOWSWIRL(RainbowSwirl.class, 20),
	RUNES(Runes.class, 20),
	SLIME(Slime.class, 20),
	SMOKE(Smoke.class, 20),
	SNOW(Snow.class, 20),
	SNOWBALL(Snowball.class, 20),
	SPARK(Spark.class, 20),
	SPARKLE(Sparkle.class, 20),
	SPLASH(Splash.class, 20),
	SWIRL(Swirl.class, 20),
	VOID(Void.class, 20),
	WATERDRIP(WaterDrip.class, 20);

	private Class<? extends Effect> effectClass;
	private int frequency;
	private DisplayType displayType;

	ParticleType(Class<? extends Effect> effectClass, int frequency) {
		this.effectClass = effectClass;
		this.frequency = frequency;
		this.displayType = displayType;
	}

	public int getFrequency() {
		return this.frequency;
	}

	public Class<? extends Effect> getEffectInstance(EffectType effectType) {
		Effect effect = null;
		try {
			Object o = this.effectClass.getConstructor(ParticleType.class, EffectType.class).newInstance(this, effectType);
		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to create new Effect instance [" + this.toString() + "].", e, true);
		}
		return null;
	}

	public BlockBreak getBlockBreakInstance(EffectType effectType, int id, int meta) {
		BlockBreak effect = null;
		try {
			Object o = this.effectClass.getConstructor(ParticleType.class, EffectType.class, Integer.class, Integer.class).newInstance(this, effectType, id, meta);
		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to create new Effect instance [" + this.toString() + "].", e, true);
		}
		return null;
	}

	public Firework getFireworkInstance(EffectType effectType, FireworkEffect fe) {
		Firework effect = null;
		try {
			Object o = this.effectClass.getConstructor(ParticleType.class, EffectType.class, FireworkEffect.class).newInstance(this, effectType, fe);
		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to create new Effect instance [" + this.toString() + "].", e, true);
		}
		return null;
	}

	public Note getNoteInstance(EffectType effectType, Note.NoteType noteType) {
		BlockCrack effect = null;
		try {
			Object o = this.effectClass.getConstructor(ParticleType.class, EffectType.class, Note.NoteType.class).newInstance(this, effectType, noteType);
		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to create new Effect instance [" + this.toString() + "].", e, true);
		}
		return null;
	}

	public Potion getPotionInstance(EffectType effectType, Potion.PotionType potionType) {
		Potion effect = null;
		try {
			Object o = this.effectClass.getConstructor(ParticleType.class, EffectType.class, Potion.PotionType.class).newInstance(this, effectType, potionType);
		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to create new Effect instance [" + this.toString() + "].", e, true);
		}
		return null;
	}

	public Smoke getSmokeInstance(EffectType effectType, FireworkEffect fe, Smoke.SmokeType smokeType) {
		Smoke effect = null;
		try {
			Object o = this.effectClass.getConstructor(ParticleType.class, EffectType.class, Smoke.SmokeType.class).newInstance(this, effectType, smokeType);
		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to create new Effect instance [" + this.toString() + "].", e, true);
		}
		return null;
	}

	public Swirl getSwirlInstance(EffectType effectType, FireworkEffect fe, Swirl.SwirlType swirlType, UUID uuid) {
		Swirl effect = null;
		try {
			Object o = this.effectClass.getConstructor(ParticleType.class, EffectType.class, Swirl.SwirlType.class, UUID.class).newInstance(this, effectType, swirlType, uuid);
		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to create new Effect instance [" + this.toString() + "].", e, true);
		}
		return null;
	}
}