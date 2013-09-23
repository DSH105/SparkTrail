package com.github.dsh105.sparktrail.particle.type;

import com.github.dsh105.sparktrail.particle.Effect;
import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.ParticleType;
import org.bukkit.Location;

/**
 * Project by DSH105
 */

public class Potion extends Effect {

	public PotionType potionType;

	public Potion(EffectHolder effectHolder, ParticleType particleType, PotionType potionType) {
		super(effectHolder, particleType);
		this.potionType = potionType;
	}

	@Override
	public boolean play() {
		boolean shouldPlay = super.play();
		if (shouldPlay) {
			for (Location l : this.displayType.getLocations(new Location(this.getWorld(), this.getX(), this.getY(), this.getZ()))) {
				this.getWorld().playEffect(new Location(l.getWorld(), l.getX(), l.getY(), l.getZ()), org.bukkit.Effect.POTION_BREAK, this.potionType.getValue());
			}
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