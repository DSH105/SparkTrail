package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.PacketEffect;
import com.dsh105.sparktrail.trail.ParticleType;


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