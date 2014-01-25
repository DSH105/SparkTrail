package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.PacketEffect;
import com.dsh105.sparktrail.trail.ParticleType;


public class Void extends PacketEffect {

    public Void(EffectHolder effectHolder) {
        super(effectHolder, ParticleType.VOID);
    }

    @Override
    public String getNmsName() {
        return "townaura";
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