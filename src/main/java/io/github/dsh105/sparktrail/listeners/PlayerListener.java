package io.github.dsh105.sparktrail.listeners;

import io.github.dsh105.sparktrail.SparkTrail;
import io.github.dsh105.sparktrail.data.EffectHandler;
import io.github.dsh105.sparktrail.particle.EffectHolder;
import io.github.dsh105.sparktrail.util.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Project by DSH105
 */

public class PlayerListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        EffectHolder eh = EffectHandler.getInstance().getEffect(p.getName());
        if (eh != null) {
            EffectHandler.getInstance().remove(eh);
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
                        EffectHolder eh = EffectHandler.getInstance().createFromFile(p.getName());
                        if (eh != null && !eh.getEffects().isEmpty()) {
                            Lang.sendTo(p, Lang.EFFECTS_LOADED.toString());
                        }
                    }
                }
            }.runTaskLater(SparkTrail.getInstance(), 20L);
        }
    }
}