package com.github.dsh105.sparktrail.command;

import com.github.dsh105.sparktrail.SparkTrail;
import com.github.dsh105.sparktrail.data.EffectHandler;
import com.github.dsh105.sparktrail.menu.ParticleMenu;
import com.github.dsh105.sparktrail.particle.Effect;
import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.ParticleDemo;
import com.github.dsh105.sparktrail.util.Lang;
import com.github.dsh105.sparktrail.util.Permission;
import com.github.dsh105.sparktrail.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

/**
 * Project by DSH105
 */

public class TrailCommand implements CommandExecutor {

	public String label;
	ChatColor c1 = SparkTrail.getInstance().primaryColour;
	ChatColor c2 = SparkTrail.getInstance().secondaryColour;

	public TrailCommand(String name) {
		this.label = name;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		boolean error = true;

		if (args.length == 0) {
			if (Permission.TRAIL.hasPerm(sender, true, false)) {
				Player p = (Player) sender;
				ParticleMenu pm = new ParticleMenu(p, p.getName());
				if (pm.fail) {
					Lang.sendTo(sender, Lang.MENU_ERROR.toString());
					return true;
				}
				pm.open(true);
				return true;
			} else error = false;
		}

		else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("help")) {
				if (Permission.TRAIL.hasPerm(sender, true, true)) {
					sender.sendMessage(c2 + "------------ SparkTrail Help 1/4 ------------");
					sender.sendMessage(c2 + "Parameters: <> = Required      [] = Optional");
					for (String s : HelpPage.getPage(1, true)) {
						sender.sendMessage(s);
					}
					return true;
				} else error = false;
			}

			if (args[0].equalsIgnoreCase("demo")) {
				if (Permission.DEMO.hasPerm(sender, true, false)) {
					Lang.sendTo(sender, Lang.DEMO_BEGIN.toString());
					ParticleDemo pd = new ParticleDemo((Player) sender);
					return true;
				} else error = false;
			}

			if (args[0].equalsIgnoreCase("info")) {
				if (Permission.INFO.hasPerm(sender, true, false)) {
					EffectHolder eh = EffectHandler.getInstance().getEffect(((Player) sender).getName());
					if (eh == null || eh.getEffects().isEmpty()) {
						Lang.sendTo(sender, Lang.NO_ACTIVE_EFFECTS.toString());
						return true;
					}
					sender.sendMessage(c2 + "------------ Trail Effects ------------");
					for (Effect e : eh.getEffects()) {
						if (e.getParticleType().requiresDataMenu()) {
							sender.sendMessage(c1 + StringUtil.capitalise(e.getParticleType().toString()) + ": " + c2 + e.getParticleData());
						}
						else {
							sender.sendMessage(c1 + StringUtil.capitalise(e.getParticleType().toString()));
						}
					}
					return true;
				} else error = false;
			}

			if (args[0].equalsIgnoreCase("start")) {

			}

			if (args[0].equalsIgnoreCase("stop")) {

			}

			if (args[0].equalsIgnoreCase("clear")) {

			}
		}

		else if (args.length == 2) {
			if (args[0].equals("help")) {
				if (Permission.TRAIL.hasPerm(sender, true, true)) {
					if (StringUtil.isInt(args[1])) {
						sender.sendMessage(c2 + "------------ SparkTrail Help " + args[1] + "/4 ------------");
						sender.sendMessage(c2 + "Parameters: <> = Required      [] = Optional");
						for (String s : HelpPage.getPage(Integer.parseInt(args[1]), true)) {
							sender.sendMessage(s);
						}
					}
					return true;
				} else error = false;
			}

			if (args[0].equalsIgnoreCase("player")) {
				Player target = Bukkit.getPlayer(args[1]);
				if (target == null) {
					Lang.sendTo(sender, Lang.NULL_PLAYER.toString().replace("%player%", args[1]));
					return true;
				}
				ParticleMenu pm = new ParticleMenu(((Player) sender), target.getName());
				if (pm.fail) {
					Lang.sendTo(sender, Lang.MENU_ERROR.toString());
					return true;
				}
				pm.open(false);
				Lang.sendTo(sender, Lang.ADMIN_OPEN_MENU.toString().replace("%player%", target.getName()));
				return true;
			}

			if (args[0].equalsIgnoreCase("location")) {

			}

			if (args[0].equalsIgnoreCase("mob")) {

			}
		}

		else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("player")) {
				if (args[2].equalsIgnoreCase("info")) {

				}

				if (args[2].equalsIgnoreCase("start")) {

				}

				if (args[2].equalsIgnoreCase("stop")) {

				}

				if (args[2].equalsIgnoreCase("clear")) {

				}
			}
		}

		if (error) {
			Lang.sendTo(sender, Lang.COMMAND_ERROR.toString()
					.replace("%cmd%", "/" + cmd.getLabel() + " " + (args.length == 0 ? "" : StringUtil.combineSplit(0, args, " "))));
		}
		return true;
	}
}