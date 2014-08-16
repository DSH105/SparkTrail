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

package sparktrailv4.trail.executor;

import com.dsh105.commodus.particle.ParticleBuilder;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import sparktrailv4.trail.TrailParticle;

public class SoundExecutor extends TrailExecutor {

    private Sound sound;

    public SoundExecutor() {
        super(TrailParticle.SOUND, "", 0, 0);
    }

    public SoundExecutor(Sound sound) {
        this();
        this.sound = sound;
    }

    public SoundExecutor withSound(Sound sound) {
        this.sound = sound;
        return this;
    }

    public Sound getSound() {
        return sound;
    }

    @Override
    public void show(Player player) {
        player.playSound(getLocation(player.getWorld()), getSound(), 10F, 1F);
    }

    @Override
    public void show(Location origin) {
        origin.getWorld().playSound(getLocation(origin.getWorld()), getSound(), 10F, 1F);
    }

    @Override
    public SoundExecutor clone() throws CloneNotSupportedException {
        return (SoundExecutor) super.clone();
    }
}