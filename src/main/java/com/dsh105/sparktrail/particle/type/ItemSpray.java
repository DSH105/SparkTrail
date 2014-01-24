package com.dsh105.sparktrail.particle.type;

import com.dsh105.sparktrail.SparkTrailPlugin;
import com.dsh105.sparktrail.particle.Effect;
import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.ParticleType;
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

    public ItemSpray(EffectHolder effectHolder, ParticleType particleType, int idValue, int metaValue) {
        super(effectHolder, particleType);
        this.idValue = idValue;
        this.metaValue = metaValue;
    }

    @Override
    public boolean play() {
        boolean shouldPlay = super.play();
        if (shouldPlay) {
            for (Location l : this.displayType.getLocations(new Location(this.getWorld(), this.getX(), this.getY(), this.getZ()))) {
                for (int i = 1; i <= 5; i++) {
                    Item item = this.getWorld().dropItemNaturally(l, new ItemStack(this.idValue, 1, (short) this.metaValue));
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
            Item item = this.getWorld().dropItemNaturally(p.getLocation(), new ItemStack(this.idValue, 1, (short) this.metaValue));
            item.setPickupDelay(Integer.MAX_VALUE);
            new ItemSprayRemoveTask(item);
        }
    }

    class ItemSprayRemoveTask extends BukkitRunnable {

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
            if (this.item != null && !this.item.isDead()) {
                this.item.remove();
            }
            TASKS.remove(this);
            UUID_LIST.remove(this.uuid);
        }
    }
}