package com.github.dsh105.sparktrail.particle.type;

import com.github.dsh105.sparktrail.logger.Logger;
import com.github.dsh105.sparktrail.particle.Effect;
import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.ParticleType;
import com.github.dsh105.sparktrail.util.ReflectionUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Project by DSH105
 */

public class BlockCrack extends Effect {

	int i = -1;

	public BlockCrack(EffectHolder effectHolder, ParticleType particleType) {
		super(effectHolder, particleType);
	}

	@Override
	public boolean play() {
		boolean shouldPlay = super.play();
		if (shouldPlay) {
			for (Location l : this.displayType.getLocations(new Location(this.getWorld(), this.getX(), this.getY(), this.getZ()))) {
				if (--i >= 11) {
					i = -1;
				}

				try {
					Object animation = Class.forName("net.minecraft.server." + ReflectionUtil.getVersionString() + ".Packet55BlockBreakAnimation")
							.getConstructor(Integer.class, Integer.class, Integer.class, Integer.class)
							.newInstance(0, l.getBlockX(), l.getBlockY(), l.getBlockZ(), i);
					ReflectionUtil.sendPacket(new Location(l.getWorld(), l.getX(), l.getY(), l.getZ()), animation);

				} catch (Exception e) {
					Logger.log(Logger.LogLevel.SEVERE, "Failed to create Packet Object (Packet55BlockBreakAnimation).", e, true);
				}
				/*Packet55BlockBreakAnimation animation = new Packet55BlockBreakAnimation(0, l.getBlockX(), l.getBlockY(), l.getBlockZ(), i);
				try {
					ReflectionUtil.sendPacket(new Location(l.getWorld(), l.getX(), l.getY(), l.getZ()), animation);
				} catch (Exception e) {
					Logger.log(Logger.LogLevel.SEVERE, "Failed to create Packet Object (Packet55BlockBreakAnimation).", e, true);
				}*/
			}
		}
		return shouldPlay;
	}

	public void playDemo(Player p) {
		if (--i >= 11) {
			i = -1;
		}

		try {
			Object animation = Class.forName("net.minecraft.server." + ReflectionUtil.getVersionString() + ".Packet55BlockBreakAnimation")
					.getConstructor(Integer.class, Integer.class, Integer.class, Integer.class)
					.newInstance(0, p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ(), i);
			ReflectionUtil.sendPacket(p, animation);

		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to create Packet Object (Packet55BlockBreakAnimation).", e, true);
		}
	}
}