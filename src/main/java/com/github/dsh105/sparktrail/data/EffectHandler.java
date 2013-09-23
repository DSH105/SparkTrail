package com.github.dsh105.sparktrail.data;

import com.github.dsh105.sparktrail.SparkTrail;
import com.github.dsh105.sparktrail.config.YAMLConfig;
import com.github.dsh105.sparktrail.mysql.SQLEffectHandler;
import com.github.dsh105.sparktrail.particle.Effect;
import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.ParticleType;
import com.github.dsh105.sparktrail.particle.type.*;
import com.github.dsh105.sparktrail.util.GeometryUtil;
import com.github.dsh105.sparktrail.util.Serialise;
import org.bukkit.Location;
import org.bukkit.World;
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
			save("autosave", e);
			SQLEffectHandler.save(e);
			e.stop();
			i.remove();
		}
	}

	public HashSet<EffectHolder> getEffects() {
		return this.effects;
	}

	public void save(String type, EffectHolder e) {
		clear(type, e);
		YAMLConfig config = SparkTrail.getInstance().getConfig(SparkTrail.ConfigType.MAIN);
		String path = type + ".";
		if (e.getEffectType() == EffectHolder.EffectType.PLAYER) {
			path = path + "player." + e.getDetails().playerName + ".";
		}
		else if (e.getEffectType() == EffectHolder.EffectType.LOCATION) {
			path = path + "location." + Serialise.serialiseLocation(e.getLocation()) + ".";
		}
		else if (e.getEffectType() == EffectHolder.EffectType.MOB) {
			path = path + "mob." + e.getDetails().mobUuid + ".";
		}

		for (Effect effect : e.getEffects()) {
			if (effect.getParticleType().requiresDataMenu()) {
				ParticleType pt = effect.getParticleType();
				String value = null;
				if (pt == ParticleType.BLOCKBREAK) {
					value = ((BlockBreak) effect).idValue + "," + ((BlockBreak) effect).metaValue;
				}
				else if (pt == ParticleType.FIREWORK) {
					value = Serialise.serialiseFireworkEffect(((Firework) effect).fireworkEffect);
				}
				else if (pt == ParticleType.NOTE) {
					value = ((Note) effect).noteType.toString();
				}
				else if (pt == ParticleType.POTION) {
					value = ((Potion) effect).potionType.toString();
				}
				else if (pt == ParticleType.SMOKE) {
					value = ((Smoke) effect).smokeType.toString();
				}
				else if (pt == ParticleType.SWIRL) {
					value = ((Swirl) effect).swirlType.toString();
				}
				if (value != null) {
					config.set(path + effect.getParticleType().toString(), value);
				}
			}
			else {
				config.set(path + effect.getParticleType().toString(), "none");
			}
		}

		config.saveConfig();
	}

	public void clear(String type, EffectHolder e) {
		YAMLConfig config = SparkTrail.getInstance().getConfig(SparkTrail.ConfigType.MAIN);
		String path = type + ".";
		if (e.getEffectType() == EffectHolder.EffectType.PLAYER) {
			path = path + "player." + e.getDetails().playerName + ".";
		}
		else if (e.getEffectType() == EffectHolder.EffectType.LOCATION) {
			path = path + "location." + Serialise.serialiseLocation(e.getLocation()) + ".";
		}
		else if (e.getEffectType() == EffectHolder.EffectType.MOB) {
			path = path + "mob." + e.getDetails().mobUuid + ".";
		}
		config.set(path, null);
		config.saveConfig();
	}

	public EffectHolder getEffect(Location l) {
		return this.getEffect(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ());
	}
	
	public EffectHolder getEffect(World world, int x, int y, int z) {
		for (EffectHolder e : effects) {
			if (e.world.equals(world) && e.locX == x && e.locY == y && e.locZ == z) {
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
		save("autosave", e);
		SQLEffectHandler.save(e);
		e.stop();
		effects.remove(e);
	}
}