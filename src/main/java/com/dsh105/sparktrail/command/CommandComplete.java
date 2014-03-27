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