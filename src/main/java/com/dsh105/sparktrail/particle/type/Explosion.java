package com.dsh105.sparktrail.particle.type;

import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.PacketEffect;
import com.dsh105.sparktrail.particle.ParticleType;


public class Explosion extends PacketEffect {

    public Explosion(EffectHolder effectHolder) {
        super(effectHolder, ParticleType.EXPLOSION);
    }

    @Override
    public String getNmsName() {
        return "hugeexplosion";
    }

    @Override
    public float getSpeed() {
        return 1F;
    }

    @Override
    public int getParticleAmount() {
        return 1;
    }
}