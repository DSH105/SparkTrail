package com.dsh105.sparktrail.particle.type;

import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.PacketEffect;
import com.dsh105.sparktrail.particle.ParticleType;


public class Snow extends PacketEffect {

    public Snow(EffectHolder effectHolder) {
        super(effectHolder, ParticleType.SNOW);
    }

    @Override
    public String getNmsName() {
        return "snowshovel";
    }

    @Override
    public float getSpeed() {
        return 0.02F;
    }

    @Override
    public int getParticleAmount() {
        return 50;
    }
}