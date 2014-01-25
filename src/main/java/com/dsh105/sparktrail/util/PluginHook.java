package com.dsh105.sparktrail.util;

import com.dsh105.sparktrail.SparkTrailPlugin;
import org.bukkit.plugin.Plugin;
import org.kitteh.vanish.VanishPlugin;

public class PluginHook {

    public static VanishPlugin getVNP() {
        Plugin plugin = SparkTrailPlugin.getInstance().getServer().getPluginManager().getPlugin("VanishNoPacket");
        if (plugin == null || !(plugin instanceof VanishPlugin)) {
            return null;
        }
        return (VanishPlugin) plugin;
    }
}