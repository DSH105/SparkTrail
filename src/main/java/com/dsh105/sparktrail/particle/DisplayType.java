package com.dsh105.sparktrail.particle;

import io.github.dsh105.dshutils.util.GeometryUtil;
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

        if (this == DisplayType.NORMAL) {
            locations.add(l);
        } else if (this == DisplayType.CIRCLE) {
            for (Location location : GeometryUtil.circle(l, 4, 1, true, false, true)) {
                locations.add(location);
            }
        } else if (this == DisplayType.DOUBLE) {
            locations.add(l);
            locations.add(new Location(l.getWorld(), l.getX(), l.getY() + 1.0D, l.getZ()));
        } else if (this == DisplayType.ABOVE) {
            locations.add(new Location(l.getWorld(), l.getX(), l.getY() + 2.0D, l.getZ()));
        }

        return locations;
    }
}