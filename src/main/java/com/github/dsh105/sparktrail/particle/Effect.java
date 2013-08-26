package com.github.dsh105.sparktrail.particle;

import com.github.dsh105.sparktrail.SparkTrail;
import com.github.dsh105.sparktrail.api.event.EffectPlayEvent;
import com.github.dsh105.sparktrail.config.options.ConfigOptions;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * Project by DSH105
 */

public abstract class Effect {

	private EffectHolder holder;

	protected DisplayType displayType;
	protected ParticleType particleType;
	protected BukkitTask task;

	public Effect(EffectHolder effectHolder, ParticleType particleType) {
		this.holder = effectHolder;
		this.particleType = particleType;

		this.displayType = ConfigOptions.instance.getEffectDisplay(this.particleType);
		if (this.displayType == null) {
			this.displayType = DisplayType.NORMAL;
		}
	}

	public EffectHolder.EffectType getEffectType() {
		return this.holder.effectType;
	}

	public int getX() {
		return holder.locX;
	}

	public int getY() {
		return holder.locY;
	}

	public int getZ() {
		return holder.locZ;
	}

	public World getWorld() {
		return holder.world;
	}

	public boolean play() {
		EffectPlayEvent effectPlayEvent = new EffectPlayEvent(this);
		SparkTrail.getInstance().getServer().getPluginManager().callEvent(effectPlayEvent);
		return !effectPlayEvent.isCancelled();
	}

	public void stop() {
		this.task.cancel();
	}

	public ParticleType getParticleType() {
		return particleType;
	}
}