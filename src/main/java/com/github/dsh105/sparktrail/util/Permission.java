package com.github.dsh105.sparktrail.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Project by DSH105
 */

public enum Permission {

	UPDATE("sparktrail.update", ""),
	TRAIL("sparktrail.trail", "", "sparktrail.trail.*", "sparktrail.*"),
	EFFECT("sparktrail.trail.%effect%", "sparktrail.trail", "sparktrail.trail.*", "sparktrail.*"),
	DEMO("sparktrail.trail.demo", "sparktrail.trail", "sparktrail.trail.*", "sparktrail.*"),
	INFO("sparktrail.trail.info", "sparktrail.trail", "sparktrail.trail.*", "sparktrail.*"),
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
		}
		else {
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
		}
		else {
			return true;
		}
	}
}