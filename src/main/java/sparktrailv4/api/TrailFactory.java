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

package sparktrailv4.api;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import sparktrailv4.trail.*;
import sparktrailv4.trail.executor.TrailExecutor;
import sparktrailv4.trail.scope.SimpleScope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrailFactory {

    private HashMap<TrailExecutor, Scope> executors = new HashMap<>();

    private Type type;
    private Location location; // for location effects
    private LivingEntity entity; // for entity effects
    private int tickInterval = 20;

    public TrailFactory atLocation(Location location) {
        Validate.notNull(location, "Trail location must not be null!");
        this.location = location;
        this.type = Type.LOCATION;
        return this;
    }

    public TrailFactory forEntity(LivingEntity entity) {
        Validate.notNull(entity, "Entity must not be null!");
        this.entity = entity;
        this.type = entity instanceof Player ? Type.PLAYER : Type.LOCATION;
        return this;
    }

    public TrailFactory withTickInterval(int tickInterval) {
        this.tickInterval = tickInterval;
        return this;
    }

    public TrailFactory withParticle(TrailParticle particle) {
        return withExecutor(particle.copyExecutor(), new SimpleScope());
    }

    public TrailFactory withExecutor(TrailExecutor trailExecutor) {
        return withExecutor(trailExecutor, new SimpleScope());
    }

    public TrailFactory withExecutor(TrailExecutor trailExecutor, Scope scope) {
        executors.put(trailExecutor, scope);
        return this;
    }

    public HashMap<TrailExecutor, Scope> getExecutors() {
        return executors;
    }

    public Type getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public int getTickInterval() {
        return tickInterval;
    }

    public FactoryBuild build() {
        Trail owningTrail = null;
        FactoryResult result = FactoryResult.SUCCESS;
        switch (this.type) {
            case LOCATION:
                owningTrail = new LocationTrail(getTickInterval(), getLocation());
                break;
            case MOB:
                owningTrail = new MobTrail(getTickInterval(), getEntity());
                break;
            case PLAYER:
                owningTrail = new PlayerTrail(getTickInterval(), (Player) getEntity());
                break;
        }

        for (Map.Entry<TrailExecutor, Scope> entry : getExecutors().entrySet()) {
            switch (owningTrail.add(entry.getKey(), entry.getValue())) {
                case SUCCESS:
                    // Do nothing
                    break;
                default:
                    result = FactoryResult.MIXED_SUCCESS;
                    break;
            }
        }

        if (owningTrail.getRegisteredExecutors().isEmpty() != executors.isEmpty()) {
            result = FactoryResult.FAILURE;
        }

        return new FactoryBuild(owningTrail, type, result);
    }

    public class FactoryBuild {

        private Trail trail;
        private Type type;
        private FactoryResult result;

        protected FactoryBuild(Trail trail, Type type, FactoryResult result) {
            this.trail = trail;
            this.type = type;
            this.result = result;
        }

        public Trail getTrail() {
            return trail;
        }

        public Type getType() {
            return type;
        }

        public FactoryResult getResult() {
            return result;
        }

    }

    public enum FactoryResult {
        SUCCESS, MIXED_SUCCESS, FAILURE
    }

    public enum Type {
        LOCATION, MOB, PLAYER
    }
}