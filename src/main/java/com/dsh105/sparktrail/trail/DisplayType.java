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
import org.bukkit.Location;

import java.util.ArrayList;


public enum DisplayType {

    NORMAL,
    CIRCLE,
    DOUBLE,
    ABOVE,
    FEET;

    public ArrayList<Location> getLocations(Location l) {
        ArrayList<Location> locations = new ArrayList<Location>();

        if (this == NORMAL || this == FEET) {
            locations.add(l);
        } else if (this == CIRCLE) {
            for (Location location : GeometryUtil.circle(l, 4, 1, true, false, true)) {
                locations.add(location);
            }
        } else if (this == DOUBLE) {
            locations.add(l);
            locations.add(new Location(l.getWorld(), l.getX(), l.getY() + 1.0D, l.getZ()));
        } else if (this == ABOVE) {
            locations.add(new Location(l.getWorld(), l.getX(), l.getY() + 2.0D, l.getZ()));
        }

        return locations;
    }
}