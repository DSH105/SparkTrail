package com.github.dsh105.sparktrail.particle;

import com.github.dsh105.sparktrail.logger.Logger;
import com.github.dsh105.sparktrail.particle.type.Note;
import com.github.dsh105.sparktrail.particle.type.Potion;
import com.github.dsh105.sparktrail.particle.type.Smoke;
import com.github.dsh105.sparktrail.particle.type.Swirl;
import org.apache.commons.lang.Validate;
import org.bukkit.FireworkEffect;

import java.util.UUID;

/**
 * Project by DSH105
 */

public class ParticleDetails {

	private ParticleType particleType;
	private EffectHolder.EffectType type;
	private UUID uuid;
	private String playerName;

	public int blockId;
	public int blockMeta;
	public FireworkEffect fireworkEffect;
	public Note.NoteType noteType;
	public Potion.PotionType potionType;
	public Smoke.SmokeType smokeType;
	public Swirl.SwirlType swirlType;

	public ParticleDetails(ParticleType particleType) {
		this.particleType = particleType;
		this.type = EffectHolder.EffectType.LOCATION;
	}

	public void setMob(UUID uuid) {
		this.uuid = uuid;
		this.type = EffectHolder.EffectType.MOB;
	}

	public void setPlayer(String name, UUID uuid) {
		this.playerName = name;
		this.uuid = uuid;
		this.type = EffectHolder.EffectType.PLAYER;
	}

	public ParticleType getParticleType() {
		return this.particleType;
	}

	public Object[] getDetails() {
		Object[] o = new Object[] {};
		if (particleType == ParticleType.BLOCKBREAK) {
			return new Object[] {this.blockId, this.blockMeta};
		}
		else if (particleType == ParticleType.FIREWORK) {
			return new Object[] {this.fireworkEffect};
		}
		else if (particleType == ParticleType.NOTE) {
			return new Object[] {this.noteType};
		}
		else if (particleType == ParticleType.POTION) {
			return new Object[] {this.potionType};
		}
		else if (particleType == ParticleType.SMOKE) {
			return new Object[] {this.smokeType};
		}
		else if (particleType == ParticleType.SWIRL) {
			return new Object[] {this.swirlType, this.uuid};
		}
		for (Object object : o) {
			if (o == null) {
				Logger.log(Logger.LogLevel.WARNING, "Encountered error while initiating Particle Effect (" + this.particleType.toString() + ").", false);
				return new Object[] {};
			}
		}
		return o;
	}
}