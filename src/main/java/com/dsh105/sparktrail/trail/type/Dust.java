package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.PacketEffect;
import com.dsh105.sparktrail.trail.ParticleType;
import com.dsh105.dshutils.logger.Logger;
import com.dsh105.dshutils.util.ReflectionUtil;
import net.minecraft.server.v1_7_R1.PacketPlayOutWorldParticles;
import org.bukkit.entity.Player;


public class Dust extends PacketEffect {

    public int idValue;
    public int metaValue;

    public Dust(EffectHolder effectHolder, int idValue, int metaValue) {
        super(effectHolder, ParticleType.DUST);
        this.idValue = idValue;
        this.metaValue = metaValue;
    }

    @Override
    public Object createPacket() {
        return new PacketPlayOutWorldParticles(
                this.getNmsName() + "_" + idValue + "_" + metaValue,
                (float) this.getHolder().getEffectPlayLocation().getX(),
                (float) this.getHolder().getEffectPlayLocation().getY(),
                (float) this.getHolder().getEffectPlayLocation().getZ(),
                0.5F, 1F, 0.5F,
                this.getSpeed(), this.getParticleAmount());
    }

    @Override
    public void playDemo(Player p) {
        try {
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                    this.getNmsName() + "_" + idValue + "_" + metaValue,
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

    @Override
    public String getNmsName() {
        return "blockdust";
    }

    @Override
    public float getSpeed() {
        return 0F;
    }

    @Override
    public int getParticleAmount() {
        return 100;
    }
}