package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.PacketEffect;
import com.dsh105.sparktrail.trail.ParticleType;

public class FootStep extends PacketEffect {

    public FootStep(EffectHolder effectHolder) {
        super(effectHolder, ParticleType.FOOTSTEP);
    }

    @Override
    public String getNmsName() {
        return "footstep";
    }

    @Override
    public float getSpeed() {
        return 0;
    }

    @Override
    public int getParticleAmount() {
        return 5;
    }
}