package com.github.dsh105.sparktrail.particle.type;

import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.PacketEffect;
import com.github.dsh105.sparktrail.particle.ParticleType;

/**
 * Project by DSH105
 */

public class Fire extends PacketEffect {

	public Fire(EffectHolder effectHolder, ParticleType particleType) {
		super(effectHolder, particleType);
	}

	@Override
	public String getNmsName() {
		return "flame";
	}

	@Override
	public float getSpeed() {
		return 0.05F;
	}

	@Override
	public int getParticleAmount() {
		return 50;
	}
}