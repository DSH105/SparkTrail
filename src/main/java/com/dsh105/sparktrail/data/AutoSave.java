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

package com.dsh105.sparktrail.data;

import com.dsh105.sparktrail.SparkTrailPlugin;
import com.dsh105.sparktrail.trail.EffectHolder;
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