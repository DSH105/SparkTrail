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

package com.dsh105.sparktrail.chat;

import com.dsh105.dshutils.util.StringUtil;
import com.dsh105.sparktrail.trail.ParticleDemo;
import com.dsh105.sparktrail.util.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class MenuChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String msg = event.getMessage();

        if (ParticleDemo.ACTIVE.containsKey(player.getName())) {
            if (msg.equalsIgnoreCase("NAME")) {
                Lang.sendTo(player, Lang.DEMO_CURRENT_PARTICLE.toString().replace("%effect%", StringUtil.capitalise(ParticleDemo.ACTIVE.get(player.getName()).getCurrentParticle().toString())));
                event.setCancelled(true);
            } else if (msg.equalsIgnoreCase("STOP")) {
                ParticleDemo pd = ParticleDemo.ACTIVE.get(player.getName());
                pd.cancel();
                Lang.sendTo(player, Lang.DEMO_STOP.toString());
                event.setCancelled(true);

            }
        }
    }
}