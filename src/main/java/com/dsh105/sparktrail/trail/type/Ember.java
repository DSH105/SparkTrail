package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.PacketEffect;
import com.dsh105.sparktrail.trail.ParticleType;


public class Ember extends PacketEffect {

    public Ember(EffectHolder effectHolder) {
        super(effectHolder, ParticleType.EMBER);
    }

    @Override
    public String getNmsName() {
        return "lava";
    }

    @Override
    public float getSpeed() {
        return 0F;
    }

    @Override
    public int getParticleAmount() {
        return 25;
    }
}