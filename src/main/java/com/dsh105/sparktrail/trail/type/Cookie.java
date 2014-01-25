package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.PacketEffect;
import com.dsh105.sparktrail.trail.ParticleType;


public class Cookie extends PacketEffect {

    public Cookie(EffectHolder effectHolder) {
        super(effectHolder, ParticleType.COOKIE);
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