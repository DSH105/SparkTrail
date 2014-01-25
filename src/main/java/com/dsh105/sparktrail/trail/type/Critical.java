package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.PacketEffect;
import com.dsh105.sparktrail.trail.ParticleType;


public class Critical extends PacketEffect {

    public CriticalType criticalType;

    public Critical(EffectHolder effectHolder, CriticalType criticalType) {
        super(effectHolder, ParticleType.CRITICAL);
        this.criticalType = criticalType;
    }

    @Override
    public String getNmsName() {
        return this.criticalType.getNmsName();
    }

    @Override
    public float getSpeed() {
        return 0.1F;
    }

    @Override
    public int getParticleAmount() {
        return 50;
    }

    public enum CriticalType {
        NORMAL("crit"),
        MAGIC("magicCrit");

        private String nmsName;

        CriticalType(String nmsName) {
            this.nmsName = nmsName;
        }

        public String getNmsName() {
            return nmsName;
        }
    }
}