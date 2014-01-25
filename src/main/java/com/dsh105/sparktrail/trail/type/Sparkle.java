package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.PacketEffect;
import com.dsh105.sparktrail.trail.ParticleType;


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