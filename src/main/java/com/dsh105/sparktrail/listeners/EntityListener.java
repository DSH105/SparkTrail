/*
 * This file is part of SparkTrail 3.
 *
 * SparkTrail 3 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SparkTrail 3 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SparkTrail 3.  If not, see <http://www.gnu.org/licenses/>.
 */

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