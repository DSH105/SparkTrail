package com.dsh105.sparktrail.command;

import com.dsh105.sparktrail.SparkTrailPlugin;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;


public class CommandComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> list = new ArrayList<String>();
        String cmdString = SparkTrailPlugin.getInstance().cmdString;
        if (cmd.getName().equalsIgnoreCase(cmdString)) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("sound")) {
                    for (Sound s : Sound.values()) {
                        if (s.toString().toLowerCase().startsWith(args[args.length - 1])) {
                            list.add(s.toString().toLowerCase());
                        }
                    }
                }
            }
        }
        return list;
    }
}