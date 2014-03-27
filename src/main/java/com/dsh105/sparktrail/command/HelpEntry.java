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

package com.dsh105.sparktrail.command;

import org.bukkit.ChatColor;


public enum HelpEntry {

    SPARKTRAIL("/sparktrail", "View the plugin information."),
    TRAIL_HELP("/trail help <page>", "View the SparkTrail Help Menu"),
    TRAIL("/trail", "Open the Trail Menu to activate particle effects"),
    TRAIL_EFFECT("/trail <effect> [data]", "Activate a specific Trail effect"),
    TRAIL_TIMEOUT("/trail timeout <time>", "Sets how long until current effect auto-disables"),
    TRAIL_RANDOM("/trail random", "Activate a random Trail effect"),
    TRAIL_CLEAR("/trail clear", "Clear all Trail effect information"),
    TRAIL_START("/trail start", "Start all Trail effects previously stopped"),
    TRAIL_STOP("/trail stop", "Pause all active particle effects"),
    TRAIL_DEMO("/trail demo", "Display a Client-Side demonstration of all Trail effects."),
    TRAIL_INFO("/trail info", "View active particle effects"),
    TRAIL_SOUND("/trail sound <sound>", "Activate a Sound Trail effect"),

    TRAIL_LOC("/trail location", "Select Location Trail effects"),
    TRAIL_LOC_LIST("/trail location list", "List all active Location Trail effects"),
    TRAIL_LOC_START("/trail location start", "Start a Location Trail effect previously stopped"),
    TRAIL_LOC_STOP("/trail location stop", "Pause an active Location Trail effect"),
    TRAIL_LOC_CLEAR("/trail location clear", "Clear information for a Location Trail effect"),

    TRAIL_MOB("/trail mob", "Select Entity Trail effects"),
    TRAIL_MOB_LIST("/trail mob list", "List all active Entity Trail effects"),
    TRAIL_MOB_START("/trail mob start", "Start a Mob Trail effect previously stopped"),
    TRAIL_MOB_STOP("/trail mob stop", "Pause an active Mob Trail effect"),
    TRAIL_MOB_CLEAR("/trail mob clear", "Clear information for a Mob Trail effect"),

    TRAIL_PLAYER("/trail player <name>", "Open the Trail Menu to activate effects for another player"),
    TRAIL_PLAYER_INFO("/trail player <name> info", "View active Trail effects for another player"),
    TRAIL_PLAYER_LIST("/trail player list", "List all active Player Trail effects"),
    TRAIL_PLAYER_CLEAR("/trail player <name> clear", "Clear all Trail effect information for another player"),
    TRAIL_PLAYER_ACTIVATE("/trail player <name> start", "Start all Trail effects previously stopped for another player"),
    TRAIL_PLAYER_STOP("/trail player <name> stop", "Pause all active Trail effects for another player"),;

    private String line;
    private EntryType[] entryTypes;

    HelpEntry(String cmd, String desc, EntryType... entryTypes) {
        this.line = ChatColor.YELLOW + cmd + ChatColor.WHITE + "  •••  " + ChatColor.GREEN + desc;
        this.entryTypes = entryTypes;
    }

    public String getLine() {
        return this.line;
    }

    public EntryType[] getEntryTypes() {
        return this.entryTypes;
    }

    public boolean isType(EntryType entryType) {
        for (EntryType et : getEntryTypes()) {
            if (entryType.equals(et)) {
                return true;
            }
        }
        return false;
    }

    public enum EntryType {
        PLAYER_TRAIL, BLOCK_TRAIL, LOCATION_TRAIL;
    }
}