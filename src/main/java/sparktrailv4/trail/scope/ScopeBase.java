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

import sparktrailv4.api.Scope;
import sparktrailv4.trail.Trail;

public abstract class ScopeBase<T extends Trail> implements Scope<T> {

    private T registeredWith;

    protected void registerTo(T trail) {
        this.registeredWith = trail;
    }

    @Override
    public T getRegisteredWith() {
        return registeredWith;
    }

    @Override
    public void onRegister() {
        // Do nothing...yet
    }

    @Override
    public void onUnregister() {
        // Do nothing...yet...again
    }
}