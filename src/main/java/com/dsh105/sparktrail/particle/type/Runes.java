package com.dsh105.sparktrail.particle.type;

import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.PacketEffect;
import com.dsh105.sparktrail.particle.ParticleType;


public class Runes extends PacketEffect {

    public Runes(EffectHolder effectHolder) {
        super(effectHolder, ParticleType.RUNES);
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