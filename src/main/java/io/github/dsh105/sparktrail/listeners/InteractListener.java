package io.github.dsh105.sparktrail.listeners;

import io.github.dsh105.sparktrail.chat.MenuChatListener;
import io.github.dsh105.sparktrail.data.EffectHandler;
import io.github.dsh105.sparktrail.menu.ParticleMenu;
import io.github.dsh105.sparktrail.particle.EffectHolder;
import io.github.dsh105.sparktrail.util.Lang;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

/**
 * Project by DSH105
 */

public class InteractListener implements Listener {

    public static HashMap<String, InteractDetails> INTERACTION = new HashMap<String, InteractDetails>();

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        if (INTERACTION.containsKey(p.getName()) && INTERACTION.get(p.getName()).interactType.equals(InteractDetails.InteractType.MOB)) {
            if (!(event.getRightClicked() instanceof Player)) {
                MenuChatListener.RETRY_INTERACT.put(p.getName(), INTERACTION.get(p.getName()));
                INTERACTION.remove(p.getName());
                Lang.sendTo(p, Lang.RETRY_MOB_INTERACT.toString());
                return;
            }
            Entity e = event.getRightClicked();
            if (INTERACTION.get(p.getName()).modifyType.equals(InteractDetails.ModifyType.ADD)) {
                ParticleMenu pm = new ParticleMenu(p, e.getUniqueId());
                pm.open(true);
                INTERACTION.remove(p.getName());
                Lang.sendTo(p, Lang.OPEN_MENU.toString());
            } else {
                EffectHolder eh = EffectHandler.getInstance().getEffect(e.getUniqueId());
                if (eh != null) {
                    EffectHandler.getInstance().remove(eh);
                }
                INTERACTION.remove(p.getName());
                Lang.sendTo(p, Lang.LOC_EFFECTS_STOPPED.toString());
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (INTERACTION.containsKey(p.getName())) {
            if (INTERACTION.get(p.getName()).interactType.equals(InteractDetails.InteractType.BLOCK)) {
                if (!(event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
                    MenuChatListener.RETRY_INTERACT.put(p.getName(), INTERACTION.get(p.getName()));
                    INTERACTION.remove(p.getName());
                    Lang.sendTo(p, Lang.RETRY_BLOCK_INTERACT.toString());
                    return;
                }
                Location l = event.getClickedBlock().getLocation();
                if (INTERACTION.get(p.getName()).modifyType.equals(InteractDetails.ModifyType.ADD)) {
                    ParticleMenu pm = new ParticleMenu(p, l);
                    pm.open(true);
                    INTERACTION.remove(p.getName());
                    Lang.sendTo(p, Lang.OPEN_MENU.toString());
                } else {
                    EffectHolder eh = EffectHandler.getInstance().getEffect(l);
                    if (eh != null) {
                        EffectHandler.getInstance().remove(eh);
                    }
                    INTERACTION.remove(p.getName());
                    Lang.sendTo(p, Lang.LOC_EFFECTS_STOPPED.toString());
                }
                event.setCancelled(true);
            } else if (INTERACTION.get(p.getName()).equals(InteractDetails.InteractType.MOB)) {
                MenuChatListener.RETRY_INTERACT.put(p.getName(), INTERACTION.get(p.getName()));
                INTERACTION.remove(p.getName());
                Lang.sendTo(p, Lang.RETRY_MOB_INTERACT.toString());
                return;
            }
            event.setCancelled(true);
        }
    }
}