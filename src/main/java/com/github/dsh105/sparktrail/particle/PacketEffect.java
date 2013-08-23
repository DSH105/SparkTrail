package com.github.dsh105.sparktrail.particle;

import com.github.dsh105.sparktrail.logger.Logger;
import com.github.dsh105.sparktrail.util.ReflectionUtil;
import org.bukkit.Location;

/**
 * Project by DSH105
 */

public abstract class PacketEffect extends Effect {

	protected Object packet;

	public PacketEffect(ParticleType particleType, EffectType effectType) {
		super(particleType, effectType);
		this.createPacket();
	}

	public void createPacket() {
		try {
			packet = Class.forName("net.minecraft.server" + ReflectionUtil.getVersionString() + ".Packet63WorldParticles").getConstructor().newInstance();
			ReflectionUtil.setValue(packet, "a", this.getNmsName());
			ReflectionUtil.setValue(packet, "b", (float) this.locX);
			ReflectionUtil.setValue(packet, "c", (float) this.locY);
			ReflectionUtil.setValue(packet, "d", (float) this.locZ);
			ReflectionUtil.setValue(packet, "e", 0.5f);
			ReflectionUtil.setValue(packet, "f", 1f);
			ReflectionUtil.setValue(packet, "g", 0.5f);
			ReflectionUtil.setValue(packet, "h", this.getSpeed());
			ReflectionUtil.setValue(packet, "i", this.getParticleAmount());
		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to create Packet Object (Packet63WorldParticles).", e, true);
		}
	}

	public abstract String getNmsName();
	public abstract float getSpeed();
	public abstract int getParticleAmount();

	@Override
	public boolean play() {
		boolean shouldPlay = super.play();
		if (shouldPlay) {
			try {
				ReflectionUtil.sendPacket(new Location(this.world, this.locX, this.locY, this.locZ), this.packet);
			} catch (Exception e) {
				Logger.log(Logger.LogLevel.SEVERE, "Failed to send Packet Object (Packet63WorldParticles) to players within a radius of 20 (" + this.locX + "," + this.locY + "," + this.locZ + ")", e, true);
			}
		}
		return shouldPlay;
	}
}