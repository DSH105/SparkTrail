package com.github.dsh105.sparktrail.particle;

import com.github.dsh105.sparktrail.SparkTrail;
import com.github.dsh105.sparktrail.config.options.ConfigOptions;
import com.github.dsh105.sparktrail.particle.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;

/**
 * Project by DSH105
 */

public class EffectHolder extends BukkitRunnable {

	private HashSet<Effect> effects = new HashSet<Effect>();
	private BukkitTask task = null;
	protected EffectType effectType;
	protected EffectDetails details;

	public World world;
	public int locX;
	public int locY;
	public int locZ;

	public EffectHolder(EffectType effectType) {
		this.effectType = effectType;
		this.details = new EffectDetails(effectType);
	}

	public void setEffects(HashSet<Effect> effects) {
		this.effects = effects;
	}

	public void updateLocation(Location l) {
		this.locX = l.getBlockX();
		this.locY = l.getBlockY();
		this.locZ = l.getBlockZ();
	}

	public void updateLocation(int x, int y, int z) {
		this.locX = x;
		this.locY = y;
		this.locZ = z;
	}

	public EffectDetails getDetails() {
		return this.details;
	}

	public void start() {
		this.task = this.runTaskTimer(SparkTrail.getInstance(), 0L, ConfigOptions.instance.maxTick);
	}

	public void run() {
		for (Effect e : effects) {
			if (ConfigOptions.instance.maxTick % ConfigOptions.instance.getEffectFrequency(e.getParticleType()) == 0) {
				e.play();
			}
		}
	}

	public void stop() {
		this.task.cancel();
	}

	public enum EffectType {

		LOCATION, PLAYER, MOB;
	}
}