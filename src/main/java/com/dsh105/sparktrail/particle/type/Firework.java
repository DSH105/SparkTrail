package com.dsh105.sparktrail.particle.type;

import io.github.dsh105.dshutils.logger.Logger;
import com.dsh105.sparktrail.particle.Effect;
import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.ParticleType;
import io.github.dsh105.dshutils.util.ReflectionUtil;
import net.minecraft.server.v1_7_R1.PacketPlayOutWorldParticles;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;


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
                ReflectionUtil.spawnFirework(new Location(l.getWorld(), l.getX(), l.getY() + 1, l.getZ()), this.fireworkEffect);
            }
        }
        return shouldPlay;
    }

    public void playDemo(Player p) {
        try {
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                    "fireworksSpark",
                    (float) p.getLocation().getX(),
                    (float) p.getLocation().getY(),
                    (float) p.getLocation().getZ(),
                    0.5F, 1F, 0.5F,
                    50F, 30);
            ReflectionUtil.sendPacket(p, packet);
        } catch (Exception e) {
            Logger.log(Logger.LogLevel.SEVERE, "Failed to send Packet Object (PacketPlayOutWorldParticles) to player [" + p.getName() + "].", e, true);
        }
    }
}