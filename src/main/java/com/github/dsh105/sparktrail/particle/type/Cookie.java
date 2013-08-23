package com.github.dsh105.sparktrail.particle.type;

import com.github.dsh105.sparktrail.particle.EffectType;
import com.github.dsh105.sparktrail.particle.PacketEffect;
import com.github.dsh105.sparktrail.particle.ParticleType;

/**
 * Project by DSH105
 */

public class Cookie extends PacketEffect {

	public Cookie(ParticleType particleType, EffectType effectType) {
		super(particleType, effectType);
	}

	@Override
	public String getNmsName() {
		return "angryVillager";
	}

	@Override
	public float getSpeed() {
		return 0F;
	}

	@Override
	public int getParticleAmount() {
		return 15;
	}
}