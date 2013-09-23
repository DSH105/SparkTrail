package com.github.dsh105.sparktrail.command;

import com.github.dsh105.sparktrail.util.Lang;
import com.github.dsh105.sparktrail.util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Project by DSH105
 */

public class TrailCommand implements CommandExecutor {

	public String label;

	public TrailCommand(String name) {
		this.label = name;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		boolean error = true;

		if (error) {
			// Something went wrong. Maybe the player didn't use a command correctly?
			// Send them a message with the exact command to make sure
			if (!HelpPage.sendRelevantHelpMessage(sender, args)) {
				Lang.sendTo(sender, Lang.COMMAND_ERROR.toString()
						.replace("%cmd%", "/" + cmd.getLabel() + " " + (args.length == 0 ? "" : StringUtil.combineSplit(0, args, " "))));
			}
		}
		return true;
	}
}