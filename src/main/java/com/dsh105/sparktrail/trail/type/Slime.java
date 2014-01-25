package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.PacketEffect;
import com.dsh105.sparktrail.trail.ParticleType;


public class Slime extends PacketEffect {

    public Slime(EffectHolder effectHolder) {
        super(effectHolder, ParticleType.SLIME);
    }

    @Override
    public String getNmsName() {
        return "slime";
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