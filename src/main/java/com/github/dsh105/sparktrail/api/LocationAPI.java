package com.github.dsh105.sparktrail.api;

import com.github.dsh105.sparktrail.data.EffectHandler;
import com.github.dsh105.sparktrail.particle.Effect;
import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.ParticleType;
import org.bukkit.Location;

import java.util.HashSet;

/**
 * Project by DSH105
 */

public class LocationAPI {

	public void addEffect(ParticleType particleType, Location location) {

	}

	public void removeEffect(ParticleType particleType, Location location) {

	}

	public HashSet<ParticleType> getEffects(Location location) {
		HashSet<ParticleType> list = new HashSet<ParticleType>();
		for (EffectHolder eh : EffectHandler.getInstance().getEffectHolders()) {
			if (eh.getEffectType() == EffectHolder.EffectType.LOCATION && eh.isLocation(location)) {
				for (Effect e : eh.getEffects()) {
					list.add(e.getParticleType());
				}
			}
		}
		return null;
	}

	public EffectHolder getEffectHolder(Location location) {
		for (EffectHolder eh : EffectHandler.getInstance().getEffectHolders()) {
			if (eh.getEffectType() == EffectHolder.EffectType.LOCATION && eh.isLocation(location)) {
				return eh;
			}
		}
		return null;
	}
}