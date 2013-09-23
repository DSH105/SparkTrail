package com.github.dsh105.sparktrail.particle.type;

import com.github.dsh105.sparktrail.logger.Logger;
import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.PacketEffect;
import com.github.dsh105.sparktrail.particle.ParticleType;
import com.github.dsh105.sparktrail.util.ReflectionUtil;

/**
 * Project by DSH105
 */

public class BlockBreak extends PacketEffect {

	public int idValue;
	public int metaValue;

	public BlockBreak(EffectHolder effectHolder, ParticleType particleType, int idValue, int metaValue) {
		super(effectHolder, particleType);
		this.idValue = idValue;
		this.metaValue = metaValue;
		this.createPacket();
	}

	@Override
	public void createPacket() {
		try {
			packet = Class.forName("net.minecraft.server" + ReflectionUtil.getVersionString() + ".Packet63WorldParticles").getConstructor().newInstance();
			ReflectionUtil.setValue(packet, "a", this.getNmsName() + "_" + idValue + "_" + metaValue);
			ReflectionUtil.setValue(packet, "b", (float) this.getX());
			ReflectionUtil.setValue(packet, "c", (float) this.getY());
			ReflectionUtil.setValue(packet, "d", (float) this.getZ());
			ReflectionUtil.setValue(packet, "e", 0.5f);
			ReflectionUtil.setValue(packet, "f", 1f);
			ReflectionUtil.setValue(packet, "g", 0.5f);
			ReflectionUtil.setValue(packet, "h", this.getSpeed());
			ReflectionUtil.setValue(packet, "i", this.getParticleAmount());
		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to create Packet Object (Packet63WorldParticles).", e, true);
		}
	}

	@Override
	public String getNmsName() {
		return "tilecrack";
	}

	@Override
	public float getSpeed() {
		return 0F;
	}

	@Override
	public int getParticleAmount() {
		return 100;
	}
}