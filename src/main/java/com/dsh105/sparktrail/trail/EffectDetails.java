package com.dsh105.sparktrail.trail;

import java.util.UUID;


public class EffectDetails {

    protected EffectHolder.EffectType effectType;
    public String playerName;
    public UUID mobUuid;

    public EffectDetails(EffectHolder.EffectType effectType) {
        this.effectType = effectType;
    }
}