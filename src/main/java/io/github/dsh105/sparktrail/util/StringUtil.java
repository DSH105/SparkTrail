package io.github.dsh105.sparktrail.util;

import org.bukkit.ChatColor;


public class StringUtil {

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String capitalise(String s) {
        String finalString = "";
        if (s.contains(" ")) {
            StringBuilder builder = new StringBuilder();
            String[] sp = s.split(" ");
            for (String string : sp) {
                string = string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
                builder.append(string);
                builder.append(" ");
            }
            builder.deleteCharAt(builder.length() - 1);
            finalString = builder.toString();
        } else {
            finalString = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        }
        return finalString;
    }

    public static String combineSplit(int startIndex, String[] string, String separator) {
        if (string == null) {
            return "";
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = startIndex; i < string.length; i++) {
                builder.append(string[i]);
                builder.append(separator);
            }
            builder.deleteCharAt(builder.length() - separator.length());
            return builder.toString();
        }
    }

    public static String replaceStringWithColours(String string) {
        string = string.replace("&0", ChatColor.BLACK.toString());
        string = string.replace("&1", ChatColor.DARK_BLUE.toString());
        string = string.replace("&2", ChatColor.DARK_GREEN.toString());
        string = string.replace("&3", ChatColor.DARK_AQUA.toString());
        string = string.replace("&4", ChatColor.DARK_RED.toString());
        string = string.replace("&5", ChatColor.DARK_PURPLE.toString());
        string = string.replace("&6", ChatColor.GOLD.toString());
        string = string.replace("&7", ChatColor.GRAY.toString());
        string = string.replace("&8", ChatColor.DARK_GRAY.toString());
        string = string.replace("&9", ChatColor.BLUE.toString());
        string = string.replace("&a", ChatColor.GREEN.toString());
        string = string.replace("&b", ChatColor.AQUA.toString());
        string = string.replace("&c", ChatColor.RED.toString());
        string = string.replace("&d", ChatColor.LIGHT_PURPLE.toString());
        string = string.replace("&e", ChatColor.YELLOW.toString());
        string = string.replace("&f", ChatColor.WHITE.toString());

        string = string.replace("&k", ChatColor.MAGIC.toString());
        string = string.replace("&l", ChatColor.BOLD.toString());
        string = string.replace("&m", ChatColor.STRIKETHROUGH.toString());
        string = string.replace("&n", ChatColor.UNDERLINE.toString());
        string = string.replace("&o", ChatColor.ITALIC.toString());
        string = string.replace("&r", ChatColor.RESET.toString());
        return string;
    }

    public static String replaceColoursWithString(String s) {
        s = s.replace(ChatColor.BLACK + "", "&0");
        s = s.replace(ChatColor.DARK_BLUE + "", "&1");
        s = s.replace(ChatColor.DARK_GREEN + "", "&2");
        s = s.replace(ChatColor.DARK_AQUA + "", "&3");
        s = s.replace(ChatColor.DARK_RED + "", "&4");
        s = s.replace(ChatColor.DARK_PURPLE + "", "&5");
        s = s.replace(ChatColor.GOLD + "", "&6");
        s = s.replace(ChatColor.GRAY + "", "&7");
        s = s.replace(ChatColor.DARK_GRAY + "", "&8");
        s = s.replace(ChatColor.BLUE + "", "&9");
        s = s.replace(ChatColor.GREEN + "", "&a");
        s = s.replace(ChatColor.AQUA + "", "&b");
        s = s.replace(ChatColor.RED + "", "&c");
        s = s.replace(ChatColor.LIGHT_PURPLE + "", "&d");
        s = s.replace(ChatColor.YELLOW + "", "&e");
        s = s.replace(ChatColor.WHITE + "", "&f");

        s = s.replace(ChatColor.MAGIC + "", "&k");
        s = s.replace(ChatColor.BOLD + "", "&l");
        s = s.replace(ChatColor.STRIKETHROUGH + "", "&m");
        s = s.replace(ChatColor.UNDERLINE + "", "&n");
        s = s.replace(ChatColor.ITALIC + "", "&o");
        s = s.replace(ChatColor.RESET + "", "&r");
        return s;
    }
}