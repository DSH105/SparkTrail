package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.PacketEffect;
import com.dsh105.sparktrail.trail.ParticleType;


public class Splash extends PacketEffect {

    public Splash(EffectHolder effectHolder) {
        super(effectHolder, ParticleType.SPLASH);
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