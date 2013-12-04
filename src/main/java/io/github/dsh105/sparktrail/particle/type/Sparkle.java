package io.github.dsh105.sparktrail.particle.type;

import io.github.dsh105.sparktrail.particle.EffectHolder;
import io.github.dsh105.sparktrail.particle.PacketEffect;
import io.github.dsh105.sparktrail.particle.ParticleType;


public class Sparkle extends PacketEffect {

    public Sparkle(EffectHolder effectHolder, ParticleType particleType) {
        super(effectHolder, particleType);
    }

    @Override
    public String getNmsName() {
        return "happyVillager";
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