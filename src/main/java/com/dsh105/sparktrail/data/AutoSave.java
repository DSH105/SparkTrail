package com.dsh105.sparktrail.data;

import com.dsh105.sparktrail.SparkTrailPlugin;
import com.dsh105.sparktrail.particle.EffectHolder;
import org.bukkit.scheduler.BukkitRunnable;


public class AutoSave {

    public AutoSave(int timer) {
        new BukkitRunnable() {
            public void run() {
                SparkTrailPlugin plugin = SparkTrailPlugin.getInstance();
                for (EffectHolder e : plugin.EH.getEffectHolders()) {
                    plugin.EH.save(e);
                    plugin.SQLH.save(e);
                }
            }
        }.runTaskTimer(SparkTrailPlugin.getInstance(), (20 * timer) / 2, 20 * timer);
    }
}