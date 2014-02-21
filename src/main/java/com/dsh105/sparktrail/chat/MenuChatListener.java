package com.dsh105.sparktrail.chat;

import com.dsh105.dshutils.util.StringUtil;
import com.dsh105.sparktrail.listeners.InteractDetails;
import com.dsh105.sparktrail.listeners.InteractListener;
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