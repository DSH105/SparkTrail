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

import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.ParticleType;
import org.bukkit.entity.Entity;

import java.util.HashSet;
import java.util.UUID;


public class MobAPI {

    public void addEffect(ParticleType particleType, UUID uuid) {

    }

    public void removeEffect(ParticleType particleType, UUID uuid) {

    }

    public HashSet<ParticleType> getEffects(UUID uuid) {
        return null;
    }

    public EffectHolder getEffectHolder(UUID uuid) {
        return null;
    }

    public EffectHolder getEffectHolder(Entity entity) {
        return null;
    }
}