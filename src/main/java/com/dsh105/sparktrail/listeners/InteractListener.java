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

import com.dsh105.sparktrail.chat.MenuChatListener;
import com.dsh105.sparktrail.conversation.InputFactory;
import com.dsh105.sparktrail.conversation.YesNoFunction;
import com.dsh105.sparktrail.data.EffectManager;
import com.dsh105.sparktrail.menu.ParticleMenu;
import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.util.Lang;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;


public class InteractListener implements Listener {

    public static HashMap<String, InteractDetails> INTERACTION = new HashMap<String, InteractDetails>();

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        if (INTERACTION.containsKey(p.getName()) && INTERACTION.get(p.getName()).interactType.equals(InteractDetails.InteractType.MOB)) {
            if (event.getRightClicked() instanceof Player) {
                InputFactory.promptInt(p, new YesNoFunction(INTERACTION.get(p.getName()), Lang.RETRY_MOB_INTERACT.toString()));
                INTERACTION.remove(p.getName());
                return;
            }
            Entity e = event.getRightClicked();
            if (INTERACTION.get(p.getName()).modifyType.equals(InteractDetails.ModifyType.ADD)) {
                ParticleMenu pm = new ParticleMenu(p, e.getUniqueId());
                pm.open(true);
                INTERACTION.remove(p.getName());
            } else if (INTERACTION.get(p.getName()).modifyType.equals(InteractDetails.ModifyType.STOP)) {
                EffectHolder eh = EffectManager.getInstance().getEffect(e.getUniqueId());
                if (eh == null) {
                    InputFactory.promptInt(p, new YesNoFunction(INTERACTION.get(p.getName()), Lang.MOB_NO_ACTIVE_EFFECTS_RETRY_INTERACT.toString()));
                } else {
                    EffectManager.getInstance().remove(eh);
                    Lang.sendTo(p, Lang.MOB_EFFECTS_STOPPED.toString());
                }
                INTERACTION.remove(p.getName());
            } else if (INTERACTION.get(p.getName()).modifyType.equals(InteractDetails.ModifyType.START)) {
                EffectHolder eh = EffectManager.getInstance().getEffect(e.getUniqueId());
                if (eh == null) {
                    InputFactory.promptInt(p, new YesNoFunction(INTERACTION.get(p.getName()), Lang.MOB_NO_EFFECTS_RETRY_INTERACT.toString()));
                } else {
                    Lang.sendTo(p, Lang.MOB_EFFECTS_STARTED.toString());
                }
                INTERACTION.remove(p.getName());
            } else if (INTERACTION.get(p.getName()).modifyType.equals(InteractDetails.ModifyType.CLEAR)) {
                EffectHolder eh = EffectManager.getInstance().createFromFile(e.getUniqueId());
                if (eh == null) {
                    InputFactory.promptInt(p, new YesNoFunction(INTERACTION.get(p.getName()), Lang.MOB_NO_ACTIVE_EFFECTS_RETRY_INTERACT.toString()));
                } else {
                    EffectManager.getInstance().clear(eh);
                    Lang.sendTo(p, Lang.MOB_EFFECTS_CLEARED.toString());
                }
                INTERACTION.remove(p.getName());
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
                    InputFactory.promptInt(p, new YesNoFunction(INTERACTION.get(p.getName()), Lang.RETRY_BLOCK_INTERACT.toString()));
                    INTERACTION.remove(p.getName());
                    return;
                }
                Location l = event.getClickedBlock().getLocation();
                if (INTERACTION.get(p.getName()).modifyType.equals(InteractDetails.ModifyType.ADD)) {
                    ParticleMenu pm = new ParticleMenu(p, l);
                    pm.open(true);
                    INTERACTION.remove(p.getName());
                } else if (INTERACTION.get(p.getName()).modifyType.equals(InteractDetails.ModifyType.STOP)) {
                    EffectHolder eh = EffectManager.getInstance().getEffect(l);
                    if (eh == null) {
                        InputFactory.promptInt(p, new YesNoFunction(INTERACTION.get(p.getName()), Lang.LOC_NO_ACTIVE_EFFECTS_RETRY_BLOCK_INTERACT.toString()));
                    } else {
                        EffectManager.getInstance().remove(eh);
                        Lang.sendTo(p, Lang.LOC_EFFECTS_STOPPED.toString());
                    }
                    INTERACTION.remove(p.getName());
                } else if (INTERACTION.get(p.getName()).modifyType.equals(InteractDetails.ModifyType.START)) {
                    EffectHolder eh = EffectManager.getInstance().createFromFile(l);
                    if (eh == null) {
                        InputFactory.promptInt(p, new YesNoFunction(INTERACTION.get(p.getName()), Lang.LOC_NO_EFFECTS_RETRY_BLOCK_INTERACT.toString()));
                    } else {
                        Lang.sendTo(p, Lang.LOC_EFFECTS_STARTED.toString());
                    }
                    INTERACTION.remove(p.getName());
                } else if (INTERACTION.get(p.getName()).modifyType.equals(InteractDetails.ModifyType.CLEAR)) {
                    EffectHolder eh = EffectManager.getInstance().getEffect(l);
                    if (eh == null) {
                        InputFactory.promptInt(p, new YesNoFunction(INTERACTION.get(p.getName()), Lang.LOC_NO_ACTIVE_EFFECTS_RETRY_BLOCK_INTERACT.toString()));
                    } else {
                        EffectManager.getInstance().clear(eh);
                        Lang.sendTo(p, Lang.LOC_EFFECTS_CLEARED.toString());
                    }
                    INTERACTION.remove(p.getName());
                }
                event.setCancelled(true);
            } else if (INTERACTION.get(p.getName()).equals(InteractDetails.InteractType.MOB)) {
                InputFactory.promptInt(p, new YesNoFunction(INTERACTION.get(p.getName()), Lang.RETRY_MOB_INTERACT.toString()));
                INTERACTION.remove(p.getName());
                return;
            }
            event.setCancelled(true);
        }
    }
}