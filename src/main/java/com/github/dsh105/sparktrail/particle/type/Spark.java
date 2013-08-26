package com.github.dsh105.sparktrail.particle.type;

import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.PacketEffect;
import com.github.dsh105.sparktrail.particle.ParticleType;

/**
 * Project by DSH105
 */

public class Spark extends PacketEffect {

	public Spark(EffectHolder effectHolder, ParticleType particleType) {
		super(effectHolder, particleType);
	}

	@Override
	public String getNmsName() {
		return "lava";
	}

	@Override
	public float getSpeed() {
		return 0F;
	}

	@Override
	public int getParticleAmount() {
		return 25;
	}
}