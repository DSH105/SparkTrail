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

import com.dsh105.sparktrail.SparkTrailPlugin;
import com.dsh105.sparktrail.data.EffectManager;
import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.type.ItemSpray;
import com.dsh105.sparktrail.util.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class PlayerListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        EffectHolder eh = EffectManager.getInstance().getEffect(p.getName());
        if (eh != null) {
            EffectManager.getInstance().remove(eh);
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        final Player p = event.getPlayer();
        if (event.getResult().equals(PlayerLoginEvent.Result.ALLOWED)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (p != null) {
                        EffectHolder eh = EffectManager.getInstance().createFromFile(p.getName());
                        if (eh != null && !eh.getEffects().isEmpty()) {
                            Lang.sendTo(p, Lang.EFFECTS_LOADED.toString());
                        }
                    }
                }
            }.runTaskLater(SparkTrailPlugin.getInstance(), 20L);
        }
    }

    @EventHandler
    public void onInventoryPickup(InventoryPickupItemEvent event) {
        if (event.getInventory().getType() == InventoryType.HOPPER) {
            if (ItemSpray.UUID_LIST.contains(event.getItem().getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
}