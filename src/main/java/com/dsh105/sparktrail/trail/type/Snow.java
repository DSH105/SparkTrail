package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.PacketEffect;
import com.dsh105.sparktrail.trail.ParticleType;


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