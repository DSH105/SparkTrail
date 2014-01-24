package com.dsh105.sparktrail.particle.type;

import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.PacketEffect;
import com.dsh105.sparktrail.particle.ParticleType;

public class FootStep extends PacketEffect {

    public FootStep(EffectHolder effectHolder, ParticleType particleType) {
        super(effectHolder, particleType);
    }

    @Override
    public String getNmsName() {
        return "footstep";
    }

    @Override
    public float getSpeed() {
        return 0;
    }

    @Override
    public int getParticleAmount() {
        return 5;
    }
}