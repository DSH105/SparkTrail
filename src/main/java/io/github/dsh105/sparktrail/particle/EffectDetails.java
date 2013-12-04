package io.github.dsh105.sparktrail.particle;

import java.util.UUID;


public class EffectDetails {

    protected EffectHolder.EffectType effectType;
    public String playerName;
    public UUID mobUuid;

    public EffectDetails(EffectHolder.EffectType effectType) {
        this.effectType = effectType;
    }
}