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

import com.captainbern.minecraft.conversion.BukkitUnwrapper;
import com.captainbern.minecraft.protocol.PacketType;
import com.captainbern.minecraft.reflection.MinecraftMethods;
import com.captainbern.minecraft.wrapper.WrappedDataWatcher;
import com.captainbern.minecraft.wrapper.WrappedPacket;
import com.dsh105.commodus.GeometryUtil;
import com.dsh105.commodus.ServerUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import sparktrailv4.trail.EntityTrail;
import sparktrailv4.trail.Trail;
import sparktrailv4.trail.TrailParticle;

/**
 * TODO: This stuff isn't really that safe. Once 1.8 hits home this can be updated to use the new particle colour technique
 */
public class SwirlExecutor extends TrailExecutor {

    private WrappedDataWatcher dataWatcher = new WrappedDataWatcher();

    private int data;

    public SwirlExecutor(TrailParticle trail, int data) {
        super(trail, "", 0, 0);
        this.data = data;
    }

    public SwirlExecutor withData(int data) {
        this.data = data;
        return this;
    }

    public int getData() {
        return data;
    }

    @Override
    public void show(Trail owner, Player player) {
        dataWatcher.setObject(7, getData());
        WrappedPacket meta = new WrappedPacket(PacketType.Play.Server.ENTITY_METADATA);
        meta.getIntegers().write(0, BukkitUnwrapper.getInstance().unwrap(((EntityTrail) owner).getEntity()).hashCode());
        meta.getWatchableObjectLists().write(0, dataWatcher.getWatchableObjects());
        MinecraftMethods.sendPacket(player, meta.getHandle());
    }

    @Override
    public void show(Trail owner, Location origin) {
        dataWatcher.setObject(7, getData());
        WrappedPacket meta = new WrappedPacket(PacketType.Play.Server.ENTITY_METADATA);
        meta.getIntegers().write(0, BukkitUnwrapper.getInstance().unwrap(((EntityTrail) owner).getEntity()).hashCode());
        meta.getWatchableObjectLists().write(0, dataWatcher.getWatchableObjects());
        for (Player player : GeometryUtil.getNearbyPlayers(origin, -1)) {
            MinecraftMethods.sendPacket(player, meta.getHandle());
        }
    }

    public void reset(EntityTrail owner) {
        dataWatcher.setObject(0, getData());
        WrappedPacket meta = new WrappedPacket(PacketType.Play.Server.ENTITY_METADATA);
        meta.getIntegers().write(0, BukkitUnwrapper.getInstance().unwrap(owner.getEntity()).hashCode());
        meta.getWatchableObjectLists().write(0, dataWatcher.getWatchableObjects());
        for (Player player : ServerUtil.getOnlinePlayers()) { // reset for all
            MinecraftMethods.sendPacket(player, meta.getHandle());
        }
    }

    @Override
    public SwirlExecutor clone() throws CloneNotSupportedException {
        return (SwirlExecutor) super.clone();
    }
}