package com.github.dsh105.sparktrail.util;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;

/**
 * Project by DSH105
 */

public class Serialise {

	public static String serialiseLocation(Location l) {
		return l.getWorld().getName() + "," + l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ();
	}

	public static String serialiseFireworkEffect(FireworkEffect fe) {
		return "";
	}

	public static FireworkEffect deserialiseFireworkEffect(String s) {
		return fe;
	}

}