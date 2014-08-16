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

package sparktrailv4.inventory;

import com.dsh105.commodus.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sparktrailv4.trail.TrailParticle;

import java.util.ArrayList;
import java.util.Arrays;

public enum MenuPreset {

    ;

    public static ItemStack SELECTOR_PRESET;

    static {
        SELECTOR_PRESET = new org.bukkit.inventory.ItemStack(Material.BONE);
        ItemMeta meta = SELECTOR_PRESET.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Pets");
        SELECTOR_PRESET.setItemMeta(meta);
    }

    private Material material;
    private String name;
    private String command;
    private int amount;
    private short materialData;
    private MenuType menuType;
    private TrailParticle particle;
    private TrailParticle.Type particleType;

    MenuPreset(Material material, MenuType menuType, TrailParticle particle) {
        init(material, StringUtil.capitalise(toString().replace("_", " ")), null, 0, menuType, PetData.Type.BOOLEAN, petData);
    }

    MenuPreset(Material material, int materialData, MenuType menuType, PetData petData) {
        init(material, StringUtil.capitalise(toString().replace("_", " ")), null, materialData, menuType, PetData.Type.BOOLEAN, petData);
    }

    MenuPreset(Material material, int amount, int materialData, MenuType menuType, PetData petData) {
        init(material, StringUtil.capitalise(toString().replace("_", " ")), null, amount, materialData, menuType, PetData.Type.BOOLEAN, petData);
    }

    MenuPreset(Material material, MenuType menuType, PetData.Type dataType) {
        init(material, StringUtil.capitalise(toString().replace("_", " ")), null, 0, menuType, dataType, null);
    }

    MenuPreset(Material material, String name, String command, MenuType menuType) {
        init(material, name, command, 0, menuType, null, null);
    }

    private void init(Material material, String name, String command, int materialData, MenuType menuType, PetData.Type dataType, PetData petData) {
        init(material, name, command, 1, materialData, menuType, dataType, petData);
    }

    private void init(Material material, String name, String command, int amount, int materialData, MenuType menuType, TrailParticle.Type dataType, TrailParticle particle) {
        this.material = material;
        this.name = name;
        this.command = "pet " + command;
        this.amount = amount;
        this.materialData = (short) materialData;
        this.menuType = menuType;
        this.dataType = dataType;
        this.petData = petData;
    }

    public String getCommand() {
        return command;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    public short getMaterialData() {
        return materialData;
    }

    public String getName() {
        return name;
    }

    public PetData.Type getDataType() {
        return dataType;
    }

    public MenuType getMenuType() {
        return menuType;
    }

    public PetData getPetData() {
        return petData;
    }

    public static ArrayList<MenuPreset> getPresetsOfType(PetData.Type toCompare) {
        ArrayList<MenuPreset> presets = new ArrayList<>();
        for (MenuPreset preset : MenuPreset.values()) {
            if ((preset.getDataType() != null && preset.getDataType().equals(toCompare)) || preset.getPetData().isType(toCompare)) {
                presets.add(preset);
            }
        }
        return presets;
    }

    public static ArrayList<MenuPreset> getPresetsOfType(MenuType toCompare) {
        ArrayList<MenuPreset> presets = new ArrayList<>();
        for (MenuPreset preset : MenuPreset.values()) {
            if (preset.getMenuType().equals(toCompare)) {
                presets.add(preset);
            }
        }
        return presets;
    }

    public static ArrayList<MenuPreset> getPresets(PetType petType) {
        ArrayList<MenuPreset> presets = new ArrayList<>(Arrays.asList(RIDE, HAT));

        ArrayList<PetData> registeredData = AttributeAccessor.getRegisteredData(petType);
        ArrayList<MenuPreset> dataPresets = getPresetsOfType(MenuType.DATA);
        for (MenuPreset preset : dataPresets) {
            if (presets.contains(preset)) {
                continue;
            }
            for (PetData data : registeredData) {
                if (data == preset.getPetData() || data.isType(preset.getDataType())) {
                    presets.add(preset);
                }
            }
        }
        return presets;
    }

    public enum MenuType {
        SELECTOR, DATA, DATA_SECOND_LEVEL
    }
}