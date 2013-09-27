package com.github.dsh105.sparktrail.util;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Project by DSH105
 */

public class Serialise {

	public static String serialiseLocation(Location l) {
		return l.getWorld().getName() + "," + l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ();
	}

	public static String serialiseFireworkEffect(FireworkEffect fe) {
		List<Color> colours = fe.getColors();
		FireworkEffect.Type type = fe.getType();
		boolean flicker = fe.hasFlicker();
		boolean trail = fe.hasTrail();

		String s = "";
		for (Color c : colours) {
			s = s + c.toString() + ",";
		}
		s = s + type + (flicker ? ",flicker" : "") + (trail ? ",trail" : "");
		return s;
	}

	public static FireworkEffect deserialiseFireworkEffect(String s) {
		FireworkEffect fe = null;
		ArrayList<Color> colours = new ArrayList<Color>();
		FireworkEffect.Type type = FireworkEffect.Type.BALL;
		boolean flicker = false;
		boolean trail = false;

		String[] split = s.split(",");
		for (int i = 0; i < split.length; i++) {
			if (s.equalsIgnoreCase("flicker")) {
				flicker = true;
			}
			if (s.equalsIgnoreCase("trail")) {
				trail = true;
			}

			if (EnumUtil.isEnumType(Colour.class, s.toUpperCase())) {
				colours.add(Colour.valueOf(s.toUpperCase()).getColor());
			}

			if (EnumUtil.isEnumType(FireworkEffect.Type.class, s.toUpperCase())) {
				type = FireworkEffect.Type.valueOf(s.toUpperCase());
			}
		}

		fe = FireworkEffect.builder().withColor(colours).withFade(colours).with(type).flicker(flicker).trail(trail).build();
		return fe;
	}

}