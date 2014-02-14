package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.PacketEffect;
import com.dsh105.sparktrail.trail.ParticleType;
import com.dsh105.dshutils.logger.Logger;
import com.dsh105.dshutils.util.ReflectionUtil;
import net.minecraft.server.v1_7_R1.PacketPlayOutWorldParticles;
import org.bukkit.entity.Player;


public class BlockBreak extends PacketEffect {

    public int idValue;
    public int metaValue;

    public BlockBreak(EffectHolder effectHolder, int idValue, int metaValue) {
        super(effectHolder, ParticleType.BLOCKBREAK);
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
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                this.getNmsName() + "_" + idValue + "_" + metaValue,
                (float) (p.getLocation().getX() + 0.5D),
                (float) p.getLocation().getY(),
                (float) (p.getLocation().getZ() + 0.5D),
                0.5F, 1F, 0.5F,
                this.getSpeed(), this.getParticleAmount());
        ReflectionUtil.sendPacket(p, packet);
    }

    @Override
    public String getNmsName() {
        return "blockcrack";
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