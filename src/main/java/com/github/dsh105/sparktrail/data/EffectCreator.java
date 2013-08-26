package com.github.dsh105.sparktrail.data;

import com.github.dsh105.sparktrail.logger.Logger;
import com.github.dsh105.sparktrail.particle.Effect;
import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.EffectHolder.EffectType;
import com.github.dsh105.sparktrail.particle.ParticleDetails;
import com.github.dsh105.sparktrail.particle.ParticleType;
import com.github.dsh105.sparktrail.particle.type.Note;
import com.github.dsh105.sparktrail.particle.type.Potion;
import com.github.dsh105.sparktrail.particle.type.Smoke;
import com.github.dsh105.sparktrail.particle.type.Swirl;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.UUID;

/**
 * Project by DSH105
 */

public class EffectCreator {

	public static EffectHolder createHolder(EffectHolder effectHolder, HashSet<ParticleDetails> particles) {
		HashSet<Effect> effects = new HashSet<Effect>();
		for (ParticleDetails pd : particles) {
			effects.add(createEffect(effectHolder, pd.getParticleType(), pd.getDetails()));
		}
		effectHolder.setEffects(effects);
		EffectHandler.getInstance().addHolder(effectHolder);
		effectHolder.start();
		return effectHolder;
	}

	public static EffectHolder createLocHolder(HashSet<ParticleDetails> particles, EffectType effectType, Location location) {
		EffectHolder effectHolder = new EffectHolder(effectType);
		effectHolder.updateLocation(location);
		return createHolder(effectHolder, particles);
	}

	public static EffectHolder createPlayerHolder(HashSet<ParticleDetails> particles, EffectType effectType, Location location, String playerName, UUID uuid) {
		EffectHolder effectHolder = new EffectHolder(effectType);
		effectHolder.updateLocation(location);
		effectHolder.getDetails().playerName = playerName;
		effectHolder.getDetails().mobUuid = uuid;
		return createHolder(effectHolder, particles);
	}

	public static EffectHolder createMobHolder(HashSet<ParticleDetails> particles, EffectType effectType, Location location, UUID uuid) {
		EffectHolder effectHolder = new EffectHolder(effectType);
		effectHolder.updateLocation(location);
		effectHolder.getDetails().mobUuid = uuid;
		return createHolder(effectHolder, particles);
	}

	public static Effect createEffect(EffectHolder effectHolder, ParticleType particleType, Object[] o) {
		Effect e = null;

		if (particleType == ParticleType.BLOCKBREAK) {
			if (!checkArray(o, new Class[] {Integer.class, Integer.class})) {
				Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Particle effect (" + particleType.toString() + ").", true);
				return null;
			}
			e = particleType.getBlockBreakInstance(effectHolder, (Integer) o[0], (Integer) o[1]);
		}
		else if (particleType == ParticleType.FIREWORK) {
			if (!checkArray(o, new Class[] {FireworkEffect.class})) {
				Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Particle effect (" + particleType.toString() + ").", true);
				return null;
			}
			e = particleType.getFireworkInstance(effectHolder, (FireworkEffect) o[0]);
		}
		else if (particleType == ParticleType.NOTE) {
			if (!checkArray(o, new Class[] {Note.NoteType.class})) {
				Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Particle effect (" + particleType.toString() + ").", true);
				return null;
			}
			e = particleType.getNoteInstance(effectHolder, (Note.NoteType) o[0]);
		}
		else if (particleType == ParticleType.POTION) {
			if (!checkArray(o, new Class[] {Potion.PotionType.class})) {
				Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Particle effect (" + particleType.toString() + ").", true);
				return null;
			}
			e = particleType.getPotionInstance(effectHolder, (Potion.PotionType) o[0]);
		}
		else if (particleType == ParticleType.SMOKE) {
			if (!checkArray(o, new Class[] {Smoke.SmokeType.class})) {
				Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Particle effect (" + particleType.toString() + ").", true);
				return null;
			}
			e = particleType.getSmokeInstance(effectHolder, (Smoke.SmokeType) o[0]);
		}
		else if (particleType == ParticleType.SWIRL) {
			if (!checkArray(o, new Class[] {Swirl.SwirlType.class, UUID.class})) {
				Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Particle effect (" + particleType.toString() + ").", true);
				return null;
			}
			e = particleType.getSwirlInstance(effectHolder, (Swirl.SwirlType) o[0], (UUID) o[1]);
		}
		else {
			e = particleType.getEffectInstance(effectHolder);
		}

		return e;
	}

	private static boolean checkArray(Object[] o1, Class[] classes) {
		for (int i = 0; i < o1.length; i++) {
			if (!(o1[i].getClass().equals(classes[i]))) {
				return false;
			}
		}
		return true;
	}
}