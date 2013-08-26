package com.github.dsh105.sparktrail.util;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Project by DSH105
 */

public class GeometryUtil {

	public static List<Location> circle(Location loc, int r, int h, boolean hollow, boolean sphere) {
		List<Location> blocks = new ArrayList<Location>();
		int cx = loc.getBlockX(),
				cy = loc.getBlockY(),
				cz = loc.getBlockZ();
		for (int x = cx - r; x <= cx +r; x++)
			for (int z = cz - r; z <= cz +r; z++)
				for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
					double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
					if (dist < r*r && !(hollow && dist < (r-1)*(r-1))) {
						Location l = new Location(loc.getWorld(), x, y, z);
						blocks.add(l);
					}
				}
		return blocks;
	}

}