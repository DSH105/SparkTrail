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

import com.dsh105.dshutils.util.GeometryUtil;
import com.dsh105.sparktrail.util.protocol.wrapper.WrapperPacketWorldParticles;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class PacketEffect extends Effect {

    public PacketEffect(EffectHolder effectHolder, ParticleType particleType) {
        super(effectHolder, particleType);
    }

    public WrapperPacketWorldParticles createPacket() {
        WrapperPacketWorldParticles particles = new WrapperPacketWorldParticles();
        particles.setParticle(this.getNmsName());
        particles.setOffsetX(r.nextFloat());
        particles.setOffsetY(r.nextFloat());
        particles.setOffsetZ(r.nextFloat());
        particles.setParticleSpeed(this.getSpeed());
        particles.setParticleAmount(this.getParticleAmount());
        return particles;
    }

    public abstract String getNmsName();

    public abstract float getSpeed();

    public abstract int getParticleAmount();

    @Override
    public boolean play() {
        boolean shouldPlay = super.play();
        if (shouldPlay) {
            for (Location l : this.displayType.getLocations(this.getHolder().getEffectPlayLocation())) {
                WrapperPacketWorldParticles particles = this.createPacket();
                particles.setX((float) (l.getX() + 0.5D));
                particles.setY((float) l.getY());
                particles.setZ((float) (l.getZ() + 0.5D));
                for (Player p : GeometryUtil.getNearbyPlayers(l, 50)) {
                    particles.send(p);
                }
            }
        }
        return shouldPlay;
    }

    public void playDemo(Player p) {
        WrapperPacketWorldParticles particles = this.createPacket();
        particles.setX((float) (p.getLocation().getX() + 0.5D));
        particles.setY((float) p.getLocation().getY());
        particles.setZ((float) (p.getLocation().getZ() + 0.5D));
        particles.send(p);
    }
}