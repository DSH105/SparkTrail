package io.github.dsh105.sparktrail.menu;

import io.github.dsh105.sparktrail.particle.ParticleType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;


public enum ParticleDataItem {

    AQUA(Material.WOOL, (short) 3, "Aqua", ParticleType.POTION), //light blue wool
    BLACK(Material.WOOL, (short) 15, "Black", ParticleType.SMOKE, ParticleType.POTION, ParticleType.SWIRL), //black wool
    BLUE(Material.getMaterial(351), (short) 4, "Blue", ParticleType.POTION, ParticleType.SWIRL), //lapis lazuli
    CRIMSON(Material.getMaterial(351), (short) 1, "Crimson", ParticleType.POTION), //rose red dye
    DARKBLUE(Material.WOOL, (short) 11, "Dark Blue", ParticleType.POTION, ParticleType.SWIRL), //blue wool
    DARKGREEN(Material.WOOL, (short) 13, "Dark Green", ParticleType.POTION, ParticleType.SWIRL), //green wool
    DARKRED(Material.STAINED_CLAY, (short) 14, "Dark Red", ParticleType.POTION, ParticleType.SWIRL), //red stained clay
    GOLD(Material.STAINED_CLAY, (short) 4, "Gold", ParticleType.POTION), //yellow stained clay
    GRAY(Material.WOOL, (short) 7, "Gray", ParticleType.POTION, ParticleType.SWIRL), //gray wool
    GREEN(Material.WOOL, (short) 5, "Green", ParticleType.POTION, ParticleType.SWIRL), //lime wool
    LIGHTBLUE(Material.WOOL, (short) 3, "Light Blue", ParticleType.SWIRL), //light blue wool
    MAGIC(Material.DIAMOND_SWORD, (short) 0, "Magic Critical", ParticleType.CRITICAL),
    NORMAL(Material.IRON_SWORD, (short) 0, "Critical", ParticleType.CRITICAL),
    ORANGE(Material.WOOL, (short) 1, "Orange", ParticleType.SWIRL), //orange wool
    PINK(Material.WOOL, (short) 6, "Pink", ParticleType.POTION, ParticleType.SWIRL), //pink wool
    PURPLE(Material.WOOL, (short) 10, "Purple", ParticleType.SWIRL), //purple wool
    RAINBOW(Material.FIREWORK, (short) 0, "Rainbow", ParticleType.SMOKE), //firework
    RED(Material.WOOL, (short) 14, "Red", ParticleType.SMOKE, ParticleType.POTION, ParticleType.SWIRL), //red wool
    YELLOW(Material.WOOL, (short) 4, "Yellow", ParticleType.SWIRL), //yellow wool
    WHITE(Material.WOOL, (short) 0, "White", ParticleType.SWIRL); //white wool

    private Material material;
    private short data;
    private String name;
    private ArrayList<ParticleType> particleTypes = new ArrayList<ParticleType>();

    ParticleDataItem(Material material, short data, String name, ParticleType... particleType) {
        this.material = material;
        this.data = data;
        for (ParticleType pt : particleType) {
            this.particleTypes.add(pt);
        }
        this.name = name;
    }

    public ArrayList<ParticleType> getParticleTypes() {
        return this.particleTypes;
    }

    public boolean isType(ParticleType particleType) {
        return this.particleTypes.contains(particleType);
    }

    public ItemStack getMenuItem() {
        ItemStack i = new ItemStack(this.material, 1, this.data);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + this.name);
        i.setItemMeta(meta);
        return i;
    }

    public ItemStack getMenuItem(boolean nameFlag) {
        ItemStack i = new ItemStack(this.material, 1, this.data);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + this.name + (nameFlag ? ChatColor.GREEN + " [TOGGLE ON]" : ChatColor.RED + " [TOGGLE OFF]"));
        i.setItemMeta(meta);
        return i;
    }
}