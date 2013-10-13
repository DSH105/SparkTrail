package io.github.dsh105.sparktrail.particle.type;

import io.github.dsh105.sparktrail.particle.EffectHolder;
import io.github.dsh105.sparktrail.particle.PacketEffect;
import io.github.dsh105.sparktrail.particle.ParticleType;

/**
 * Project by DSH105
 */

public class Snow extends PacketEffect {

    public Snow(EffectHolder effectHolder, ParticleType particleType) {
        super(effectHolder, particleType);
    }

    @Override
    public String getNmsName() {
        return "snowshovel";
    }

    @Override
    public float getSpeed() {
        return 0.02F;
    }

    @Override
    public int getParticleAmount() {
        return 50;
    }
}