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

package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.PacketEffect;
import com.dsh105.sparktrail.trail.ParticleType;


public class Critical extends PacketEffect {

    public CriticalType criticalType;

    public Critical(EffectHolder effectHolder, CriticalType criticalType) {
        super(effectHolder, ParticleType.CRITICAL);
        this.criticalType = criticalType;
    }

    @Override
    public String getNmsName() {
        return this.criticalType.getNmsName();
    }

    @Override
    public float getSpeed() {
        return 0.1F;
    }

    @Override
    public int getParticleAmount() {
        return 50;
    }

    public enum CriticalType {
        NORMAL("crit"),
        MAGIC("magicCrit");

        private String nmsName;

        CriticalType(String nmsName) {
            this.nmsName = nmsName;
        }

        public String getNmsName() {
            return nmsName;
        }
    }
}