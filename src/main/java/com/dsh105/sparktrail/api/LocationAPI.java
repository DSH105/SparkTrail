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

package com.dsh105.sparktrail.api;

import com.dsh105.sparktrail.data.EffectManager;
import com.dsh105.sparktrail.trail.Effect;
import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.ParticleType;
import org.bukkit.Location;

import java.util.HashSet;


public class LocationAPI {

    public void addEffect(ParticleType particleType, Location location) {

    }

    public void removeEffect(ParticleType particleType, Location location) {

    }

    public HashSet<ParticleType> getEffects(Location location) {
        HashSet<ParticleType> list = new HashSet<ParticleType>();
        for (EffectHolder eh : EffectManager.getInstance().getEffectHolders()) {
            if (eh.getEffectType() == EffectHolder.EffectType.LOCATION && eh.isLocation(location)) {
                for (Effect e : eh.getEffects()) {
                    list.add(e.getParticleType());
                }
            }
        }
        return null;
    }

    public EffectHolder getEffectHolder(Location location) {
        for (EffectHolder eh : EffectManager.getInstance().getEffectHolders()) {
            if (eh.getEffectType() == EffectHolder.EffectType.LOCATION && eh.isLocation(location)) {
                return eh;
            }
        }
        return null;
    }
}