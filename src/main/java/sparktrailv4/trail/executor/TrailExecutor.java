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
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import sparktrailv4.trail.Trail;
import sparktrailv4.trail.TrailParticle;

public class TrailExecutor extends ParticleBuilder {

    private TrailParticle trail;

    public TrailExecutor(TrailParticle trail, String name, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float speed, int amount) {
        super(name, x, y, z, offsetX, offsetY, offsetZ, speed, amount);
        this.trail = trail;
    }

    public TrailExecutor(TrailParticle trail, String name, float x, float y, float z, float speed, int amount) {
        super(name, x, y, z, speed, amount);
        this.trail = trail;
    }

    public TrailExecutor(TrailParticle trail, String name, float speed, int amount) {
        super(name, speed, amount);
        this.trail = trail;
    }

    public static TrailExecutor build(ParticleBuilder builder, TrailParticle trail) {
        return new TrailExecutor(trail, builder.getName(), builder.getX(), builder.getY(), builder.getZ(), builder.getOffsetX(), builder.getOffsetY(), builder.getOffsetZ(), builder.getSpeed(), builder.getAmount());
    }

    public TrailExecutor withTrail(TrailParticle trail) {
        this.trail = trail;
        return this;
    }

    public TrailParticle getTrail() {
        return trail;
    }

    public void show(Trail owner, Player player) {
        super.show(player);
    }

    public void show(Trail owner, Location origin) {
        super.show(origin);
    }

    @Override
    public TrailExecutor withLocation(Vector vector) {
        return (TrailExecutor) super.withLocation(vector);
    }

    @Override
    public TrailExecutor clone() throws CloneNotSupportedException {
        return (TrailExecutor) super.clone();
    }
}