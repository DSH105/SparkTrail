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
import com.dsh105.sparktrail.trail.Effect;
import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.ParticleType;
import com.dsh105.sparktrail.util.ParticleUtil;
import com.dsh105.sparktrail.util.protocol.wrapper.WrapperPacketWorldParticles;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public class Firework extends Effect {

    public FireworkEffect fireworkEffect;

    public Firework(EffectHolder effectHolder, FireworkEffect fireworkEffect) {
        super(effectHolder, ParticleType.FIREWORK);
        this.fireworkEffect = fireworkEffect;
    }

    @Override
    public boolean play() {
        boolean shouldPlay = super.play();
        if (shouldPlay) {
            for (Location l : this.displayType.getLocations(this.getHolder().getEffectPlayLocation())) {
                ReflectionUtil.spawnFirework(new Location(l.getWorld(), l.getX(), l.getY() + 1, l.getZ()), this.fireworkEffect);
            }
        }
        return shouldPlay;
    }

    public void playDemo(Player p) {
        ParticleUtil.showPlayer(WrapperPacketWorldParticles.ParticleType.FIREWORK_SPARK, p, p.getLocation());
    }
}