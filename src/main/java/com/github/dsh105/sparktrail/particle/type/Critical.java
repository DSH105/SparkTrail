package com.github.dsh105.sparktrail.particle.type;

import com.github.dsh105.sparktrail.particle.EffectType;
import com.github.dsh105.sparktrail.particle.PacketEffect;
import com.github.dsh105.sparktrail.particle.ParticleType;

/**
 * Project by DSH105
 */

public class Critical extends PacketEffect {

	private CriticalType criticalType;

	public Critical(ParticleType particleType, EffectType effectType, CriticalType criticalType) {
		super(particleType, effectType);
		this.criticalType = criticalType;
	}

	@Override
	public String getNmsName() {
		return this.criticalType == CriticalType.MAGIC ? "magicCrit" : "crit";
	}

	@Override
	public float getSpeed() {
		return 0.1F;
	}

	@Override
	public int getParticleAmount() {
		return 50;
	}

	public enum CriticalType {
		NORMAL, MAGIC;
	}
}