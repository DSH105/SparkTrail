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

package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.SparkTrailPlugin;
import com.dsh105.sparktrail.trail.Effect;
import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.ParticleType;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class ItemSpray extends Effect {

    public static ArrayList<ItemSprayRemoveTask> TASKS = new ArrayList<ItemSprayRemoveTask>();
    public static ArrayList<UUID> UUID_LIST = new ArrayList<UUID>();

    public int idValue;
    public int metaValue;

    public ItemSpray(EffectHolder effectHolder, int idValue, int metaValue) {
        super(effectHolder, ParticleType.ITEMSPRAY);
        this.idValue = idValue;
        this.metaValue = metaValue;
    }

    @Override
    public boolean play() {
        boolean shouldPlay = super.play();
        if (shouldPlay) {
            for (Location l : this.displayType.getLocations(this.getHolder().getEffectPlayLocation())) {
                for (int i = 1; i <= 5; i++) {
                    Item item = this.getWorld().dropItemNaturally(l, new ItemStack(this.idValue, 1, (short) this.metaValue));
                    item.setVelocity(item.getVelocity().normalize().multiply(0.4F));
                    item.setPickupDelay(Integer.MAX_VALUE);
                    new ItemSprayRemoveTask(item);
                }
            }
        }
        return shouldPlay;
    }

    @Override
    public void playDemo(Player p) {
        for (int i = 1; i <= 5; i++) {
            Item item = this.getWorld().dropItemNaturally(new Location(p.getWorld(), p.getLocation().getX() + 0.5D, p.getLocation().getY(), p.getLocation().getZ() + 0.5D), new ItemStack(this.idValue, 1, (short) this.metaValue));
            item.setPickupDelay(Integer.MAX_VALUE);
            new ItemSprayRemoveTask(item);
        }
    }

    public class ItemSprayRemoveTask extends BukkitRunnable {

        private Item item;
        private UUID uuid;

        ItemSprayRemoveTask(Item item) {
            this.item = item;
            this.uuid = item.getUniqueId();
            TASKS.add(this);
            UUID_LIST.add(this.uuid);
            this.runTaskLater(SparkTrailPlugin.getInstance(), 50L);
        }

        @Override
        public void run() {
            this.executeFinish(true);
        }

        public void executeFinish(boolean removeFromLists) {
            if (this.item != null && !this.item.isDead()) {
                this.item.remove();
            }
            if (removeFromLists) {
                TASKS.remove(this);
                UUID_LIST.remove(this.uuid);
            }
        }

        @Override
        public synchronized void cancel() throws IllegalStateException {
            super.cancel();
        }
    }
}