package com.dsh105.sparktrail.util;

import org.bukkit.Color;


public enum FireworkColour {

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

    FireworkColour(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public static FireworkColour getByColor(Color c) {
        for (FireworkColour fireworkColour : FireworkColour.values()) {
            if (fireworkColour.getColor().equals(c)) {
                return fireworkColour;
            }
        }
        return null;
    }
}