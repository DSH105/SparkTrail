package com.dsh105.sparktrail.particle.type;

import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.PacketEffect;
import com.dsh105.sparktrail.particle.ParticleType;


public class Sparkle extends PacketEffect {

    public Sparkle(EffectHolder effectHolder) {
        super(effectHolder, ParticleType.SPARKLE);
    }

    @Override
    public String getNmsName() {
        return "happyVillager";
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