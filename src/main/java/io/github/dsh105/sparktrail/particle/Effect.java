package io.github.dsh105.sparktrail.particle;

import io.github.dsh105.sparktrail.SparkTrail;
import io.github.dsh105.sparktrail.api.event.EffectPlayEvent;
import io.github.dsh105.sparktrail.particle.type.*;
import io.github.dsh105.sparktrail.util.Serialise;
import io.github.dsh105.sparktrail.util.StringUtil;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

/**
 * Project by DSH105
 */

public abstract class Effect {

    private EffectHolder holder;

    protected DisplayType displayType;
    protected ParticleType particleType;
    protected BukkitTask task;

    public Effect(EffectHolder effectHolder, ParticleType particleType) {
        this.holder = effectHolder;
        this.particleType = particleType;

        this.displayType = this.particleType.getDisplayType();
        if (this.displayType == null) {
            this.displayType = DisplayType.NORMAL;
        }
    }

    public EffectHolder getHolder() {
        return this.holder;
    }

    public EffectHolder.EffectType getEffectType() {
        return this.holder.effectType;
    }

    public int getX() {
        return holder.locX;
    }

    public int getY() {
        return holder.locY;
    }

    public int getZ() {
        return holder.locZ;
    }

    public World getWorld() {
        return holder.world;
    }

    public boolean play() {
        EffectPlayEvent effectPlayEvent = new EffectPlayEvent(this);
        SparkTrail.getInstance().getServer().getPluginManager().callEvent(effectPlayEvent);
        return !effectPlayEvent.isCancelled();
    }

    public abstract void playDemo(Player p);

    public void stop() {
        this.task.cancel();
    }

    public ParticleType getParticleType() {
        return particleType;
    }

    public String getParticleData() {
        if (this.particleType == ParticleType.BLOCKBREAK) {
            return "ID: " + ((BlockBreak) this).idValue + ", Meta:" + ((BlockBreak) this).metaValue;
        } else if (this.particleType == ParticleType.CRITICAL) {
            return StringUtil.capitalise(((Critical) this).criticalType.toString());
        } else if (this.particleType == ParticleType.FIREWORK) {
            return Serialise.serialiseFireworkEffect(((Firework) this).fireworkEffect);
        }
        /*else if (this.particleType == ParticleType.NOTE) {
            return StringUtil.capitalise(((Note) this).noteType.toString());
		}*/
        else if (this.particleType == ParticleType.POTION) {
            return StringUtil.capitalise(((Potion) this).potionType.toString());
        } else if (this.particleType == ParticleType.SMOKE) {
            return StringUtil.capitalise(((Smoke) this).smokeType.toString());
        } else if (this.particleType == ParticleType.SWIRL) {
            return StringUtil.capitalise(((Swirl) this).swirlType.toString());
        } else {
            return "";
        }
    }
}