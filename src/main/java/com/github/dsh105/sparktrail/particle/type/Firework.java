package com.github.dsh105.sparktrail.particle.type;

import com.github.dsh105.sparktrail.particle.Effect;
import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.ParticleType;
import com.github.dsh105.sparktrail.util.ReflectionUtil;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;

/**
 * Project by DSH105
 */

public class Firework extends Effect {

	public FireworkEffect fireworkEffect;

	public Firework(EffectHolder effectHolder, ParticleType particleType, FireworkEffect fireworkEffect) {
		super(effectHolder, particleType);
		this.fireworkEffect = fireworkEffect;
	}

	@Override
	public boolean play() {
		boolean shouldPlay = super.play();
		if (shouldPlay) {
			for (Location l : this.displayType.getLocations(new Location(this.getWorld(), this.getX(), this.getY(), this.getZ()))) {
				ReflectionUtil.spawnFirework(new Location(l.getWorld(), l.getX(), l.getY(), l.getZ()), this.fireworkEffect);
			}
		}
		return shouldPlay;
	}
}