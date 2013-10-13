package io.github.dsh105.sparktrail.particle.type;

import io.github.dsh105.sparktrail.particle.EffectHolder;
import io.github.dsh105.sparktrail.particle.PacketEffect;
import io.github.dsh105.sparktrail.particle.ParticleType;

/**
 * Project by DSH105
 */

public class Runes extends PacketEffect {

    public Runes(EffectHolder effectHolder, ParticleType particleType) {
        super(effectHolder, particleType);
    }

    @Override
    public String getNmsName() {
        return "enchantmenttable";
    }

    @Override
    public float getSpeed() {
        return 1F;
    }

    @Override
    public int getParticleAmount() {
        return 100;
    }
}