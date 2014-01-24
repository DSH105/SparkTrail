package com.dsh105.sparktrail.chat;

import io.github.dsh105.dshutils.logger.Logger;
import io.github.dsh105.dshutils.util.StringUtil;
import com.dsh105.sparktrail.data.EffectCreator;
import com.dsh105.sparktrail.data.EffectManager;
import com.dsh105.sparktrail.listeners.InteractDetails;
import com.dsh105.sparktrail.listeners.InteractListener;
import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.ParticleDemo;
import com.dsh105.sparktrail.particle.ParticleDetails;
import com.dsh105.sparktrail.particle.ParticleType;
import com.dsh105.sparktrail.util.Lang;
import com.dsh105.sparktrail.util.Permission;
import com.dsh105.sparktrail.util.Serialise;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class MenuChatListener implements Listener {

    public static HashMap<String, WaitingData> AWAITING_DATA = new HashMap<String, WaitingData>();
    public static HashMap<String, WaitingData> AWAITING_RETRY = new HashMap<String, WaitingData>();
    public static ArrayList<String> AWAITING_TIMEOUT_INPUT = new ArrayList<String>();

    public static HashMap<String, InteractDetails> RETRY_INTERACT = new HashMap<String, InteractDetails>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String msg = event.getMessage();

        if (AWAITING_DATA.containsKey(player.getName())) {
            event.setCancelled(true);

            WaitingData wd = AWAITING_DATA.get(player.getName());

            ParticleType pt = wd.particleType;
            if (msg.equalsIgnoreCase("CANCEL")) {
                Lang.sendTo(player, Lang.EFFECT_CREATION_CANCELLED.toString().replace("%effect%", StringUtil.capitalise(AWAITING_DATA.get(player.getName()).particleType.toString())));
                AWAITING_DATA.remove(player.getName());
                return;
            } else if (pt == ParticleType.BLOCKBREAK) {
                BlockData bd = Serialise.findBlockBreak(msg);
                if (bd == null) {
                    Lang.sendTo(player, Lang.INCORRECT_EFFECT_ARGS.toString()
                            .replace("%effect%", "Block Break")
                            .replace("%string%", msg));
                    AWAITING_RETRY.put(player.getName(), wd);
                    AWAITING_DATA.remove(player.getName());
                    return;
                }

                EffectHolder eh = getHolder(wd);

                if (eh == null) {
                    Lang.sendTo(player, Lang.EFFECT_CREATION_FAILED.toString());
                    Logger.log(Logger.LogLevel.SEVERE, "Effect creation failed while adding Particle Type (" + wd.particleType.toString() + ", Player: " + player.getName() + ") [Reported from MenuChatListener].", true);
                    AWAITING_DATA.remove(player.getName());
                    return;
                }

                if (Permission.hasEffectPerm(player, true, pt, wd.effectType)) {
                    ParticleDetails pd = new ParticleDetails(pt);
                    pd.blockId = bd.id;
                    pd.blockMeta = bd.data;
                    if (eh.addEffect(pd, true)) {
                        Lang.sendTo(player, Lang.EFFECT_ADDED.toString().replace("%effect%", "Block Break"));
                    }
                }

                AWAITING_DATA.remove(player.getName());
                return;
            } else if (pt == ParticleType.FIREWORK) {
                FireworkEffect fe = Serialise.findFirework(msg);
                if (fe == null) {
                    Lang.sendTo(player, Lang.INCORRECT_EFFECT_ARGS.toString()
                            .replace("%effect%", "Firework")
                            .replace("%string%", msg));
                    AWAITING_RETRY.put(player.getName(), wd);
                    AWAITING_DATA.remove(player.getName());
                    return;
                }

                EffectHolder eh = getHolder(wd);

                if (eh == null) {
                    Lang.sendTo(player, Lang.EFFECT_CREATION_FAILED.toString());
                    Logger.log(Logger.LogLevel.SEVERE, "Effect creation failed while adding Particle Type (" + wd.particleType.toString() + ", Player: " + player.getName() + ") [Reported from MenuChatListener].", true);
                    AWAITING_DATA.remove(player.getName());
                    return;
                }

                if (Permission.hasEffectPerm(player, true, pt, wd.effectType)) {
                    ParticleDetails pd = new ParticleDetails(pt);
                    pd.fireworkEffect = fe;
                    if (eh.addEffect(pd, true)) {
                        Lang.sendTo(player, Lang.EFFECT_ADDED.toString().replace("%effect%", "Firework"));
                    }
                }

                AWAITING_DATA.remove(player.getName());
                return;
            }
            event.setCancelled(true);
        } else if (AWAITING_RETRY.containsKey(player.getName())) {
            if (msg.equalsIgnoreCase("YES")) {
                if (AWAITING_RETRY.get(player.getName()).particleType.equals(ParticleType.BLOCKBREAK)) {
                    Lang.sendTo(player, Lang.ENTER_BLOCK.toString());
                } else if (AWAITING_RETRY.get(player.getName()).equals(ParticleType.FIREWORK)) {
                    Lang.sendTo(player, Lang.ENTER_FIREWORK.toString());
                }
                AWAITING_DATA.put(player.getName(), AWAITING_RETRY.get(player.getName()));
                AWAITING_RETRY.remove(player.getName());
                event.setCancelled(true);
            } else if (msg.equalsIgnoreCase("NO")) {
                Lang.sendTo(player, Lang.EFFECT_CREATION_CANCELLED.toString().replace("%effect%", StringUtil.capitalise(AWAITING_RETRY.get(player.getName()).particleType.toString())));
                AWAITING_RETRY.remove(player.getName());
                event.setCancelled(true);
            } else {
                Lang.sendTo(player, Lang.YES_OR_NO.toString());
                event.setCancelled(true);
            }
        } else if (RETRY_INTERACT.containsKey(player.getName())) {
            InteractDetails.InteractType it = RETRY_INTERACT.get(player.getName()).interactType;
            if (msg.equalsIgnoreCase("YES")) {
                if (it.equals(InteractDetails.InteractType.BLOCK)) {
                    Lang.sendTo(player, Lang.INTERACT_BLOCK.toString());
                } else {
                    Lang.sendTo(player, Lang.INTERACT_MOB.toString());
                }
                InteractListener.INTERACTION.put(player.getName(), RETRY_INTERACT.get(player.getName()));
                RETRY_INTERACT.remove(player.getName());
                event.setCancelled(true);
            } else if (msg.equalsIgnoreCase("NO")) {
                Lang.sendTo(player, Lang.EFFECT_CREATION_CANCELLED.toString().replace("%effect%", StringUtil.capitalise(AWAITING_RETRY.get(player.getName()).particleType.toString())));
                RETRY_INTERACT.remove(player.getName());
                event.setCancelled(true);
            } else {
                Lang.sendTo(player, Lang.YES_OR_NO.toString());
                event.setCancelled(true);
            }
        } else if (ParticleDemo.ACTIVE.containsKey(player.getName())) {
            if (msg.equalsIgnoreCase("NAME")) {
                Lang.sendTo(player, Lang.DEMO_CURRENT_PARTICLE.toString().replace("%effect%", StringUtil.capitalise(ParticleDemo.ACTIVE.get(player.getName()).getCurrentParticle().toString())));
                event.setCancelled(true);
            } else if (msg.equalsIgnoreCase("STOP")) {
                ParticleDemo pd = ParticleDemo.ACTIVE.get(player.getName());
                pd.cancel();
                Lang.sendTo(player, Lang.DEMO_STOP.toString());
                event.setCancelled(true);

            }
        } else if (AWAITING_TIMEOUT_INPUT.contains(player.getName())) {
            if (StringUtil.isInt(msg)) {
                EffectHolder eh = EffectManager.getInstance().getEffect(player.getName());
                if (eh == null) {
                    Lang.sendTo(event.getPlayer(), Lang.NO_ACTIVE_EFFECTS.toString());
                }
                else if (Permission.TIMEOUT.hasPerm(player, true)) {
                    eh.setTimeout(Integer.parseInt(msg));
                    Lang.sendTo(player, Lang.TIMEOUT_SET.toString().replace("%timeout%", msg));
                }
                event.setCancelled(true);
            } else {
                Lang.sendTo(player, Lang.INT_ONLY.toString().replace("%string%", msg));
                event.setCancelled(true);
            }
        }
    }

    private EffectHolder getHolder(WaitingData data) {
        EffectHolder eh = null;
        if (data.effectType == EffectHolder.EffectType.LOCATION) {
            try {
                eh = EffectManager.getInstance().getEffect(data.location);
            } catch (Exception e) {
                Logger.log(Logger.LogLevel.SEVERE, "Failed to create Location (" + data + ") whilst removing an Effect (" + data.particleType.toString() + ")", e, true);
                return null;
            }
        } else if (data.effectType == EffectHolder.EffectType.PLAYER) {
            eh = EffectManager.getInstance().getEffect(data.playerName);
        } else if (data.effectType == EffectHolder.EffectType.MOB) {
            eh = EffectManager.getInstance().getEffect(data.mobUuid);
        }

        if (eh == null) {
            HashSet<ParticleDetails> hashSet = new HashSet<ParticleDetails>();
            if (data.effectType == EffectHolder.EffectType.PLAYER) {
                eh = EffectCreator.createPlayerHolder(hashSet, data.effectType, data.playerName);
            } else if (data.effectType == EffectHolder.EffectType.LOCATION) {
                Location l = data.location;
                if (l == null) {
                    Logger.log(Logger.LogLevel.SEVERE, "Failed to create Location Effect (" + data + ") whilst finding EffectHolder (" + data.particleType.toString() + ") [Reported from MenuChatListener].", true);
                    return null;
                }
                eh = EffectCreator.createLocHolder(hashSet, data.effectType, l);
            } else if (data.effectType == EffectHolder.EffectType.MOB) {
                eh = EffectCreator.createMobHolder(hashSet, data.effectType, data.mobUuid);
            }
        }
        return eh;
    }
}