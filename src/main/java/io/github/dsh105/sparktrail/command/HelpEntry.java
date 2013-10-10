package com.github.dsh105.sparktrail.command;

import org.bukkit.ChatColor;

/**
 * Project by DSH105
 */

public enum HelpEntry {

	SPARKTRAIL("/sparktrail", "View the plugin information."),
	TRAIL("/trail", "Open the Trail Menu to activate particle effects"),
	TRAIL_CLEAR("/trail clear", "Clear all Trail information"),
	TRAIL_DEMO("/trail demo", "Display a Client-Side demonstration of all Trail effects."),
	TRAIL_INFO("/trail info", "View active particle effects"),
	TRAIL_LOC("/trail location", "Select Location Trail effects"),
	TRAIL_LOCATION_INFO("/trail location <id> info ", "View active particle effects for a location"),
	TRAIL_LOC_LIST("/trail location list", "List all active Location Trail effects"),
	TRAIL_MOB("/trail mob", "Select Entity Trail effects"),
	TRAIL_MOB_INFO("/trail mob <uuid> info ", "View active particle effects for an Entity [by UUID]"),
	TRAIL_MOB_LIST("/trail mob list", "List all active Entity Trail effects"),
	TRAIL_PLAYER("/trail player <name>", "Activate Trail effects for another player"),
	TRAIL_PLAYER_CLEAR("/trail player <name> clear", "Clear all Trail information for another player"),
	TRAIL_PLAYER_INFO("/trail player <name> info", "View active particle effects for another player"),
	TRAIL_PLAYER_LIST("/trail player list", "List all active Player Trail effects"),
	TRAIL_PLAYER_ACTIVATE("/trail player <name> start", "Start all particle effects previously stopped for another player"),
	TRAIL_PLAYER_STOP("/trail player <name> stop", "Pause all active particle effects for another player"),
	TRAIL_ACTIVATE("/trail start", "Start all particle effects previously stopped"),
	TRAIL_STOP("/trail stop", "Pause all active particle effects"),
	;

	private String line;
	private EntryType[] entryTypes;
	HelpEntry(String cmd, String desc, EntryType... entryTypes) {
		this.line = ChatColor.YELLOW + cmd + ChatColor.WHITE + "  |  " + ChatColor.GREEN + desc;
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