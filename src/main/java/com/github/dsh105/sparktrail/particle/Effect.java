package com.github.dsh105.sparktrail.particle;

import com.github.dsh105.sparktrail.SparkTrail;
import com.github.dsh105.sparktrail.api.event.EffectPlayEvent;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * Project by DSH105
 */

public abstract class Effect {

	protected ParticleType particleType;
	private EffectType effectType;
	protected BukkitTask task;

	public World world;
	public int locX;
	public int locY;
	public int locZ;

	public Effect(ParticleType particleType, EffectType effectType) {
		this.particleType = particleType;
		this.effectType = effectType;
	}

	public void updateLocation(int x, int y, int z) {
		this.locX = x;
		this.locY = y;
		this.locZ = z;
	}

	public EffectType getEffectType() {
		return this.effectType;
	}

	public boolean play() {
		EffectPlayEvent effectPlayEvent = new EffectPlayEvent(this);
		SparkTrail.getInstance().getServer().getPluginManager().callEvent(effectPlayEvent);
		return !effectPlayEvent.isCancelled();
	}

	public void stop() {
		this.task.cancel();
	}
}