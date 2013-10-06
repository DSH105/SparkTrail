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
	INT_ONLY("int_only", "&e%string% &a[Arg &e%argNum%&a] needs to be an integer."),

	NO_ACTIVE_EFFECTS("no_active_effects", "&aYou do not have any Trail effects active."),
	LOC_NO_ACTIVE_EFFECTS("loc_no_active_effects", "&aThere are no Location Trail effects active."),

	EFFECT_CREATION_FAILED("effect_creation_failed", "&aEffect creation has failed. Please see the console for errors."),
	EFFECT_CREATION_CANCELLED("effect_creation_cancelled", "&e%effect% &aeffect creation cancelled."),
	EFFECT_ADDED("effect_added", "&e%effect% &aeffect added."),
	EFFECT_REMOVED("effect_removed", "&e%effect% &aeffect removed."),
	EFFECTS_LOADED("effects_loaded", "&aYour Trail effects have been loaded."),
	EFFECTS_CLEARED("effects_cleared", "&aYour Trail effects have been cleared."),
	EFFECTS_STOPPED("effects_stopped", "&aYour Trail effects have been stopped."),
	NO_EFFECTS_TO_LOAD("no_effects_to_load", "&aYou have no Trail effects to load."),
	RANDOM_SELECT_FAILED("random_select_failed", "&aRandom Trail selection has failed."),
	SWIRL_NOT_ALLOWED("swirl_not_allowed", "&eSwirl &aTrail effect cannot be added to blocks."),

	LOC_EFFECTS_STOPPED("loc_effects_stopped", "&aLocation Trail effect stopped."),
	LOC_STOP_ALL("loc_stop_all", "&aAll Location Trail effects have been stopped."),

	CRITICAL_HELP("critical_help", "&eCritical &aeffects: &eNormal&a, &eMagic&a."),
	POTION_HELP("potion_help", "&ePotion &aeffects: &eAqua&a, &eBlack&a, &eBlue&a, &eCrimson&a, &eDarkBlue&a, &eDarkGreen&a, &eDarkRed&a, &eGold&a, &eGray&a, &eGreen&a, &ePink&a, &eRed&a."),
	SMOKE_HELP("smoke_help", "&eSmoke &aeffects: &eRed&a, &eBlack&a, &eRainbow&a."),
	SWIRL_HELP("swirl_help", "&eSwirl &aeffects: &eLightBlue&a, &eBlue&a, &eDarkBlue&a, &eRed&a, &eDarkRed&a, &eGreen&a, &eDarkGreen&a, &eYellow&a, &eOrange&a, &eGray&a, &eBlack&a, &eWhite&a, &ePurple&a, &ePink&a."),

	MENU_ERROR("menu_error", "&aThere has been a problem with the Trail GUI Menu. Please see the console for details."),
	OPEN_MENU("open_menu", "&aOpening Trail Effect GUI"),
	ENTER_BLOCK("enter_block_break", "&aPlease enter effect parameters [&eBlock Break&a]. Structure: &e<IdValue> <BlockMeta>"),
	ENTER_FIREWORK("enter_firework", "&aPlease enter effect parameters [&eFirework&a]. Separate each parameter with a space."),
	INCORRECT_EFFECT_ARGS("incorrect_blockbreak_args", "&aCould not create &e%effect% &aeffect from String [&e%string%&a]. Would you like to retry? &e[YES or NO]"),
	YES_OR_NO("yes_or_no", "&aPlease enter &eYES &aor &eNO&a."),
	INTERACT_BLOCK("interact_block", "&aPlease interact with a target block to modify its Trail effects."),
	INTERACT_MOB("interact_mob", "&aPlease interact with a target entity to modify its Trail effects."),
	RETRY_BLOCK_INTERACT("retry_block_interact", "&aUse the Trail command to create Air Trail effects. Would you like to retry creating a block effect? &e[YES or NO]"),
	RETRY_MOB_INTERACT("retry_mob_interact", "&aInteract with an Entity (other than a Player) to modify its Trail effects. Would you like to retry? &e[YES or NO]"),
	EXECUTE_LOC_LIST("execute_loc_list", "&aExecute &e/" + SparkTrail.getInstance().cmdString + " list &ato view the list of Location Trail effects. Use the IDs to run &e/" + SparkTrail.getInstance().cmdString + " stop <id>&a."),

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