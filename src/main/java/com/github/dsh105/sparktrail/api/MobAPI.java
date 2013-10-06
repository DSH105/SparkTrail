package com.github.dsh105.sparktrail.api;

import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.ParticleType;
import org.bukkit.entity.Entity;

import java.util.HashSet;
import java.util.UUID;

/**
 * Project by DSH105
 */

public class MobAPI {

	public void addEffect(ParticleType particleType, UUID uuid) {

	}

	public void removeEffect(ParticleType particleType, UUID uuid) {

	}

	public HashSet<ParticleType> getEffects(UUID uuid) {
		return null;
	}

	public EffectHolder getEffectHolder(UUID uuid) {
		return null;
	}

	public EffectHolder getEffectHolder(Entity entity) {
		return null;
	}
}