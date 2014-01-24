package com.dsh105.sparktrail.particle.type;

import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.PacketEffect;
import com.dsh105.sparktrail.particle.ParticleType;


public class LavaDrip extends PacketEffect {

    public LavaDrip(EffectHolder effectHolder, ParticleType particleType) {
        super(effectHolder, particleType);
    }

    @Override
    public String getNmsName() {
        return "dripLava";
    }

    @Override
    public float getSpeed() {
        return 0F;
    }

    @Override
    public int getParticleAmount() {
        return 50;
    }
}