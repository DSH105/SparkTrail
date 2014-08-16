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

package sparktrailv4.trail.scope;

import com.dsh105.commodus.particle.ParticleBuilder;
import org.bukkit.Location;
import sparktrailv4.listeners.MovementListener;
import sparktrailv4.trail.PlayerTrail;
import sparktrailv4.trail.executor.TrailExecutor;

public class MovementScope extends ScopeBase<PlayerTrail> {

    @Override
    public void onRegister() {
        MovementListener.register(getRegisteredWith().getEntity(), this);
    }

    @Override
    public void onUnregister() {
        MovementListener.unregister(getRegisteredWith().getEntity());
    }

    @Override
    public void display(TrailExecutor executor, Location origin) {
        // Ignored
    }
}