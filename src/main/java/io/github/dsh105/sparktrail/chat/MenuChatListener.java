package io.github.dsh105.sparktrail.chat;

import io.github.dsh105.dshutils.logger.Logger;
import io.github.dsh105.dshutils.util.GeneralUtil;
import io.github.dsh105.sparktrail.data.EffectCreator;
import io.github.dsh105.sparktrail.data.EffectHandler;
import io.github.dsh105.sparktrail.listeners.InteractDetails;
import io.github.dsh105.sparktrail.listeners.InteractListener;
import io.github.dsh105.sparktrail.particle.EffectHolder;
import io.github.dsh105.sparktrail.particle.ParticleDemo;
import io.github.dsh105.sparktrail.particle.ParticleDetails;
import io.github.dsh105.sparktrail.particle.ParticleType;
import io.github.dsh105.sparktrail.util.Lang;
import io.github.dsh105.sparktrail.util.Permission;
import io.github.dsh105.sparktrail.util.Serialise;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.HashSet;


public class MenuChatListener implements Listener {

    public static HashMap<String, WaitingData> AWAITING_DATA = new HashMap<String, WaitingData>();
    public static HashMap<String, WaitingData> AWAITING_RETRY = new HashMap<String, WaitingData>();

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
                Lang.sendTo(player, Lang.EFFECT_CREATION_CANCELLED.toString().replace("%effect%", GeneralUtil.capitalise(AWAITING_DATA.get(player.getName()).particleType.toString())));
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
                    eh.addEffect(pd);
                    Lang.sendTo(player, Lang.EFFECT_ADDED.toString().replace("%effect%", "Block Break"));
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
                    eh.addEffect(pd);
                    Lang.sendTo(player, Lang.EFFECT_ADDED.toString().replace("%effect%", "Firework"));
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
                Lang.sendTo(player, Lang.EFFECT_CREATION_CANCELLED.toString().replace("%effect%", GeneralUtil.capitalise(AWAITING_RETRY.get(player.getName()).particleType.toString())));
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
                Lang.sendTo(player, Lang.EFFECT_CREATION_CANCELLED.toString().replace("%effect%", GeneralUtil.capitalise(AWAITING_RETRY.get(player.getName()).particleType.toString())));
                RETRY_INTERACT.remove(player.getName());
                event.setCancelled(true);
            } else {
                Lang.sendTo(player, Lang.YES_OR_NO.toString());
                event.setCancelled(true);
            }
        } else if (ParticleDemo.ACTIVE.containsKey(player.getName())) {
            if (msg.equalsIgnoreCase("NAME")) {
                Lang.sendTo(player, Lang.DEMO_CURRENT_PARTICLE.toString().replace("%effect%", GeneralUtil.capitalise(ParticleDemo.ACTIVE.get(player.getName()).getCurrentParticle().toString())));
                event.setCancelled(true);
            } else if (msg.equalsIgnoreCase("STOP")) {
                ParticleDemo pd = ParticleDemo.ACTIVE.get(player.getName());
                pd.cancel();
                Lang.sendTo(player, Lang.DEMO_STOP.toString());
                event.setCancelled(true);

            }
        }
    }

    private EffectHolder getHolder(WaitingData data) {
        EffectHolder eh = null;
        if (data.effectType == EffectHolder.EffectType.LOCATION) {
            try {
                eh = EffectHandler.getInstance().getEffect(data.location);
            } catch (Exception e) {
                Logger.log(Logger.LogLevel.SEVERE, "Failed to create Location (" + data + ") whilst removing an Effect (" + data.particleType.toString() + ")", e, true);
                return null;
            }
        } else if (data.effectType == EffectHolder.EffectType.PLAYER) {
            eh = EffectHandler.getInstance().getEffect(data.playerName);
        } else if (data.effectType == EffectHolder.EffectType.MOB) {
            eh = EffectHandler.getInstance().getEffect(data.mobUuid);
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