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

package sparktrailv4.listeners;

import com.dsh105.commodus.IdentUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import sparktrailv4.SparkTrail;
import sparktrailv4.api.Scope;
import sparktrailv4.trail.PlayerTrail;
import sparktrailv4.trail.scope.MovementScope;

import java.util.HashMap;

/**
 * Listens to the {@link org.bukkit.event.player.PlayerMoveEvent} to allow for movement-based trails
 * <p/>
 * This listener is dynamically registered and unregistered to improve overall plugin performance
 * <p/>
 * As a result, it only listens to the event when needed
 */
public class MovementListener implements Listener {

    private HashMap<String, Scope<PlayerTrail>> REGISTERED = new HashMap<>();

    private static MovementListener SINGLETON;

    public MovementListener() {
        SINGLETON = this;
    }

    public static void register(Player player, Scope<PlayerTrail> scope) {
        SINGLETON.registerTrailScope(player, scope);
    }

    public static void unregister(Player player) {
        SINGLETON.unregisterTrailScope(player);
    }

    public void registerTrailScope(Player player, Scope<PlayerTrail> scope) {
        for (Scope<PlayerTrail> existingScope : REGISTERED.values()) {
            if (existingScope.getRegisteredWith().equals(scope.getRegisteredWith())) {
                // Already registered - reduces the number of scopes we have to iterate through
                return;
            }
        }

        REGISTERED.put(IdentUtil.getIdentificationForAsString(player), scope);

        if (REGISTERED.size() == 1) {
            SparkTrail.getCore().getServer().getPluginManager().registerEvents(this, SparkTrail.getCore());
        }
    }

    public void unregisterTrailScope(Player player) {
        String ident = IdentUtil.getIdentificationForAsString(player);
        if (REGISTERED.containsKey(ident)) {
            REGISTERED.remove(ident);
            if (REGISTERED.isEmpty()) {
                HandlerList.unregisterAll(this);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        // If we enabled fetching here it would cause horrendous lag
        // Better off just saying no
        String ident = IdentUtil.getIdentificationForAsString(event.getPlayer(), false);
        if (ident != null) {
            Scope<PlayerTrail> scope = REGISTERED.get(ident);
            if (scope != null) {
                scope.getRegisteredWith().showExecutorsOfScope(MovementScope.class, event.getFrom(), false);
            }
            // else -> not supported
        }
    }
}