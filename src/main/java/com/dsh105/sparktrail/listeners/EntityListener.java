package com.dsh105.sparktrail.listeners;

import com.dsh105.sparktrail.data.EffectManager;
import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.type.ItemSpray;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.ArrayList;
import java.util.Iterator;


public class EntityListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        EffectHolder holder = EffectManager.getInstance().getEffect(event.getEntity().getUniqueId());
        if (holder != null) {
            EffectManager.getInstance().remove(holder);
            //ConsoleLogger.log(Logger.LogLevel.WARNING, "Entity with active Trail effect has despawned: {Type: " + event.getEntity().getType() + ", ID: " + event.getEntity().getUniqueId() + "}");
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        ArrayList<Entity> toRemove = new ArrayList<Entity>();
        for (Entity e : event.getChunk().getEntities()) {
            EffectHolder holder = EffectManager.getInstance().getEffect(e.getUniqueId());
            if (holder != null) {
                EffectManager.getInstance().clear(holder);
                continue;
            }

            if (e instanceof Item && ItemSpray.UUID_LIST.contains(e.getUniqueId())) {
                toRemove.add(e);
            }
        }
        if (!toRemove.isEmpty()) {
            Iterator<Entity> i = toRemove.iterator();
            while (i.hasNext()) {
                i.next().remove();
            }
        }
    }
}