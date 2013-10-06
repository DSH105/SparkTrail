package com.github.dsh105.sparktrail.particle.type;

import com.github.dsh105.sparktrail.particle.Effect;
import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.ParticleType;
import com.github.dsh105.sparktrail.util.EntityUtil;
import org.bukkit.Location;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Project by DSH105
 */

public class Orb extends Effect {

	public Orb(EffectHolder effectHolder, ParticleType particleType) {
		super(effectHolder, particleType);
	}

	@Override
	public boolean play() {
		boolean shouldPlay = super.play();
		if (shouldPlay) {
			for (Location l : this.displayType.getLocations(new Location(this.getWorld(), this.getX(), this.getY(), this.getZ()))) {
				for (int i = 0; i < 5; i++) {
					EntityUtil.spawnOrb(l, 10);
				}
			}
		}
		return shouldPlay;
	}

	public void playDemo(Player p) {
		for (int i = 0; i < 5; i++) {
			EntityUtil.spawnOrb(p.getLocation(), 10);

		}
	}
}