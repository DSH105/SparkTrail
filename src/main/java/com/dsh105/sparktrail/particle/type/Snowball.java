package com.dsh105.sparktrail.particle.type;

import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.PacketEffect;
import com.dsh105.sparktrail.particle.ParticleType;


public class Snowball extends PacketEffect {

    public Snowball(EffectHolder effectHolder) {
        super(effectHolder, ParticleType.SNOWBALL);
    }

    @Override
    public String getNmsName() {
        return "snowballpoof";
    }

    @Override
    public float getSpeed() {
        return 1F;
    }

    @Override
    public int getParticleAmount() {
        return 20;
    }
}