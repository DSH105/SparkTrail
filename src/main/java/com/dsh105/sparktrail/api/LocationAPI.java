package com.dsh105.sparktrail.api;

import com.dsh105.sparktrail.data.EffectManager;
import com.dsh105.sparktrail.particle.Effect;
import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.ParticleType;
import org.bukkit.Location;

import java.util.HashSet;


public class LocationAPI {

    public void addEffect(ParticleType particleType, Location location) {

    }

    public void removeEffect(ParticleType particleType, Location location) {

    }

    public HashSet<ParticleType> getEffects(Location location) {
        HashSet<ParticleType> list = new HashSet<ParticleType>();
        for (EffectHolder eh : EffectManager.getInstance().getEffectHolders()) {
            if (eh.getEffectType() == EffectHolder.EffectType.LOCATION && eh.isLocation(location)) {
                for (Effect e : eh.getEffects()) {
                    list.add(e.getParticleType());
                }
            }
        }
        return null;
    }

    public EffectHolder getEffectHolder(Location location) {
        for (EffectHolder eh : EffectManager.getInstance().getEffectHolders()) {
            if (eh.getEffectType() == EffectHolder.EffectType.LOCATION && eh.isLocation(location)) {
                return eh;
            }
        }
        return null;
    }
}