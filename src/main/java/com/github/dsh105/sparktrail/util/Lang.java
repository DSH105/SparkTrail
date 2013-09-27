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
	COMMAND_ERROR("cmd_error", "&aError for input string: &e%cmd%&a. Use &e/" + SparkTrail.getInstance().cmdString + " help &afor help."),
	HELP_INDEX_TOO_BIG("help_index_too_big", "&aPage &e%index% &adoes not exist."),
	IN_GAME_ONLY("in_game_only", "&ePlease log in to do that."),
	STRING_ERROR("string_error", "&aError parsing String: [&e%string%&a]. Please revise command arguments."),
	NULL_PLAYER("null_player", "&e%player% &ais not online. Please try a different Player."),

	NO_ACTIVE_EFFECTS("no_active_effects", "&aYou do not have any Trail effects active."),

	EFFECT_CREATION_FAILED("effect_creation_failed", "&aEffect creation has failed. Please see the console for errors."),
	EFFECT_CREATION_CANCELLED("effect_creation_failed", "&e%effect% &aeffect creation cancelled."),

	MENU_ERROR("menu_error", "&aThere has been a problem with the Trail GUI Menu. Please see the console for details."),
	OPEN_MENU("open_menu", "&aOpening Trail Effect GUI"),
	ENTER_BLOCK("enter_block_break", "&aPlease enter effect parameters [&e%Block Break%&a]. Structure: &e<IdValue> <BlockMeta>"),
	ENTER_FIREWORK("enter_firework", "&aPlease enter effect parameters [&e%Firework%&a]. Separate each parameter with a space."),
	INCORRECT_EFFECT_ARGS("incorrect_blockbreak_args", "&aCould not create &e%effect% &aeffect from String [&e%string%&a]. Would you like to retry? &e[YES or NO]"),
	YES_OR_NO("yes_or_no", "&aPlease enter &eYES &aor &eNO&a."),

	DEMO_BEGIN("demo_begin", "&aStarting Trail Demo. You may enter the following commands during the presentation: &eNAME&a, &eSTOP&a."),
	DEMO_STOP("demo_stop", "&aTrail Demo has been stopped."),
	DEMO_CURRENT_PARTICLE("demo_current_particle", "&aCurrent Trail effect: &e%effect%&a."),

	ADMIN_OPEN_MENU("open_player_menu", "&aOpening Trail Effect GUI for &e%player%&a."),

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