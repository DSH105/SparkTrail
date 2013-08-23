package com.github.dsh105.sparktrail.particle.type;

import com.github.dsh105.sparktrail.particle.EffectType;
import com.github.dsh105.sparktrail.particle.PacketEffect;
import com.github.dsh105.sparktrail.particle.ParticleType;

/**
 * Project by DSH105
 */

public class WaterDrip extends PacketEffect {

	public WaterDrip(ParticleType particleType, EffectType effectType) {
		super(particleType, effectType);
	}

	@Override
	public String getNmsName() {
		return "dripWater";
	}

	@Override
	public float getSpeed() {
		return 0F;
	}

	@Override
	public int getParticleAmount() {
		return 50;
	}
}