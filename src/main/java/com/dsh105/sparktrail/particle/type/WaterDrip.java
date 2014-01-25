package com.dsh105.sparktrail.particle.type;

import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.PacketEffect;
import com.dsh105.sparktrail.particle.ParticleType;


public class WaterDrip extends PacketEffect {

    public WaterDrip(EffectHolder effectHolder) {
        super(effectHolder, ParticleType.WATERDRIP);
    }

    @Override
    public String getNmsName() {
        return "dripWater";
    }

    @Override
    public float getSpeed() {
        return 0F;
    }

    @Override
    public int getParticleAmount() {
        return 50;
    }
}