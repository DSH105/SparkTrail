package com.dsh105.sparktrail.particle.type;

import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.PacketEffect;
import com.dsh105.sparktrail.particle.ParticleType;


public class Portal extends PacketEffect {

    public Portal(EffectHolder effectHolder) {
        super(effectHolder, ParticleType.PORTAL);
    }

    @Override
    public String getNmsName() {
        return "portal";
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