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

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import sparktrailv4.api.Scope;
import sparktrailv4.trail.executor.SwirlExecutor;
import sparktrailv4.trail.executor.TrailExecutor;
import sparktrailv4.trail.scope.SimpleScope;

import java.util.*;

public abstract class Trail extends BukkitRunnable implements Iterable<TrailExecutor> {

    private static HashMap<TrailParticle, Class<? extends Trail>> TYPE_LOCKS = new HashMap<>();

    static {
        for (TrailParticle trail : TrailParticle.getByParent(TrailParticle.SWIRL)) {
            TYPE_LOCKS.put(trail, EntityTrail.class);
        }
    }

    private final HashMap<TrailExecutor, Scope> EXECUTORS = new HashMap<>();
    private int tickInterval;

    public Trail() {
        this(20);
    }

    public Trail(int tickInterval) {
        this.tickInterval = tickInterval;
    }

    public AddResult add(TrailExecutor executor) {
        return add(executor, new SimpleScope());
    }

    public AddResult add(TrailExecutor executor, Scope scope) {
        if (getExecutor(executor.getTrail()) != null) {
            return AddResult.ALREADY_ACTIVE;
        }

        if (TYPE_LOCKS.containsKey(executor.getTrail())) {
            if (!TYPE_LOCKS.get(executor.getTrail()).isAssignableFrom(getClass())) {
                return AddResult.TYPE_LOCKED;
            }
        }

        EXECUTORS.put(executor, scope);
        return AddResult.SUCCESS;
    }

    public TrailExecutor getExecutor(TrailParticle particle) {
        for (TrailExecutor executor : getRegisteredExecutors().keySet()) {
            if (executor.getTrail() == particle) {
                return executor;
            }
        }
        return null;
    }

    public int getTickInterval() {
        return tickInterval;
    }

    public void setTickInterval(int tickInterval) {
        this.tickInterval = tickInterval;
    }

    public boolean isPersistent() {
        return true;
    }

    public Map<TrailExecutor, Scope> getRegisteredExecutors() {
        return Collections.unmodifiableMap(EXECUTORS);
    }

    public void showExecutorsOfScope(Class<? extends Scope> scope, Location origin) {
        showExecutorsOfScope(scope, origin, true);
    }

    public void showExecutorsOfScope(Class<? extends Scope> scope, Location origin, boolean useScopeDisplay) {
        for (TrailExecutor executor : getExecutorsWithScope(scope)) {
            if (useScopeDisplay) {
                getRegisteredExecutors().get(executor).display(executor.withLocation(origin.toVector()), origin);
            } else {
                // Only display at this location
                executor.withLocation(origin.toVector()).show(this, origin);
            }
        }
    }

    public void display(Location origin) {
        for (Map.Entry<TrailExecutor, Scope> entry : getRegisteredExecutors().entrySet()) {
            entry.getValue().display(entry.getKey().withLocation(origin.toVector()), origin.clone().add(0.5D, 0D, 0.5D));
        }
    }

    public List<TrailExecutor> getExecutorsWithScope(Scope scope) {
        return getExecutorsWithScope(scope.getClass());
    }

    public List<TrailExecutor> getExecutorsWithScope(Class<? extends Scope> scopeClass) {
        ArrayList<TrailExecutor> executors = new ArrayList<>();
        for (Map.Entry<TrailExecutor, Scope> entry : getRegisteredExecutors().entrySet()) {
            if (scopeClass.isAssignableFrom(entry.getValue().getClass())) {
                executors.add(entry.getKey());
            }
        }
        return Collections.unmodifiableList(executors);
    }

    protected abstract Location getDisplayLocation();

    @Override
    public void run() {
        display(getDisplayLocation());
    }

    public void end() {
        if (this instanceof EntityTrail) {
            for (TrailExecutor executor : this) {
                if (executor instanceof SwirlExecutor) {
                    ((SwirlExecutor) executor).reset((EntityTrail) this);
                }
            }
        }
    }

    @Override
    public Iterator<TrailExecutor> iterator() {
        return getRegisteredExecutors().keySet().iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trail that = (Trail) o;
        return tickInterval == that.tickInterval && EXECUTORS.equals(that.EXECUTORS);
    }

    @Override
    public int hashCode() {
        int result = EXECUTORS.hashCode();
        result = 31 * result + tickInterval;
        return result;
    }

    public static enum AddResult {

        SUCCESS, ALREADY_ACTIVE, TYPE_LOCKED;
    }
}