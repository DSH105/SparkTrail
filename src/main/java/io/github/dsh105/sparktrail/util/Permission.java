package io.github.dsh105.sparktrail.util;

import io.github.dsh105.sparktrail.particle.EffectHolder;
import io.github.dsh105.sparktrail.particle.ParticleType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Project by DSH105
 */

public enum Permission {

    UPDATE("sparktrail.update", ""),
    TRAIL("sparktrail.trail", "", "sparktrail.trail.*", "sparktrail.*"),

    PLAYER_TRAIL("sparktrail.trail.player", "", "sparktrail.trail.*", "sparktrail.*", "sparktrail.trail.player.*"),
    PLAYER_EFFECT("sparktrail.trail.player.%effect%", "", "sparktrail.trail.*", "sparktrail.*", "sparktrail.trail.player.*"),

    LOC_START("sparktrail.trail.location.start", "", "sparktrail.trail.*", "sparktrail.trail.location.*", "sparktrail.*"),
    LOC_STOP("sparktrail.trail.location.stop", "", "sparktrail.trail.*", "sparktrail.trail.location.*", "sparktrail.*"),
    LOC_CLEAR("sparktrail.trail.location.clear", "", "sparktrail.trail.*", "sparktrail.trail.location.*", "sparktrail.*"),
    LOC_TRAIL("sparktrail.trail.location", "", "sparktrail.trail.*", "sparktrail.trail.location.*", "sparktrail.*"),
    LOC_LIST("sparktrail.trail.location.list", "sparktrail.trail", "sparktrail.trail.location.*", "sparktrail.*"),

    MOB_START("sparktrail.trail.mob.start", "", "sparktrail.trail.*", "sparktrail.trail.location.*", "sparktrail.*"),
    MOB_STOP("sparktrail.trail.mob.stop", "", "sparktrail.trail.*", "sparktrail.trail.location.*", "sparktrail.*"),
    MOB_CLEAR("sparktrail.trail.mob.clear", "", "sparktrail.trail.*", "sparktrail.trail.location.*", "sparktrail.*"),
    MOB_TRAIL("sparktrail.trail.mob", "", "sparktrail.trail.*", "sparktrail.trail.location.*", "sparktrail.*"),
    MOB_LIST("sparktrail.trail.mob.list", "sparktrail.trail", "sparktrail.trail.location.*", "sparktrail.*"),

    EFFECT("sparktrail.trail.%effect%", "sparktrail.trail", "sparktrail.trail.*", "sparktrail.*"),
    CLEAR("sparktrail.trail.clear", "sparktrail.trail", "sparktrail.trail.*", "sparktrail.*"),
    STOP("sparktrail.trail.stop", "sparktrail.trail", "sparktrail.trail.*", "sparktrail.*"),
    START("sparktrail.trail.start", "sparktrail.trail", "sparktrail.trail.*", "sparktrail.*"),
    DEMO("sparktrail.trail.demo", "sparktrail.trail", "sparktrail.trail.*", "sparktrail.*"),
    INFO("sparktrail.trail.info", "sparktrail.trail", "sparktrail.trail.*", "sparktrail.*"),
    PLAYER_LIST("sparktrail.trail.player.list", "sparktrail.trail", "sparktrail.trail.location.*", "sparktrail.*"),
    ;

    String perm;
    String requiredPerm;
    ArrayList<String> hierarchy = new ArrayList<String>();

    Permission(String perm, String requiredPerm, String... hierarchy) {
        this.perm = perm;
        this.requiredPerm = requiredPerm;
        for (String s : hierarchy) {
            this.hierarchy.add(s);
        }
    }

    public boolean hasPerm(CommandSender sender, boolean sendMessage, boolean allowConsole) {
        if (sender instanceof Player) {
            return hasPerm(((Player) sender), sendMessage);
        } else {
            if (!allowConsole && sendMessage) {
                Lang.sendTo(sender, Lang.IN_GAME_ONLY.toString());
            }
            return allowConsole;
        }
    }

    public boolean hasPerm(Player player, boolean sendMessage) {
        boolean hasRequiredPerm = this.requiredPerm.equalsIgnoreCase("") ? true : player.hasPermission(this.requiredPerm);
        if (!(player.hasPermission(this.perm) && hasRequiredPerm)) {
            for (String s : this.hierarchy) {
                if (player.hasPermission(s)) {
                    return true;
                }
            }
            if (sendMessage) {
                Lang.sendTo(player, Lang.NO_PERMISSION.toString().replace("%perm%", this.perm));
            }
            return false;
        } else {
            return true;
        }
    }

    public static boolean hasEffectPerm(Player player, boolean sendMessage, String perm, EffectHolder.EffectType effectType) {
        if (!(player.hasPermission(perm) && player.hasPermission("sparktrail.trail"))) {
            for (String s : EFFECT.hierarchy) {
                if (player.hasPermission(s)) {
                    return true;
                }
            }
            if (player.hasPermission("sparktrail.trail." + effectType.toString().toLowerCase() + ".type.*") || player.hasPermission("sparktrail.trail.type.*") || player.hasPermission("sparktrail.trail." + effectType.toString())) {
                return true;
            }
            if (sendMessage) {
                Lang.sendTo(player, Lang.NO_PERMISSION.toString().replace("%perm%", perm));
            }
            return false;
        } else {
            return true;
        }
    }

    public static boolean hasEffectPerm(Player player, boolean sendMessage, ParticleType particleType, EffectHolder.EffectType effectType) {
        String perm = "sparktrail.trail." + effectType.toString().toLowerCase() + ".type." + particleType.toString().toLowerCase();
        return hasEffectPerm(player, sendMessage, perm, effectType) || hasEffectPerm(player, sendMessage, perm + ".*", effectType);
    }

    public static boolean hasEffectPerm(Player player, boolean sendMessage, ParticleType particleType, String data, EffectHolder.EffectType effectType) {
        String perm = "sparktrail.trail." + effectType.toString().toLowerCase() + ".type." + particleType.toString().toLowerCase();
        return hasEffectPerm(player, sendMessage, perm + "." + data, effectType) || hasEffectPerm(player, sendMessage, perm + ".*", effectType);
    }
}