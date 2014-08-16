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

package sparktrailv4.trail;

import org.bukkit.entity.LivingEntity;

public class MobTrail extends EntityTrail<LivingEntity> {

    public MobTrail(LivingEntity entity) {
        super(entity);
    }

    public MobTrail(int tickInterval, LivingEntity entity) {
        super(tickInterval, entity);
    }

    @Override
    public void run() {
        if (entity == null || entity.isDead()) {
            cancel();
            return;
        }
        super.run();
    }

    @Override
    public boolean isPersistent() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EntityTrail that = (EntityTrail) o;

        return entity.equals(that.entity);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + entity.hashCode();
        return result;
    }
}