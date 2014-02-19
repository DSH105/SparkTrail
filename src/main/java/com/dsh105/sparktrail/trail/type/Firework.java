package com.dsh105.sparktrail.trail.type;

import com.dsh105.dshutils.util.ReflectionUtil;
import com.dsh105.sparktrail.trail.Effect;
import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.ParticleType;
import net.minecraft.server.v1_7_R1.PacketPlayOutWorldParticles;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public class Firework extends Effect {

    public FireworkEffect fireworkEffect;

    public Firework(EffectHolder effectHolder, FireworkEffect fireworkEffect) {
        super(effectHolder, ParticleType.FIREWORK);
        this.fireworkEffect = fireworkEffect;
    }

    @Override
    public boolean play() {
        boolean shouldPlay = super.play();
        if (shouldPlay) {
            for (Location l : this.displayType.getLocations(this.getHolder().getEffectPlayLocation())) {
                ReflectionUtil.spawnFirework(new Location(l.getWorld(), l.getX(), l.getY() + 1, l.getZ()), this.fireworkEffect);
            }
        }
        return shouldPlay;
    }

    public void playDemo(Player p) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                "fireworksSpark",
                (float) (p.getLocation().getX() + 0.5D),
                (float) p.getLocation().getY(),
                (float) (p.getLocation().getZ() + 0.5D),
                0.5F, 1F, 0.5F,
                50F, 30);
        ReflectionUtil.sendPacket(p, packet);
    }
}