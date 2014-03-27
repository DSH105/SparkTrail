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

import org.bukkit.Location;

public class LastPlayLoc {

    private int locX;
    private int locY;
    private int locZ;

    public LastPlayLoc(Location l) {
        this.set(l);
    }

    public void set(Location l) {
        this.locX = l.getBlockX();
        this.locY = l.getBlockY();
        this.locZ = l.getBlockZ();
    }

    public boolean isSimilar(Location l) {
        if (l.getBlockX() == this.locX
                && l.getBlockY() == this.locY
                && l.getBlockZ() == this.locZ) {
            return true;
        }
        this.set(l);
        return false;
    }
}