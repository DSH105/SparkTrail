package io.github.dsh105.sparktrail.particle.type;

import io.github.dsh105.sparktrail.particle.EffectHolder;
import io.github.dsh105.sparktrail.particle.PacketEffect;
import io.github.dsh105.sparktrail.particle.ParticleType;


public class LavaDrip extends PacketEffect {

    public LavaDrip(EffectHolder effectHolder, ParticleType particleType) {
        super(effectHolder, particleType);
    }

    @Override
    public String getNmsName() {
        return "dripLava";
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