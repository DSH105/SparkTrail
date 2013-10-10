package com.github.dsh105.sparktrail.mysql;

import com.github.dsh105.sparktrail.particle.EffectHolder;

/**
 * Project by DSH105
 */

public class SQLEffectHandler {

	public static SQLEffectHandler instance;

	public SQLEffectHandler() {
		instance = this;
	}

	public void save(EffectHolder eh) {
		if (eh.getEffectType().equals(EffectHolder.EffectType.PLAYER)) {

		}

		else if (eh.getEffectType().equals(EffectHolder.EffectType.LOCATION)) {

		}

		else if (eh.getEffectType().equals(EffectHolder.EffectType.MOB)) {

		}
	}

	public void clear(EffectHolder eh) {
		if (eh.getEffectType().equals(EffectHolder.EffectType.PLAYER)) {

		}

		else if (eh.getEffectType().equals(EffectHolder.EffectType.LOCATION)) {

		}

		else if (eh.getEffectType().equals(EffectHolder.EffectType.MOB)) {

		}
	}

	public void update(EffectHolder eh) {
		if (eh.getEffectType().equals(EffectHolder.EffectType.PLAYER)) {

		}

		else if (eh.getEffectType().equals(EffectHolder.EffectType.LOCATION)) {

		}

		else if (eh.getEffectType().equals(EffectHolder.EffectType.MOB)) {

		}
	}

	public void create(EffectHolder eh) {
		if (eh.getEffectType().equals(EffectHolder.EffectType.PLAYER)) {

		}

		else if (eh.getEffectType().equals(EffectHolder.EffectType.LOCATION)) {

		}

		else if (eh.getEffectType().equals(EffectHolder.EffectType.MOB)) {

		}
	}
}