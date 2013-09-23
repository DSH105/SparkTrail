package com.github.dsh105.sparktrail.data;

import com.github.dsh105.sparktrail.SparkTrail;
import com.github.dsh105.sparktrail.particle.EffectHolder;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Project by DSH105
 */

public class AutoSave {

	public AutoSave(int timer) {
		new BukkitRunnable() {
			public void run() {
				SparkTrail plugin = SparkTrail.getInstance();
				for (EffectHolder e : plugin.EH.getEffects()) {
					plugin.EH.save("autosave", e);
					plugin.SQLH.save(e, false);
				}
			}
		}.runTaskTimer(SparkTrail.getInstance(), (20*timer)/2, 20*timer);
	}
}