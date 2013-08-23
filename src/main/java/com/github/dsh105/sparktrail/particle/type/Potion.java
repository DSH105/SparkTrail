package com.github.dsh105.sparktrail.particle.type;

import com.github.dsh105.sparktrail.particle.Effect;
import com.github.dsh105.sparktrail.particle.EffectType;
import com.github.dsh105.sparktrail.particle.ParticleType;
import org.bukkit.Location;

/**
 * Project by DSH105
 */

public class Potion extends Effect {

	private PotionType potionType;

	public Potion(ParticleType particleType, EffectType effectType, PotionType potionType) {
		super(particleType, effectType);
		this.potionType = potionType;
	}

	@Override
	public boolean play() {
		boolean shouldPlay = super.play();
		if (shouldPlay) {
			this.world.playEffect(new Location(this.world, this.locX, this.locY, this.locZ), org.bukkit.Effect.POTION_BREAK, this.potionType.getValue());
		}
		return shouldPlay;
	}

	public enum PotionType {
		AQUA(2),
		BLACK(8),
		BLUE(0),
		CRIMSON(5),
		DARKBLUE(6),
		DARKGREEN(4),
		DARKRED(12),
		GOLD(3),
		GRAY(10),
		GREEN(20),
		PINK(1),
		RED(9);

		private int value;
		PotionType(int value) {
			this.value = value;
		}
		public int getValue() {
			return this.value;
		}
	}
}