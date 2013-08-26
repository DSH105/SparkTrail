package com.github.dsh105.sparktrail.data;

import com.github.dsh105.sparktrail.mysql.SQLEffectHandler;
import com.github.dsh105.sparktrail.particle.Effect;
import com.github.dsh105.sparktrail.particle.EffectHolder;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

/**
 * Project by DSH105
 */

public class EffectHandler {

	private static EffectHandler instance;
	private HashSet<EffectHolder> effects = new HashSet<EffectHolder>();

	public EffectHandler() {
		instance = this;
	}

	public static EffectHandler getInstance() {
		return instance;
	}

	public void addHolder(EffectHolder effectHolder) {
		this.effects.add(effectHolder);
	}

	public void clearEffects() {
		Iterator<EffectHolder> i = effects.iterator();
		while (i.hasNext()) {
			EffectHolder e = i.next();
			save(e);
			SQLEffectHandler.save(e);
			e.stop();
			i.remove();
		}
	}

	public void save(EffectHolder e) {

	}

	public EffectHolder getEffect(Location l) {
		return this.getEffect(l.getBlockX(), l.getBlockY(), l.getBlockZ());
	}

	public EffectHolder getEffect(Block b) {
		return this.getEffect(b.getX(), b.getY(), b.getZ());
	}

	public EffectHolder getEffect(int x, int y, int z) {
		for (EffectHolder e : effects) {
			if (e.locX == x && e.locY == y && e.locZ == z) {
				return e;
			}
		}
		return null;
	}

	public EffectHolder getEffect(String playerName) {
		for (EffectHolder e : effects) {
			if (e.getDetails().playerName.equals(playerName)) {
				return e;
			}
		}
		return null;
	}

	public EffectHolder getEffect(UUID mobUuid) {
		for (EffectHolder e : effects) {
			if (e.getDetails().mobUuid == mobUuid) {
				return e;
			}
		}
		return null;
	}

	public void remove(EffectHolder e) {
		save(e);
		SQLEffectHandler.save(e);
		e.stop();
		effects.remove(e);
	}
}