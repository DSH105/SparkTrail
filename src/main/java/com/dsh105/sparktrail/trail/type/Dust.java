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

package com.dsh105.sparktrail.trail.type;

import com.dsh105.dshutils.util.ReflectionUtil;
import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.PacketEffect;
import com.dsh105.sparktrail.trail.ParticleType;
import net.minecraft.server.v1_7_R1.PacketPlayOutWorldParticles;
import org.bukkit.entity.Player;


public class Dust extends PacketEffect {

    public int idValue;
    public int metaValue;

    public Dust(EffectHolder effectHolder, int idValue, int metaValue) {
        super(effectHolder, ParticleType.DUST);
        this.idValue = idValue;
        this.metaValue = metaValue;
    }

    @Override
    public Object createPacket() {
        return new PacketPlayOutWorldParticles(
                this.getNmsName() + "_" + idValue + "_" + metaValue,
                (float) this.getHolder().getEffectPlayLocation().getX(),
                (float) this.getHolder().getEffectPlayLocation().getY(),
                (float) this.getHolder().getEffectPlayLocation().getZ(),
                0.5F, 1F, 0.5F,
                this.getSpeed(), this.getParticleAmount());
    }

    @Override
    public void playDemo(Player p) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                this.getNmsName() + "_" + idValue + "_" + metaValue,
                (float) (p.getLocation().getX() + 0.5D),
                (float) p.getLocation().getY(),
                (float) (p.getLocation().getZ() + 0.5D),
                0.5F, 1F, 0.5F,
                this.getSpeed(), this.getParticleAmount());
        ReflectionUtil.sendPacket(p, packet);
    }

    @Override
    public String getNmsName() {
        return "blockdust";
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