package io.github.dsh105.sparktrail.particle.type;

import io.github.dsh105.sparktrail.logger.Logger;
import io.github.dsh105.sparktrail.particle.Effect;
import io.github.dsh105.sparktrail.particle.EffectHolder;
import io.github.dsh105.sparktrail.particle.ParticleType;
import io.github.dsh105.sparktrail.util.ReflectionUtil;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Project by DSH105
 */

public class Firework extends Effect {

    public FireworkEffect fireworkEffect;

    public Firework(EffectHolder effectHolder, ParticleType particleType, FireworkEffect fireworkEffect) {
        super(effectHolder, particleType);
        this.fireworkEffect = fireworkEffect;
    }

    @Override
    public boolean play() {
        boolean shouldPlay = super.play();
        if (shouldPlay) {
            for (Location l : this.displayType.getLocations(new Location(this.getWorld(), this.getX(), this.getY(), this.getZ()))) {
                ReflectionUtil.spawnFirework(new Location(l.getWorld(), l.getX(), l.getY(), l.getZ()), this.fireworkEffect);
            }
        }
        return shouldPlay;
    }

    public void playDemo(Player p) {
        try {
            Object packet = Class.forName("net.minecraft.server." + ReflectionUtil.getVersionString() + ".Packet63WorldParticles").getConstructor().newInstance();
            ReflectionUtil.setValue(packet, "a", "fireworksSpark");
            ReflectionUtil.setValue(packet, "b", (float) p.getLocation().getX());
            ReflectionUtil.setValue(packet, "c", (float) p.getLocation().getY());
            ReflectionUtil.setValue(packet, "d", (float) p.getLocation().getZ());
            ReflectionUtil.setValue(packet, "e", 0.5f);
            ReflectionUtil.setValue(packet, "f", 1f);
            ReflectionUtil.setValue(packet, "g", 0.5f);
            ReflectionUtil.setValue(packet, "h", 50);
            ReflectionUtil.setValue(packet, "i", 0);

            ReflectionUtil.sendPacket(p, packet);
        } catch (Exception e) {
            Logger.log(Logger.LogLevel.SEVERE, "Failed to send Packet Object (Packet63WorldParticles) to player [" + p.getName() + "].", e, true);
        }
    }
}