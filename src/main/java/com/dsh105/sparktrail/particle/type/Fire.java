package com.dsh105.sparktrail.particle.type;

import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.PacketEffect;
import com.dsh105.sparktrail.particle.ParticleType;


public class Fire extends PacketEffect {

    public Fire(EffectHolder effectHolder) {
        super(effectHolder, ParticleType.FIRE);
    }

    @Override
    public String getNmsName() {
        return "flame";
    }

    @Override
    public float getSpeed() {
        return 0.05F;
    }

    @Override
    public int getParticleAmount() {
        return 50;
    }
}