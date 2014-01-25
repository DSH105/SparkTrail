package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.PacketEffect;
import com.dsh105.sparktrail.trail.ParticleType;


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