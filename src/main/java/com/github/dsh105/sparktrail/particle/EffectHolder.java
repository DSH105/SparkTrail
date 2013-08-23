package com.github.dsh105.sparktrail.particle;

import com.github.dsh105.sparktrail.particle.Effect;

import java.util.HashSet;

/**
 * Project by DSH105
 */

public class EffectHolder {

	private HashSet<Effect> effects = new HashSet<Effect>();

	public EffectHolder(HashSet<Effect> effects) {
		this.effects = effects;
	}

	public void begin() {
		for (Effect e : effects) {
			e.play();
		}
	}
}