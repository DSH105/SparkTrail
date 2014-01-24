package com.dsh105.sparktrail.particle.type;

import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.PacketEffect;
import com.dsh105.sparktrail.particle.ParticleType;


public class Magic extends PacketEffect {

    public Magic(EffectHolder effectHolder, ParticleType particleType) {
        super(effectHolder, particleType);
    }

    @Override
    public String getNmsName() {
        return "witchMagic";
    }

    @Override
    public float getSpeed() {
        return 1F;
    }

    @Override
    public int getParticleAmount() {
        return 50;
    }
}