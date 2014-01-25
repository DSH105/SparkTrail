package com.dsh105.sparktrail.particle.type;

import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.PacketEffect;
import com.dsh105.sparktrail.particle.ParticleType;


public class RainbowSwirl extends PacketEffect {

    public RainbowSwirl(EffectHolder effectHolder) {
        super(effectHolder, ParticleType.RAINBOWSWIRL);
    }

    @Override
    public String getNmsName() {
        return "mobSpell";
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