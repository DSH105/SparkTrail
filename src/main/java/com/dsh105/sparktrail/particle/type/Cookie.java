package com.dsh105.sparktrail.particle.type;

import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.PacketEffect;
import com.dsh105.sparktrail.particle.ParticleType;


public class Cookie extends PacketEffect {

    public Cookie(EffectHolder effectHolder, ParticleType particleType) {
        super(effectHolder, particleType);
    }

    @Override
    public String getNmsName() {
        return "angryVillager";
    }

    @Override
    public float getSpeed() {
        return 0F;
    }

    @Override
    public int getParticleAmount() {
        return 15;
    }
}