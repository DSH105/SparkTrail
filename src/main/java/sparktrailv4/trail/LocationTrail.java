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

package sparktrailv4.trail;

import org.bukkit.Location;

public class LocationTrail extends Trail {

    private Location location;

    public LocationTrail(Location location) {
        super();
        this.location = location;
    }

    public LocationTrail(int tickInterval, Location location) {
        super(tickInterval);
        this.location = location;
    }

    @Override
    protected Location getDisplayLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LocationTrail that = (LocationTrail) o;

        return location.equals(that.location);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + location.hashCode();
        return result;
    }
}