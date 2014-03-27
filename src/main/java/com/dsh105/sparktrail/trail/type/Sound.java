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

import com.dsh105.sparktrail.trail.Effect;
import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.ParticleType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Sound extends Effect {

    public org.bukkit.Sound sound;

    public Sound(EffectHolder effectHolder, org.bukkit.Sound sound) {
        super(effectHolder, ParticleType.SOUND);
        this.sound = sound;
    }

    @Override
    public void playDemo(Player p) {
        p.playSound(this.getHolder().getEffectPlayLocation(), this.sound, 10.0F, 1.0F);
    }

    @Override
    public boolean play() {
        boolean shouldPlay = super.play();
        if (shouldPlay) {
            this.broadcast(this.getHolder().getEffectPlayLocation());
        }
        return shouldPlay;
    }

    public void broadcast(Location l) {
        this.getWorld().playSound(l, this.sound, 10.0F, 1.0F);
    }
}