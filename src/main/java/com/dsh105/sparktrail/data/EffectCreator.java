package com.dsh105.sparktrail.data;

import com.dsh105.sparktrail.particle.Effect;
import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.EffectHolder.EffectType;
import com.dsh105.sparktrail.particle.ParticleDetails;
import com.dsh105.sparktrail.particle.ParticleType;
import com.dsh105.sparktrail.particle.type.*;
import io.github.dsh105.dshutils.logger.Logger;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.UUID;


public class EffectCreator {

    private static EffectHolder createHolder(EffectHolder effectHolder, HashSet<ParticleDetails> particles) {
        HashSet<Effect> effects = new HashSet<Effect>();
        for (ParticleDetails pd : particles) {
            Effect effect = createEffect(effectHolder, pd.getParticleType(), pd.getDetails());
            if (effect != null) {
                effects.add(effect);
            }
        }
        effectHolder.setEffects(effects);
        EffectManager.getInstance().addHolder(effectHolder);
        effectHolder.start();
        return effectHolder;
    }

    public static EffectHolder createLocHolder(HashSet<ParticleDetails> particles, EffectType effectType, Location location) {
        EffectHolder effectHolder = new EffectHolder(effectType);
        effectHolder.updateLocation(location);
        return createHolder(effectHolder, particles);
    }

    public static EffectHolder createPlayerHolder(HashSet<ParticleDetails> particles, EffectType effectType, String playerName) {
        EffectHolder effectHolder = new EffectHolder(effectType);
        effectHolder.getDetails().playerName = playerName;
        return createHolder(effectHolder, particles);
    }

    public static EffectHolder createMobHolder(HashSet<ParticleDetails> particles, EffectType effectType, UUID uuid) {
        EffectHolder effectHolder = new EffectHolder(effectType);
        effectHolder.getDetails().mobUuid = uuid;
        return createHolder(effectHolder, particles);
    }

    public static Effect createEffect(EffectHolder effectHolder, ParticleType particleType, Object[] o) {
        Effect e = null;

        if (particleType == ParticleType.BLOCKBREAK) {
            if (!checkArray(o, new Class[]{Integer.class, Integer.class})) {
                Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Particle effect (" + particleType.toString() + ").", true);
                return null;
            }
            e = new BlockBreak(effectHolder, (Integer) o[0], (Integer) o[1]);
        } else if (particleType == ParticleType.CRITICAL) {
            if (!checkArray(o, new Class[]{Critical.CriticalType.class})) {
                Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Particle effect (" + particleType.toString() + ").", true);
                return null;
            }
            e = new Critical(effectHolder, (Critical.CriticalType) o[0]);
        } else if (particleType == ParticleType.FIREWORK) {
            if (!checkArray(o, new Class[]{FireworkEffect.class})) {
                Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Particle effect (" + particleType.toString() + ").", true);
                return null;
            }
            e = new Firework(effectHolder, (FireworkEffect) o[0]);
        } else if (particleType == ParticleType.ITEMSPRAY) {
            if (!checkArray(o, new Class[]{Integer.class, Integer.class})) {
                Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Particle effect (" + particleType.toString() + ").", true);
                return null;
            }
            e = new ItemSpray(effectHolder, (Integer) o[0], (Integer) o[1]);
        }
        /*else if (particleType == ParticleType.NOTE) {
            if (!checkArray(o, new Class[] {Note.NoteType.class})) {
				Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Particle effect (" + particleType.toString() + ").", true);
				return null;
			}
			e = particleType.getNoteInstance(effectHolder, (Note.NoteType) o[0]);
		}*/
        else if (particleType == ParticleType.POTION) {
            if (!checkArray(o, new Class[]{Potion.PotionType.class})) {
                Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Particle effect (" + particleType.toString() + ").", true);
                return null;
            }
            e = new Potion(effectHolder, (Potion.PotionType) o[0]);
        } else if (particleType == ParticleType.SMOKE) {
            if (!checkArray(o, new Class[]{Smoke.SmokeType.class})) {
                Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Particle effect (" + particleType.toString() + ").", true);
                return null;
            }
            e = new Smoke(effectHolder, (Smoke.SmokeType) o[0]);
        } else if (particleType == ParticleType.SWIRL) {
            if (!checkArray(o, new Class[]{Swirl.SwirlType.class, UUID.class})) {
                Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Particle effect (" + particleType.toString() + ").", true);
                return null;
            }
            e = new Swirl(effectHolder, (Swirl.SwirlType) o[0], (UUID) o[1]);
        } else {
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