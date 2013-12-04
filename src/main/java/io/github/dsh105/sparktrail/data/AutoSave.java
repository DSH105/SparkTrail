package io.github.dsh105.sparktrail.data;

import io.github.dsh105.sparktrail.SparkTrail;
import io.github.dsh105.sparktrail.particle.EffectHolder;
import org.bukkit.scheduler.BukkitRunnable;


public class AutoSave {

    public AutoSave(int timer) {
        new BukkitRunnable() {
            public void run() {
                SparkTrail plugin = SparkTrail.getInstance();
                for (EffectHolder e : plugin.EH.getEffectHolders()) {
                    plugin.EH.save(e);
                    plugin.SQLH.save(e);
                }
            }
        }.runTaskTimer(SparkTrail.getInstance(), (20 * timer) / 2, 20 * timer);
    }
}