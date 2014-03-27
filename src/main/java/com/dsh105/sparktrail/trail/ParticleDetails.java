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

package com.dsh105.sparktrail.trail;

import com.dsh105.sparktrail.trail.type.Critical;
import com.dsh105.sparktrail.trail.type.Potion;
import com.dsh105.sparktrail.trail.type.Smoke;
import com.dsh105.sparktrail.trail.type.Swirl;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;

import java.util.UUID;


public class ParticleDetails {

    private ParticleType particleType;
    private UUID uuid;
    private String playerName;

    public Integer blockId = 1;
    public Integer blockMeta = 0;
    public Critical.CriticalType criticalType = Critical.CriticalType.NORMAL;
    public FireworkEffect fireworkEffect = FireworkEffect.builder().withColor(Color.WHITE).with(FireworkEffect.Type.BALL).withFade(Color.WHITE).build();
    //public Note.NoteType noteType = Note.NoteType.GREEN;
    public Potion.PotionType potionType = Potion.PotionType.AQUA;
    public Smoke.SmokeType smokeType = Smoke.SmokeType.BLACK;
    public Swirl.SwirlType swirlType = Swirl.SwirlType.WHITE;
    public org.bukkit.Sound sound = Sound.ANVIL_BREAK;

    public Object[] o = new Object[]{};
    private boolean prepared = false;

    public ParticleDetails(ParticleType particleType) {
        this.particleType = particleType;
    }

    public ParticleDetails set(Object[] o) {
        this.o = o;
        this.prepared = true;
        return this;
    }

    public void setMob(UUID uuid) {
        this.uuid = uuid;
    }

    public void setPlayer(String name, UUID uuid) {
        this.uuid = uuid;
        this.playerName = name;
    }

    public ParticleType getParticleType() {
        return this.particleType;
    }

    public Object[] getDetails() {
        if (this.prepared) {
            return this.o;
        }

        if (particleType == ParticleType.BLOCKBREAK || particleType == ParticleType.ITEMSPRAY) {
            return new Object[]{this.blockId, this.blockMeta};
        } else if (particleType == ParticleType.CRITICAL) {
            return new Object[]{this.criticalType};
        } else if (particleType == ParticleType.FIREWORK) {
            return new Object[]{this.fireworkEffect};
        }
        /*else if (particleType == ParticleType.NOTE) {
            return new Object[] {this.noteType};
		}*/
        else if (particleType == ParticleType.POTION) {
            return new Object[]{this.potionType};
        } else if (particleType == ParticleType.SMOKE) {
            return new Object[]{this.smokeType};
        } else if (particleType == ParticleType.SWIRL) {
            return new Object[]{this.swirlType, this.uuid};
        } else if (particleType == ParticleType.SOUND) {
            return new Object[]{this.sound};
        }
        return o;
    }
}