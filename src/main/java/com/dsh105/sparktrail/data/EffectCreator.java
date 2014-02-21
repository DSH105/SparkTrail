package com.dsh105.sparktrail.data;

import com.dsh105.dshutils.logger.Logger;
import com.dsh105.sparktrail.chat.WaitingData;
import com.dsh105.sparktrail.trail.Effect;
import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.EffectHolder.EffectType;
import com.dsh105.sparktrail.trail.ParticleType;
import com.dsh105.sparktrail.trail.type.*;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;

import java.util.UUID;


public class EffectCreator {

    private static EffectHolder createHolder(EffectHolder effectHolder) {
        EffectManager.getInstance().addHolder(effectHolder);
        effectHolder.start();
        return effectHolder;
    }

    public static EffectHolder createLocHolder(Location location) {
        EffectHolder effectHolder = new EffectHolder(EffectType.LOCATION);
        effectHolder.updateLocation(location);
        return createHolder(effectHolder);
    }

    public static EffectHolder createPlayerHolder(String playerName) {
        EffectHolder effectHolder = new EffectHolder(EffectType.PLAYER);
        effectHolder.getDetails().playerName = playerName;
        return createHolder(effectHolder);
    }

    public static EffectHolder createMobHolder(UUID uuid) {
        EffectHolder effectHolder = new EffectHolder(EffectType.MOB);
        effectHolder.getDetails().mobUuid = uuid;
        return createHolder(effectHolder);
    }

    public static EffectHolder prepareNew(WaitingData data) {
        EffectHolder eh = null;
        if (data.effectType == EffectHolder.EffectType.LOCATION) {
            eh = EffectManager.getInstance().getEffect(data.location);
        } else if (data.effectType == EffectHolder.EffectType.PLAYER) {
            eh = EffectManager.getInstance().getEffect(data.playerName);
        } else if (data.effectType == EffectHolder.EffectType.MOB) {
            eh = EffectManager.getInstance().getEffect(data.mobUuid);
        }

        if (eh == null) {
            if (data.effectType == EffectHolder.EffectType.PLAYER) {
                eh = EffectCreator.createPlayerHolder(data.playerName);
            } else if (data.effectType == EffectHolder.EffectType.LOCATION) {
                Location l = data.location;
                eh = EffectCreator.createLocHolder(l);
            } else if (data.effectType == EffectHolder.EffectType.MOB) {
                eh = EffectCreator.createMobHolder(data.mobUuid);
            }
        }
        return eh;
    }

    public static Effect createEffect(EffectHolder effectHolder, ParticleType particleType, Object[] o) {
        Effect e = null;

        if (particleType == ParticleType.BLOCKBREAK) {
            if (!checkArray(o, new Class[]{Integer.class, Integer.class})) {
                Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Trail effect (" + particleType.toString() + ").", true);
                return null;
            }
            e = new BlockBreak(effectHolder, (Integer) o[0], (Integer) o[1]);
        } else if (particleType == ParticleType.CRITICAL) {
            if (!checkArray(o, new Class[]{Critical.CriticalType.class})) {
                Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Trail effect (" + particleType.toString() + ").", true);
                return null;
            }
            e = new Critical(effectHolder, (Critical.CriticalType) o[0]);
        } else if (particleType == ParticleType.FIREWORK) {
            if (!checkArray(o, new Class[]{FireworkEffect.class})) {
                Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Trail effect (" + particleType.toString() + ").", true);
                return null;
            }
            e = new Firework(effectHolder, (FireworkEffect) o[0]);
        } else if (particleType == ParticleType.ITEMSPRAY) {
            if (!checkArray(o, new Class[]{Integer.class, Integer.class})) {
                Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Trail effect (" + particleType.toString() + ").", true);
                return null;
            }
            e = new ItemSpray(effectHolder, (Integer) o[0], (Integer) o[1]);
        } else if (particleType == ParticleType.POTION) {
            if (!checkArray(o, new Class[]{Potion.PotionType.class})) {
                Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Trail effect (" + particleType.toString() + ").", true);
                return null;
            }
            e = new Potion(effectHolder, (Potion.PotionType) o[0]);
        } else if (particleType == ParticleType.SMOKE) {
            if (!checkArray(o, new Class[]{Smoke.SmokeType.class})) {
                Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Trail effect (" + particleType.toString() + ").", true);
                return null;
            }
            e = new Smoke(effectHolder, (Smoke.SmokeType) o[0]);
        } else if (particleType == ParticleType.SWIRL) {
            if (!checkArray(o, new Class[]{Swirl.SwirlType.class, UUID.class})) {
                Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Trail effect (" + particleType.toString() + ").", true);
                return null;
            }
            e = new Swirl(effectHolder, (Swirl.SwirlType) o[0], (UUID) o[1]);
        } else if (particleType == ParticleType.SOUND) {
            if (!checkArray(o, new Class[]{org.bukkit.Sound.class})) {
                Logger.log(Logger.LogLevel.WARNING, "Encountered Class Cast error initiating Trail effect (" + particleType.toString() + ").", true);
                return null;
            }
            e = new Sound(effectHolder, (org.bukkit.Sound) o[0]);
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