package com.github.dsh105.sparktrail.util;

import com.github.dsh105.sparktrail.entity.EntityOrb;
import com.github.dsh105.sparktrail.logger.Logger;
import net.minecraft.server.v1_6_R3.Entity;
import net.minecraft.server.v1_6_R3.EntityTypes;
import net.minecraft.server.v1_6_R3.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_6_R3.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

/**
 * Project by DSH105
 */

public class EntityUtil {

	public static void registerEntity(Class<? extends Entity> clazz, String name, int id) {
		Field field_c = null;
		try {
			field_c = EntityTypes.class.getDeclaredField("c");
			Field field_e = EntityTypes.class.getDeclaredField("e");
			field_c.setAccessible(true);
			field_e.setAccessible(true);

			Map<Class, String> c = (Map) field_c.get(field_c);
			Map<Class, Integer> e = (Map) field_e.get(field_e);

			Iterator i = c.keySet().iterator();
			while (i.hasNext()) {
				Class cl = (Class) i.next();
				if (cl.getCanonicalName().equals(clazz.getCanonicalName())) {
					i.remove();
				}
			}

			Iterator i2 = e.keySet().iterator();
			while (i.hasNext()) {
				Class cl = (Class) i.next();
				if (cl.getCanonicalName().equals(clazz.getCanonicalName())) {
					i.remove();
				}
			}

			c.put(clazz, name);
			e.put(clazz, id);

		} catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Registration of Block Surge Falling Block Entity [" + name + "] has failed.", e, true);
		}
	}

	public static EntityOrb spawnOrb(Location l, int value) throws IllegalArgumentException {
		World mcWorld = ((CraftWorld) l.getWorld()).getHandle();
		double x = l.getX() + 0.5,
				y = l.getY() + 1.5,
				z = l.getZ() + 0.5;
		EntityOrb orb = new EntityOrb(mcWorld, x, y, z, value);
		if (!l.getChunk().isLoaded()) {
			l.getChunk().load();
		}
		if (!mcWorld.addEntity(orb, CreatureSpawnEvent.SpawnReason.CUSTOM)) {
			Logger.log(Logger.LogLevel.SEVERE, "Failed to spawn EntitySurgeBlock at Location[" + l.getWorld() + "," + l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ() + "].", false);
		}
		return orb;
	}
}