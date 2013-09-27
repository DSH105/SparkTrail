package com.github.dsh105.sparktrail.particle;

import com.github.dsh105.sparktrail.config.options.ConfigOptions;
import com.github.dsh105.sparktrail.logger.ConsoleLogger;
import com.github.dsh105.sparktrail.logger.Logger;
import com.github.dsh105.sparktrail.util.ReflectionUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Project by DSH105
 */

public abstract class PacketEffect extends Effect {

	public PacketEffect(EffectHolder effectHolder, ParticleType particleType) {
		super(effectHolder, particleType);
	}

	public Object createPacket() {
		try {
			Object packet = Class.forName("net.minecraft.server." + ReflectionUtil.getVersionString() + ".Packet63WorldParticles").getConstructor().newInstance();
			ReflectionUtil.setValue(packet, "a", this.getNmsName());
			ReflectionUtil.setValue(packet, "b", (float) this.getX());
			ReflectionUtil.setValue(packet, "c", (float) this.getY());
			ReflectionUtil.setValue(packet, "d", (float) this.getZ());
			ReflectionUtil.setValue(packet, "e", 0.5f);
			ReflectionUtil.setValue(packet, "f", 1f);
			ReflectionUtil.setValue(packet, "g", 0.5f);
			ReflectionUtil.setValue(packet, "h", this.getSpeed());
			ReflectionUtil.setValue(packet, "i", this.getParticleAmount());
			return packet;
		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to create Packet Object (Packet63WorldParticles).", e, true);
		}
		return null;
	}

	public abstract String getNmsName();
	public abstract float getSpeed();
	public abstract int getParticleAmount();

	@Override
	public boolean play() {
		boolean shouldPlay = super.play();
		if (shouldPlay) {
			for (Location l : this.displayType.getLocations(new Location(this.getWorld(), this.getX(), this.getY(), this.getZ()))) {
				try {
					ReflectionUtil.sendPacket(new Location(l.getWorld(), l.getX(), l.getY(), l.getZ()), this.createPacket());
				} catch (Exception e) {
					Logger.log(Logger.LogLevel.SEVERE, "Failed to send Packet Object (Packet63WorldParticles) to players within a radius of 20 [" + this.getWorld() + "," + this.getX() + "," + this.getY() + "," + this.getZ() + "].", e, true);
				}
			}
		}
		return shouldPlay;
	}

	public void playDemo(Player p) {
		try {
			Object packet = Class.forName("net.minecraft.server." + ReflectionUtil.getVersionString() + ".Packet63WorldParticles").getConstructor().newInstance();
			ReflectionUtil.setValue(packet, "a", this.getNmsName());
			ReflectionUtil.setValue(packet, "b", (float) p.getLocation().getX());
			ReflectionUtil.setValue(packet, "c", (float) p.getLocation().getY());
			ReflectionUtil.setValue(packet, "d", (float) p.getLocation().getZ());
			ReflectionUtil.setValue(packet, "e", 0.5f);
			ReflectionUtil.setValue(packet, "f", 1f);
			ReflectionUtil.setValue(packet, "g", 0.5f);
			ReflectionUtil.setValue(packet, "h", this.getSpeed());
			ReflectionUtil.setValue(packet, "i", this.getParticleAmount());
			ReflectionUtil.sendPacket(p, createPacket());
		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to send Packet Object (Packet63WorldParticles) to player [" + p.getName() + "].", e, true);
		}
	}
}