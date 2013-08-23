package com.github.dsh105.sparktrail.particle.type;

import com.github.dsh105.sparktrail.particle.EffectType;
import com.github.dsh105.sparktrail.particle.PacketEffect;
import com.github.dsh105.sparktrail.particle.ParticleType;

/**
 * Project by DSH105
 */

public class RainbowSwirl extends PacketEffect {

	public RainbowSwirl(ParticleType particleType, EffectType effectType) {
		super(particleType, effectType);
	}

	@Override
	public String getNmsName() {
		return "mobSpell";
	}

	@Override
	public float getSpeed() {
		return 1F;
	}

	@Override
	public int getParticleAmount() {
		return 20;
	}
}