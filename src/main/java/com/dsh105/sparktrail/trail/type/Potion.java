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


public class Potion extends Effect {

    public PotionType potionType;

    public Potion(EffectHolder effectHolder, PotionType potionType) {
        super(effectHolder, ParticleType.POTION);
        this.potionType = potionType;
    }

    @Override
    public boolean play() {
        boolean shouldPlay = super.play();
        if (shouldPlay) {
            for (Location l : this.displayType.getLocations(this.getHolder().getEffectPlayLocation())) {
                this.getWorld().playEffect(new Location(l.getWorld(), l.getX(), l.getY(), l.getZ()), org.bukkit.Effect.POTION_BREAK, this.potionType.getValue());
            }
        }
        return shouldPlay;
    }

    public void playDemo(Player p) {
        p.playEffect(new Location(p.getWorld(), p.getLocation().getX() + 0.5D, p.getLocation().getY(), p.getLocation().getZ() + 0.5D), org.bukkit.Effect.POTION_BREAK, PotionType.AQUA.getValue());
    }

    public enum PotionType {
        AQUA(2),
        BLACK(8),
        BLUE(0),
        CRIMSON(5),
        DARKBLUE(6),
        DARKGREEN(4),
        DARKRED(12),
        GOLD(3),
        GRAY(10),
        GREEN(20),
        PINK(1),
        RED(9);

        private int value;

        PotionType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}