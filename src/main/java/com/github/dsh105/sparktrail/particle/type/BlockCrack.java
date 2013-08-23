package com.github.dsh105.sparktrail.particle.type;

import com.github.dsh105.sparktrail.logger.Logger;
import com.github.dsh105.sparktrail.particle.Effect;
import com.github.dsh105.sparktrail.particle.EffectType;
import com.github.dsh105.sparktrail.particle.ParticleType;
import com.github.dsh105.sparktrail.util.ReflectionUtil;
import net.minecraft.server.v1_6_R2.Packet55BlockBreakAnimation;
import org.bukkit.Location;

/**
 * Project by DSH105
 */

public class BlockCrack extends Effect {

	int i = -1;

	public BlockCrack(ParticleType particleType, EffectType effectType) {
		super(particleType, effectType);
	}

	@Override
	public boolean play() {
		boolean shouldPlay = super.play();
		if (shouldPlay) {
			if (--i >= 11) {
				i = -1;
			}

			Packet55BlockBreakAnimation animation = new Packet55BlockBreakAnimation(0, this.locX, this.locY, this.locZ, i);
			try {
				ReflectionUtil.sendPacket(new Location(this.world, this.locX, this.locY, this.locZ), animation);
			} catch (Exception e) {
				Logger.log(Logger.LogLevel.SEVERE, "Failed to create Packet Object (Packet55BlockBreakAnimation).", e, true);
			}
		}
		return shouldPlay;
	}
}