package com.github.dsh105.sparktrail.particle.type;

import com.github.dsh105.sparktrail.particle.Effect;
import com.github.dsh105.sparktrail.particle.EffectType;
import com.github.dsh105.sparktrail.particle.ParticleType;
import org.bukkit.Location;

/**
 * Project by DSH105
 */

public class Ender extends Effect {

	public Ender(ParticleType particleType, EffectType effectType) {
		super(particleType, effectType);
	}

	@Override
	public boolean play() {
		boolean shouldPlay = super.play();
		if (shouldPlay) {
			this.world.playEffect(new Location(this.world, this.locX, this.locY, this.locZ), org.bukkit.Effect.ENDER_SIGNAL, 0);
		}
		return shouldPlay;
	}
}