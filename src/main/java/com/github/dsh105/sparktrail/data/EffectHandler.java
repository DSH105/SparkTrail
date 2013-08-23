package com.github.dsh105.sparktrail.data;

import com.github.dsh105.sparktrail.mysql.SQLEffectHandler;
import com.github.dsh105.sparktrail.particle.Effect;
import com.github.dsh105.sparktrail.particle.EffectType;
import com.github.dsh105.sparktrail.particle.ParticleType;
import com.github.dsh105.sparktrail.particle.type.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Project by DSH105
 */

public class EffectHandler {

	private static EffectHandler instance;
	private HashSet<Effect> effects = new HashSet<Effect>();

	public EffectHandler() {
		instance = this;
	}

	public static EffectHandler getInstance() {
		return instance;
	}

	public Effect create(Effect effect) {

	}

	public Effect createEffect(EffectType effectType, ParticleType particleType) {

	}

	public BlockBreak createBlockBreak(ParticleType particleType) {

	}

	public Firework createFirework(ParticleType particleType) {

	}

	public Note createNote(ParticleType particleType) {

	}

	public Potion createPotion(ParticleType particleType) {

	}

	public Smoke createSmoke(ParticleType particleType) {

	}

	public Swirl createSwirl(ParticleType particleType) {

	}

	public void clearEffects() {
		Iterator<Effect> i = effects.iterator();
		while (i.hasNext()) {
			Effect e = i.next();
			save(e);
			SQLEffectHandler.save(e);
			e.stop();
			i.remove();
		}
	}

	public void save(Effect e) {

	}

	public Effect getEffect(Player p) {
		return this.getEffect(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
	}

	public Effect getEffect(Entity e) {
		return this.getEffect(e.getLocation().getBlockX(), e.getLocation().getBlockY(), e.getLocation().getBlockZ());
	}

	public Effect getEffect(Block b) {
		return this.getEffect(b.getX(), b.getY(), b.getZ());
	}

	public Effect getEffect(int x, int y, int z) {
		for (Effect e : effects) {
			if (e.locX == x && e.locY == y && e.locZ == z) {
				return e;
			}
		}
		return null;
	}

	public void remove(Effect e) {
		save(e);
		SQLEffectHandler.save(e);
		e.stop();
		effects.remove(e);
	}
}