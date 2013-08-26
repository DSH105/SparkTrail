package com.github.dsh105.sparktrail.particle.type;

import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.PacketEffect;
import com.github.dsh105.sparktrail.particle.ParticleType;

/**
 * Project by DSH105
 */

public class LavaDrip extends PacketEffect {

	public LavaDrip(EffectHolder effectHolder, ParticleType particleType) {
		super(effectHolder, particleType);
	}

	@Override
	public String getNmsName() {
		return "dripLava";
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