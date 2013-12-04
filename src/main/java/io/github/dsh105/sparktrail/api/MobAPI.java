package io.github.dsh105.sparktrail.api;

import io.github.dsh105.sparktrail.particle.EffectHolder;
import io.github.dsh105.sparktrail.particle.ParticleType;
import org.bukkit.entity.Entity;

import java.util.HashSet;
import java.util.UUID;


public class MobAPI {

    public void addEffect(ParticleType particleType, UUID uuid) {

    }

    public void removeEffect(ParticleType particleType, UUID uuid) {

    }

    public HashSet<ParticleType> getEffects(UUID uuid) {
        return null;
    }

    public EffectHolder getEffectHolder(UUID uuid) {
        return null;
    }

    public EffectHolder getEffectHolder(Entity entity) {
        return null;
    }
}