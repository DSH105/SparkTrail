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


public class Smoke extends PacketEffect {

    public SmokeType smokeType;

    public Smoke(EffectHolder effectHolder, SmokeType smokeType) {
        super(effectHolder, ParticleType.SMOKE);
        this.smokeType = smokeType;
    }

    @Override
    public String getNmsName() {
        return this.smokeType.getNmsName();
    }

    @Override
    public float getSpeed() {
        return this.smokeType.getSpeed();
    }

    @Override
    public int getParticleAmount() {
        return this.smokeType.getAmount();
    }

    public enum SmokeType {
        BLACK("largesmoke", 0.2F, 50),
        RED("reddust", 0F, 100),
        RAINBOW("reddust", 1F, 100);

        private String nmsName;
        private float speed;
        private int amount;

        SmokeType(String nmsName, float speed, int amount) {
            this.nmsName = nmsName;
            this.speed = speed;
            this.amount = amount;
        }

        public String getNmsName() {
            return this.nmsName;
        }

        public float getSpeed() {
            return this.speed;
        }

        public int getAmount() {
            return this.amount;
        }
    }
}