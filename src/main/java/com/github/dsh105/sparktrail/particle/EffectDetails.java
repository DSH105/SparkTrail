package com.github.dsh105.sparktrail.particle;

import java.util.UUID;

/**
 * Project by DSH105
 */

public class EffectDetails {

	protected EffectHolder.EffectType effectType;
	public String playerName;
	public UUID mobUuid;

	public EffectDetails(EffectHolder.EffectType effectType) {
		this.effectType = effectType;
	}
}