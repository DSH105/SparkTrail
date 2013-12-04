package io.github.dsh105.sparktrail.command;

import io.github.dsh105.sparktrail.SparkTrail;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;


public class CommandComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> list = new ArrayList<String>();
        String cmdString = SparkTrail.getInstance().cmdString;
        if (cmd.getName().equalsIgnoreCase(cmdString)) {

        }
        return list;
    }
}