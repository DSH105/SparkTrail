package io.github.dsh105.sparktrail.util.firework;

import org.bukkit.FireworkEffect;


public enum FireworkType {

    SMALL(FireworkEffect.Type.BALL),
    LARGE(FireworkEffect.Type.BALL_LARGE),
    BURST(FireworkEffect.Type.BURST),
    CREEPER(FireworkEffect.Type.CREEPER),
    STAR(FireworkEffect.Type.STAR);


    private FireworkEffect.Type fireworkType;

    FireworkType(FireworkEffect.Type fireworkType) {
        this.fireworkType = fireworkType;
    }

    public FireworkEffect.Type getFireworkType() {
        return this.fireworkType;
    }

    public static FireworkType getByType(FireworkEffect.Type type) {
        for (FireworkType t : FireworkType.values()) {
            if (t.getFireworkType().equals(type)) {
                return t;
            }
        }
        return null;
    }
}