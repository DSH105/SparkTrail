package com.github.dsh105.sparktrail.particle.type;

import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.PacketEffect;
import com.github.dsh105.sparktrail.particle.ParticleType;

/**
 * Project by DSH105
 */

public class Smoke extends PacketEffect {

	public SmokeType smokeType;

	public Smoke(EffectHolder effectHolder, ParticleType particleType, SmokeType smokeType) {
		super(effectHolder, particleType);
		this.smokeType = smokeType;
	}

	@Override
	public String getNmsName() {
		return this.smokeType.getNmsName();
	}

	@Override
	public float getSpeed() {
		return this.smokeType.getSpeed();
	}

	@Override
	public int getParticleAmount() {
		return this.smokeType.getAmount();
	}

	public enum SmokeType {
		BLACK("largesmoke", 0.2F, 50),
		RED("reddust", 0F, 100),
		RAINBOW("reddust", 1F, 100);

		private String nmsName;
		private float speed;
		private int amount;

		SmokeType(String nmsName, float speed, int amount) {
			this.nmsName = nmsName;
			this.speed = speed;
			this.amount = amount;
		}

		public String getNmsName() {
			return this.nmsName;
		}

		public float getSpeed() {
			return this.speed;
		}

		public int getAmount() {
			return this.amount;
		}
	}
}