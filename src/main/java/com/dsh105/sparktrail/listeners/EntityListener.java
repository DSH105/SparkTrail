package com.dsh105.sparktrail.listeners;

import io.github.dsh105.dshutils.logger.ConsoleLogger;
import io.github.dsh105.dshutils.logger.Logger;
import com.dsh105.sparktrail.data.EffectManager;
import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.type.ItemSpray;
import net.minecraft.server.v1_7_R1.Entity;
import org.bukkit.craftbukkit.v1_7_R1.CraftChunk;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.world.ChunkUnloadEvent;


public class EntityListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        EffectHolder holder = EffectManager.getInstance().getEffect(event.getEntity().getUniqueId());
        if (holder != null) {
            EffectManager.getInstance().remove(holder);
            ConsoleLogger.log(Logger.LogLevel.WARNING, "Entity with active Trail effect has despawned: {Type: " + event.getEntity().getType() + ", ID: " + event.getEntity().getUniqueId() + "}");
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        for (int i = 0; i < ((CraftChunk) event.getChunk()).getHandle().entitySlices.length; ++i) {
            java.util.Iterator<Object> iter = ((CraftChunk) event.getChunk()).getHandle().entitySlices[i].iterator();
            while (iter.hasNext()) {
                Entity entity = (Entity) iter.next();
                if (EffectManager.getInstance().getEffect(entity.getUniqueID()) != null || (entity instanceof Item && ItemSpray.UUID_LIST.contains(((Item) entity).getUniqueId()))) {
                    iter.remove();
                }
            }
        }
    }
}