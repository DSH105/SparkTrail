package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.Effect;
import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.ParticleType;
import com.dsh105.dshutils.logger.Logger;
import com.dsh105.dshutils.util.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;


public class Swirl extends Effect {

    public SwirlType swirlType;
    public UUID uuid;

    public Swirl(EffectHolder effectHolder, SwirlType swirlType, UUID entityUuid) {
        super(effectHolder, ParticleType.SWIRL);
        this.swirlType = swirlType;
        this.uuid = entityUuid;
    }

    @Override
    public boolean play() {
        boolean shouldPlay = super.play();
        if (shouldPlay) {
            Entity entity = null;
            if (this.getEffectType() == EffectHolder.EffectType.PLAYER) {
                Player p = Bukkit.getPlayerExact(this.getHolder().getDetails().playerName);
                entity = p;
            } else {
                for (Entity e : getWorld().getEntities()) {
                    if (e.getUniqueId() == this.uuid) {
                        entity = e;
                    }
                }
            }
            if (entity != null) {
                try {
                    Object nmsEntity = entity.getClass().getMethod("getHandle")
                            .invoke(entity);
                    Object dw = ReflectionUtil.getMethod(nmsEntity.getClass(), "getDataWatcher")
                            .invoke(nmsEntity);
                    ReflectionUtil.getMethod(dw.getClass(), "watch")
                            .invoke(dw, new Object[]{7, Integer.valueOf(this.swirlType.getValue())});
                } catch (Exception e) {
                    Logger.log(Logger.LogLevel.SEVERE, "Failed to access Entity Datawatcher (Swirl Effect).", e, true);
                }
            } else {
                Logger.log(Logger.LogLevel.SEVERE, "Failed to find correct Entity from UUID (Swirl Effect).", false);
            }
        }
        return shouldPlay;
    }

    public void playDemo(Player p) {
        try {
            Object nmsEntity = p.getClass().getMethod("getHandle")
                    .invoke(p);
            Object dw = ReflectionUtil.getMethod(nmsEntity.getClass(), "getDataWatcher")
                    .invoke(nmsEntity);
            ReflectionUtil.getMethod(dw.getClass(), "watch")
                    .invoke(dw, new Object[]{7, Integer.valueOf(this.swirlType.getValue())});
        } catch (Exception e) {
            Logger.log(Logger.LogLevel.SEVERE, "Failed to access Entity Datawatcher (Swirl Effect).", e, true);
        }
    }

    public void stopDemo(Player p) {
        try {
            Object nmsEntity = p.getClass().getMethod("getHandle")
                    .invoke(p);
            Object dw = ReflectionUtil.getMethod(nmsEntity.getClass(), "getDataWatcher")
                    .invoke(nmsEntity);
            ReflectionUtil.getMethod(dw.getClass(), "watch")
                    .invoke(dw, new Object[]{7, Integer.valueOf(0)});
        } catch (Exception e) {
            Logger.log(Logger.LogLevel.SEVERE, "Failed to access Entity Datawatcher (Swirl Effect).", e, true);
        }
    }

    @Override
    public void stop() {
        super.stop();
        Entity entity = null;
        for (Entity e : getWorld().getEntities()) {
            if (e.getUniqueId() == this.uuid) {
                entity = e;
            }
        }
        if (entity != null) {
            try {
                Object nmsEntity = entity.getClass().getMethod("getHandle")
                        .invoke(entity);
                Object dw = ReflectionUtil.getMethod(nmsEntity.getClass(), "getDataWatcher")
                        .invoke(nmsEntity);
                ReflectionUtil.getMethod(dw.getClass(), "watch")
                        .invoke(dw, new Object[]{8, Integer.valueOf(0)});
            } catch (Exception e) {
                Logger.log(Logger.LogLevel.SEVERE, "Failed to access Entity Datawatcher (Swirl Effect).", e, true);
            }
        } else {
            Logger.log(Logger.LogLevel.SEVERE, "Failed to find correct Entity from UUID (Swirl Effect).", false);
        }
    }

    public enum SwirlType {
        LIGHTBLUE(0x00FFFF),
        BLUE(0x0000FF),
        DARKBLUE(0x0000CC),
        RED(0xFF3300),
        DARKRED(0x660000),
        GREEN(0x00FF00),
        DARKGREEN(0x339900),
        YELLOW(0xFF9900),
        ORANGE(0xFF6600),
        GRAY(0xCCCCCC),
        BLACK(0x333333),
        WHITE(0xFFFFFF),
        PURPLE(0x9900CC),
        PINK(0xFF00CC);

        private int value;

        SwirlType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}