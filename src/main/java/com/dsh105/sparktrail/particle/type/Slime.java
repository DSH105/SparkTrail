package com.dsh105.sparktrail.particle.type;

import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.PacketEffect;
import com.dsh105.sparktrail.particle.ParticleType;


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