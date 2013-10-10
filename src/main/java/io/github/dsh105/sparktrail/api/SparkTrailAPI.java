package com.github.dsh105.sparktrail.api;

import com.github.dsh105.sparktrail.data.EffectHandler;
import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.ParticleType;
import org.bukkit.entity.Player;

import java.util.HashSet;

/**
 * Project by DSH105
 */

public class SparkTrailAPI {

	private LocationAPI locationAPI;
	private MobAPI mobAPI;
	private PlayerAPI playerAPI;

	public SparkTrailAPI() {
		this.locationAPI = new LocationAPI();
		this.mobAPI = new MobAPI();
		this.playerAPI = new PlayerAPI();
	}

	public LocationAPI locationAPI() {
		return this.locationAPI;
	}

	public MobAPI mobAPI() {
		return this.mobAPI;
	}

	public PlayerAPI playerAPI() {
		return this.playerAPI;
	}

	public HashSet<EffectHolder> getAllActiveEffects() {
		return EffectHandler.getInstance().getEffectHolders();
	}
}