/*
 * This file is part of SparkTrail 3.
 *
 * SparkTrail 3 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SparkTrail 3 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SparkTrail 3.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.dsh105.sparktrail.trail;

import com.dsh105.dshutils.util.ReflectionUtil;
import net.minecraft.server.v1_7_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public abstract class PacketEffect extends Effect {

    public PacketEffect(EffectHolder effectHolder, ParticleType particleType) {
        super(effectHolder, particleType);
    }

    public Object createPacket() {
        return new PacketPlayOutWorldParticles(
                this.getNmsName(),
                (float) this.getHolder().getEffectPlayLocation().getX(),
                (float) this.getHolder().getEffectPlayLocation().getY(),
                (float) this.getHolder().getEffectPlayLocation().getZ(),
                0.5F, 1F, 0.5F,
                this.getSpeed(), this.getParticleAmount());
    }

    public abstract String getNmsName();

    public abstract float getSpeed();

    public abstract int getParticleAmount();

    @Override
    public boolean play() {
        boolean shouldPlay = super.play();
        if (shouldPlay) {
            for (Location l : this.displayType.getLocations(this.getHolder().getEffectPlayLocation())) {
                ReflectionUtil.sendPacket(new Location(l.getWorld(), l.getX(), l.getY(), l.getZ()), this.createPacket());
            }
        }
        return shouldPlay;
    }

    public void playDemo(Player p) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                this.getNmsName(),
                (float) (p.getLocation().getX() + 0.5D),
                (float) p.getLocation().getY(),
                (float) (p.getLocation().getZ() + 0.5D),
                0.5F, 1F, 0.5F,
                this.getSpeed(), this.getParticleAmount());
        ReflectionUtil.sendPacket(p, packet);
    }
}