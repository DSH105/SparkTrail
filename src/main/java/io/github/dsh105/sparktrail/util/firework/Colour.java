package com.github.dsh105.sparktrail.util.firework;

import org.bukkit.Color;

/**
 * Project by DSH105
 */

public enum Colour {

	AQUA(Color.AQUA),
	BLACK(Color.BLACK),
	BLUE(Color.BLUE),
	FUCHSIA(Color.FUCHSIA),
	GRAY(Color.GRAY),
	GREEN(Color.GREEN),
	LIME(Color.LIME),
	MAROON(Color.MAROON),
	NAVY(Color.NAVY),
	OLIVE(Color.OLIVE),
	ORANGE(Color.ORANGE),
	PURPLE(Color.PURPLE),
	RED(Color.RED),
	SILVER(Color.SILVER),
	TEAL(Color.TEAL),
	WHITE(Color.WHITE),
	YELLOW(Color.YELLOW);

	private Color color;

	Colour(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	public static Colour getByColor(Color c) {
		for (Colour colour : Colour.values()) {
			if (colour.getColor().equals(c)) {
				return colour;
			}
		}
		return null;
 	}
}