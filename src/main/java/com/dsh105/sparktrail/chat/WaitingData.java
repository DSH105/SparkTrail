package com.dsh105.sparktrail.chat;

import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.ParticleType;
import org.bukkit.Location;

import java.util.UUID;


public class WaitingData {

    public EffectHolder.EffectType effectType;
    public ParticleType particleType;
    public Location location;
    public String playerName;
    public UUID mobUuid;

    public WaitingData(EffectHolder.EffectType effectType, ParticleType particleType) {
        this.effectType = effectType;
        this.particleType = particleType;
    }
}