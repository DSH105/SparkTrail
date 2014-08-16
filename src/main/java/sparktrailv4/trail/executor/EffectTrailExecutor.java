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

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import sparktrailv4.trail.TrailParticle;

public class EffectTrailExecutor extends TrailExecutor {

    private Effect effect;
    private int data;

    public EffectTrailExecutor(TrailParticle trail, Effect effect) {
        this(trail, effect, 0);
    }

    public EffectTrailExecutor(TrailParticle trail, Effect effect, int data) {
        super(trail, "", 0, 0);
        this.effect = effect;
        this.data =  data;
    }

    public EffectTrailExecutor withEffect(Effect effect) {
        this.effect = effect;
        return this;
    }

    public EffectTrailExecutor withData(int data) {
        this.data = data;
        return this;
    }

    public Effect getEffect() {
        return effect;
    }

    public int getData() {
        return data;
    }

    @Override
    public void show(Player player) {
        player.playEffect(getLocation(player.getWorld()), effect, data);
    }

    @Override
    public void show(Location origin) {
        origin.getWorld().playEffect(getLocation(origin.getWorld()), effect, data);
    }

    @Override
    public EffectTrailExecutor clone() throws CloneNotSupportedException {
        return (EffectTrailExecutor) super.clone();
    }
}