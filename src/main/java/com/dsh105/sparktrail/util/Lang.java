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

package com.dsh105.sparktrail.util;

import com.dsh105.sparktrail.SparkTrailPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public enum Lang {

    PREFIX("prefix", "&2ST&a » &r"),

    NO_PERMISSION("no_permission", "&aYou are not permitted to do that."),
    COMMAND_ERROR("cmd_error", "&aError for input string: &e%cmd%&a. Use &e/" + SparkTrailPlugin.getInstance().cmdString + " help &afor help."),
    HELP_INDEX_TOO_BIG("help_index_too_big", "&aPage &e%index% &adoes not exist."),
    IN_GAME_ONLY("in_game_only", "&ePlease log in to do that."),
    STRING_ERROR("string_error", "&aError parsing String: [&e%string%&a]. Please revise command arguments."),
    NULL_PLAYER("null_player", "&e%player% &ais not online. Please try a different Player."),
    INT_ONLY("int_only", "&e%string% &aneeds to be an integer."),
    INT_ONLY_WITH_ARGS("int_only_with_args", "&e%string% &a[Arg &e%argNum%&a] needs to be an integer."),
    NO_SOUND_IN_STRING("no_sound_in_string", "&aError parsing String: [&e%string%&a]. Sound Trail could not be created."),
    WHUPS("whups", "&aWhups. Something bad happened. Please report this, along with information on what you did."),
    CONFIGS_RELOADED("configs_reloaded", "&aConfiguration files reloaded."),
    MENU_DISABLED("menu_disabled", "&aTrail menu has been globally disabled."),

    PLAYER_LIST_NO_ACTIVE_EFFECTS("player_list_no_active_effects", "&aThere are no Player Trail effects active."),
    PLAYER_NO_ACTIVE_EFFECTS("player_no_active_effects", "&aThere are no Player Trail effects active for &e%player%&a."),
    PLAYER_NO_EFFECTS_TO_LOAD("player_no_effects_to_load", "&aThere are no Trail effects to load for &e%player%&a."),
    PLAYER_EFFECTS_LOADED("player_effects_loaded", "&aTrail effects have been loaded for &e%player%&a."),
    PLAYER_EFFECTS_STOPPED("player_effects_stopped", "&aTrail effects stopped for &e%player%&a."),
    PLAYER_EFFECTS_CLEARED("player_effects_cleared", "&aTrail effects cleared for &e%player%&a."),

    NO_ACTIVE_EFFECTS("no_active_effects", "&aYou do not have any Trail effects active."),
    TIMEOUT_SET("timeout_set", "&aTimeout has been set to &e%timeout%&a."),
    MAX_EFFECTS_ALLOWED("max_effects_allowed", "&aMaximum number of effects reached."),
    EFFECT_CREATION_FAILED("effect_creation_failed", "&aEffect creation has failed. SparkTrail may have encountered a severe error."),
    CANCEL_EFFECT_CREATION("cancel_effect_creation", "&aEffect creation cancelled."),
    EFFECT_ADDED("effect_added", "&e%effect% &aeffect added."),
    EFFECT_REMOVED("effect_removed", "&e%effect% &aeffect removed."),
    EFFECTS_LOADED("effects_loaded", "&aYour Trail effects have been loaded."),
    EFFECTS_CLEARED("effects_cleared", "&aYour Trail effects have been cleared."),
    NO_EFFECTS_TO_LOAD("no_effects_to_load", "&aYou have no Trail effects to load."),
    EFFECTS_STOPPED("effects_stopped", "&aYour Trail effects have been stopped."),
    RANDOM_SELECT_FAILED("random_select_failed", "&aRandom Trail selection has failed."),
    SWIRL_NOT_ALLOWED("swirl_not_allowed", "&eSwirl &aTrail effect cannot be added to blocks."),

    LOC_NO_EFFECTS_RETRY_BLOCK_INTERACT("loc_no_effects_retry_block_interact", "&aLocation Trail effects do no exist for that block. Would you like to retry? [&eYES &aor &eNO&a]"),
    LOC_EFFECTS_STARTED("loc_effects_started", "&aLocation Trail effect started."),
    LOC_EFFECTS_STOPPED("loc_effects_stopped", "&aLocation Trail effect stopped."),
    LOC_EFFECTS_CLEARED("loc_effects_cleared", "&aLocation Trail effect cleared."),
    LOC_EFFECTS_LOADED("loc_effects_loaded", "&Location Trail effects loaded."),
    LOC_NO_EFFECTS_TO_LOAD("loc_no_effects_to_load", "&aThere are no Trail effects to load for that location."),
    LOC_LIST_NO_ACTIVE_EFFECTS("loc_list_no_active_effects", "&aThere are no Location Trail effects active."),
    LOC_NO_ACTIVE_EFFECTS("loc_no_active_effects", "&aThere are no Trail effects active at that location."),
    LOC_NO_ACTIVE_EFFECTS_RETRY_BLOCK_INTERACT("loc_no_active_effects_retry_block_interact", "&aThere are no Location Trail effects active. Would you like to retry? [&eYES &aor &eNO&a]"),
    LOC_STOP_ALL("loc_stop_all", "&aAll Location Trail effects have been stopped."),

    MOB_NO_EFFECTS_RETRY_INTERACT("mob_no_effects_retry_interact", "&aMob Trail effects do no exist for that block. Would you like to retry? [&eYES &aor &eNO&a]"),
    MOB_EFFECTS_STOPPED("mob_effects_stopped", "&aMob Trail effect stopped."),
    MOB_EFFECTS_STARTED("mob_effects_started", "&aMob Trail effect started."),
    MOB_EFFECTS_CLEARED("mob_effects_cleared", "&aMob Trail effect cleared."),
    MOB_NO_EFFECTS_TO_LOAD("mob_no_effects_to_load", "&aThere are no Trail effects to load for that Mob."),
    MOB_LIST_NO_ACTIVE_EFFECTS("mob_list_no_active_effects", "&aThere are no Mob Trail effects active."),
    MOB_NO_ACTIVE_EFFECTS_RETRY_INTERACT("mob_no_active_effects_retry_interact", "&aThere are no Mob Trail effects active. Would you like to retry? [&eYES &aor &eNO&a]"),

    INVALID_EFFECT_ARGS("invalid_effect_args", "&aInvalid &e%effect% &aeffect arguments entered. %extra_info%"),
    CRITICAL_HELP("critical_help", "&eCritical &aeffects: &eNormal&a, &eMagic&a."),
    POTION_HELP("potion_help", "&ePotion &aeffects: &eAqua&a, &eBlack&a, &eBlue&a, &eCrimson&a, &eDarkBlue&a, &eDarkGreen&a, &eDarkRed&a, &eGold&a, &eGray&a, &eGreen&a, &ePink&a, &eRed&a."),
    SMOKE_HELP("smoke_help", "&eSmoke &aeffects: &eRed&a, &eBlack&a, &eRainbow&a."),
    SWIRL_HELP("swirl_help", "&eSwirl &aeffects: &eLightBlue&a, &eBlue&a, &eDarkBlue&a, &eRed&a, &eDarkRed&a, &eGreen&a, &eDarkGreen&a, &eYellow&a, &eOrange&a, &eGray&a, &eBlack&a, &eWhite&a, &ePurple&a, &ePink&a."),

    INPUT_FIRST_IDVALUE("input_first_idvalue", "&aWhat ID value would you like this effect to have?"),
    INPUT_SECOND_METAVALUE("input_second_idvalue", "&aWhat Block Meta value would you like this effect to have?"),
    INVALID_INPUT("invalid_input", "&aInvalid. Retry or enter &eexit &ato cancel."),
    INPUT_FIREWORK("input_firework", "&aWhat firework effects would you like this effect to have? Separate each parameter with a space."),
    INPUT_TIMEOUT("input_timeout", "&aHow long until this set of effects times out?"),

    MENU_ERROR("menu_error", "&aThere has been a problem with the Trail GUI Menu. Please see the console for details."),
    OPEN_MENU("open_menu", "&aOpening Trail Effect GUI"),
    ENTER_BLOCK_OR_ITEM("enter_block_break", "&aPlease enter effect parameters [&e%effect%&a]. Structure: &e<IdValue> <Meta>"),
    ENTER_FIREWORK("enter_firework", "&aPlease enter effect parameters [&eFirework&a]. Separate each parameter with a space."),
    ENTER_TIMEOUT("enter_timeout", "&aPlease enter an integer."),
    INCORRECT_EFFECT_ARGS("incorrect_blockbreak_args", "&aCould not create &e%effect% &aeffect from String [&e%string%&a]. Would you like to retry? [&eYES &aor &eNO&a]"),
    YES_OR_NO("yes_or_no", "&aPlease enter &eYES &aor &eNO&a."),
    INTERACT_BLOCK("interact_block", "&aPlease interact with a target block to modify its Trail effects."),
    INTERACT_MOB("interact_mob", "&aPlease interact with a target entity to modify its Trail effects."),
    RETRY_BLOCK_INTERACT("retry_block_interact", "&aUse the Trail command to create Air Trail effects. Would you like to retry creating a block effect? &e[YES or NO]"),
    RETRY_MOB_INTERACT("retry_mob_interact", "&aInteract with an Entity (other than a Player) to modify its Trail effects. Would you like to retry? [&eYES &aor &eNO&a]"),
    EXECUTE_LOC_LIST("execute_loc_list", "&aExecute &e/" + SparkTrailPlugin.getInstance().cmdString + " list &ato view the list of Location Trail effects. Use the IDs to run &e/" + SparkTrailPlugin.getInstance().cmdString + " stop <id>&a."),

    DEMO_BEGIN("demo_begin", "&aStarting Trail Demo. You may enter the following commands during the presentation: &eNAME&a, &eSTOP&a."),
    DEMO_STOP("demo_stop", "&aTrail Demo has been stopped."),
    DEMO_CURRENT_PARTICLE("demo_current_particle", "&aCurrent Trail effect: &e%effect%&a."),

    ADMIN_OPEN_MENU("open_player_menu", "&aOpening Trail Effect GUI for &e%player%&a."),;

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
            sender.sendMessage(SparkTrailPlugin.getInstance().prefix + msg);
        }
    }

    public static void sendTo(Player p, String msg) {
        if (msg != null && !msg.equalsIgnoreCase("") && !msg.equalsIgnoreCase(" ") && !(msg.equalsIgnoreCase("none"))) {
            p.sendMessage(SparkTrailPlugin.getInstance().prefix + msg);
        }
    }

    @Override
    public String toString() {
        String result = SparkTrailPlugin.getInstance().getConfig(SparkTrailPlugin.ConfigType.LANG).getString(this.path, this.def);
        if (result != null && result != "" && result != "none") {
            return ChatColor.translateAlternateColorCodes('&', SparkTrailPlugin.getInstance().getConfig(SparkTrailPlugin.ConfigType.LANG).getString(this.path, this.def));
        } else {
            return "";
        }
    }

    public String getRaw() {
        return SparkTrailPlugin.getInstance().getConfig(SparkTrailPlugin.ConfigType.LANG).getString(this.path, this.def);
    }
}
