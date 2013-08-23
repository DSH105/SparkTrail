package com.github.dsh105.sparktrail.particle.type;

import com.github.dsh105.sparktrail.particle.Effect;
import com.github.dsh105.sparktrail.particle.EffectType;
import com.github.dsh105.sparktrail.particle.ParticleType;
import com.github.dsh105.sparktrail.util.ReflectionUtil;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;

/**
 * Project by DSH105
 */

public class Firework extends Effect {

	private FireworkEffect fireworkEffect;

	public Firework(ParticleType particleType, EffectType effectType, FireworkEffect fireworkEffect) {
		super(particleType, effectType);
		this.fireworkEffect = fireworkEffect;
	}

	@Override
	public boolean play() {
		boolean shouldPlay = super.play();
		if (shouldPlay) {
			ReflectionUtil.spawnFirework(new Location(this.world, this.locX, this.locY, this.locZ), this.fireworkEffect);
		}
		return shouldPlay;
	}
}