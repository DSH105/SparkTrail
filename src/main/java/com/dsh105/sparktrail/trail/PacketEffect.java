package com.dsh105.sparktrail.trail;

import com.dsh105.sparktrail.util.PluginHook;
import com.dsh105.dshutils.logger.Logger;
import com.dsh105.dshutils.util.ReflectionUtil;
import net.minecraft.server.v1_7_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.kitteh.vanish.VanishPlugin;


public abstract class PacketEffect extends Effect {

    public PacketEffect(EffectHolder effectHolder, ParticleType particleType) {
        super(effectHolder, particleType);
    }

    public Object createPacket() {
        return new PacketPlayOutWorldParticles(
                this.getNmsName(),
                (float) this.getHolder().getEffectPlayLocation().getX(),
                (float) this.getHolder().getEffectPlayLocation().getY(),
                (float) this.getHolder().getEffectPlayLocation().getZ(),
                0.5F, 1F, 0.5F,
                this.getSpeed(), this.getParticleAmount());
    }

    public abstract String getNmsName();

    public abstract float getSpeed();

    public abstract int getParticleAmount();

    @Override
    public boolean play() {
        //TODO: VNP support
        boolean shouldPlay = super.play();
        if (shouldPlay) {
            for (Location l : this.displayType.getLocations(this.getHolder().getEffectPlayLocation())) {
                try {
                    ReflectionUtil.sendPacket(new Location(l.getWorld(), l.getX(), l.getY(), l.getZ()), this.createPacket());
                } catch (Exception e) {
                    Logger.log(Logger.LogLevel.SEVERE, "Failed to send Packet Object (PacketPlayOutWorldParticles) to players within a radius of 20 [" + this.getWorld() + "," + this.getX() + "," + this.getY() + "," + this.getZ() + "].", e, true);
                }
            }
        }
        return shouldPlay;
    }

    public void playDemo(Player p) {
        try {
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                    this.getNmsName(),
                    (float) (p.getLocation().getX() + 0.5D),
                    (float) p.getLocation().getY(),
                    (float) (p.getLocation().getZ() + 0.5D),
                    0.5F, 1F, 0.5F,
                    this.getSpeed(), this.getParticleAmount());
            ReflectionUtil.sendPacket(p, packet);
        } catch (Exception e) {
            Logger.log(Logger.LogLevel.SEVERE, "Failed to send Packet Object (PacketPlayOutWorldParticles) to player [" + p.getName() + "].", e, true);
        }
    }
}