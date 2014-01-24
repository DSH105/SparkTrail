package com.dsh105.sparktrail.particle.type;

import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.PacketEffect;
import com.dsh105.sparktrail.particle.ParticleType;


public class Splash extends PacketEffect {

    public Splash(EffectHolder effectHolder, ParticleType particleType) {
        super(effectHolder, particleType);
    }

    @Override
    public String getNmsName() {
        return "splash";
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