package com.github.dsh105.sparktrail.util;

import com.github.dsh105.sparktrail.SparkTrail;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Project by DSH105
 */

public enum Lang {

	NO_PERMISSION("no_permission", "&e%perm% &apermission needed to do that."),
	ADMIN_COMMAND_ERROR("admin_cmd_error", "&aError for input string: &e%cmd%&a. Use /" + SparkTrail.getInstance().adminCmdString + " for help"),
	COMMAND_ERROR("cmd_error", "&aError for input string: &e%cmd%&a. Use /" + SparkTrail.getInstance().cmdString + " for help."),
	IN_GAME_ONLY("in_game_only", "&ePlease log in to do that."),
	STRING_ERROR("string_error", "&aError parsing String: [&e%string%&a]. Please revise command arguments."),
	EFFECT_CREATION_FAILED("effect_creation_failed", "&aEffect creation has failed. Please see the console for errors."),
	OPEN_MENU("open_menu", "", ""),

	ENTER_BLOCK("enter_block_break", "Please enter effect parameters [&e%Block Break%&a]. Structure: &e<IdValue> <BlockMeta>"),
	ENTER_FIREWORK("enter_firework", "Please enter effect parameters [&e%Firework%&a]. Separate each parameter with a space."),
	INCORRECT_EFFECT_ARGS("incorrect_blockbreak_args", "&aCould not create &e%effect% &aeffect from String [&e%string%&a]. Would you like to retry? &e[YES or NO]"),
	YES_OR_NO("yes_or_no", "&aPlease enter &eYES &aor &eNO&a."),

	;

	private String path;
	private String def;
	private String[] desc;

	Lang(String path, String def, String... desc) {
		this.path = path;
		this.def = def;
		this.desc = desc;
	}

	public String[] getDescription() {
		return this.desc;
	}

	public String getPath() {
		return this.path;
	}

	public static void sendTo(CommandSender sender, String msg) {
		if (msg != null || !msg.equalsIgnoreCase("") && !msg.equalsIgnoreCase(" ") && !msg.equalsIgnoreCase("none")) {
			sender.sendMessage(SparkTrail.getInstance().prefix + msg);
		}
	}

	public static void sendTo(Player p, String msg) {
		if (msg != null && !msg.equalsIgnoreCase("") && !msg.equalsIgnoreCase(" ") && !(msg.equalsIgnoreCase("none"))) {
			p.sendMessage(SparkTrail.getInstance().prefix + msg);
		}
	}

	@Override
	public String toString() {
		String result = SparkTrail.getInstance().getConfig(SparkTrail.ConfigType.LANG).getString(this.path, this.def);
		if (result != null && result != "" && result != "none") {
			return ChatColor.translateAlternateColorCodes('&', SparkTrail.getInstance().getConfig(SparkTrail.ConfigType.LANG).getString(this.path, this.def));
		}
		else {
			return "";
		}
	}

	public String toString_() {
		return SparkTrail.getInstance().getConfig(SparkTrail.ConfigType.LANG).getString(this.path, this.def);
	}
}