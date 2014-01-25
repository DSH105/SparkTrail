package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.PacketEffect;
import com.dsh105.sparktrail.trail.ParticleType;


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