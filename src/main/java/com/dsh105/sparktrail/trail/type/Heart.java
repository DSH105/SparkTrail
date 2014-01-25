package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.PacketEffect;
import com.dsh105.sparktrail.trail.ParticleType;


public class Heart extends PacketEffect {

    public Heart(EffectHolder effectHolder) {
        super(effectHolder, ParticleType.HEART);
    }

    @Override
    public String getNmsName() {
        return "heart";
    }

    @Override
    public float getSpeed() {
        return 0F;
    }

    @Override
    public int getParticleAmount() {
        return 20;
    }
}