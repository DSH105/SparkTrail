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

/*
 * This file is part of HoloAPI.
 *
 * HoloAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HoloAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HoloAPI.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.dsh105.sparktrail.util;

import com.dsh105.dshutils.logger.ConsoleLogger;
import com.dsh105.dshutils.logger.Logger;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtil {

    public static String NMS_PATH = getNMSPackageName();
    public static String CBC_PATH = getCBCPackageName();

    public static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    public static String getNMSPackageName() {
        return "net.minecraft.server." + getServerVersion();
    }

    public static String getCBCPackageName() {
        return "org.bukkit.craftbukkit." + getServerVersion();
    }

    /**
     * Class stuff
     */

    public static Class getClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            ConsoleLogger.log(Logger.LogLevel.WARNING, "Could not find class: " + name + "!");
            return null;
        }
    }

    public static Class getNMSClass(String className) {
        return getClass(NMS_PATH + "." + className);
    }

    public static Class getCBCClass(String classPath) {
        return getClass(CBC_PATH + "." + classPath);
    }

    /**
     * Field stuff
     */

    public static Field getField(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);

            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            return field;
        } catch (NoSuchFieldException e) {
            ConsoleLogger.log(Logger.LogLevel.WARNING, "No such field: " + fieldName + "!");
            return null;
        }
    }

    public static <T> T getField(Class<?> clazz, String fieldName, Object instance) {
        try {
            return (T) getField(clazz, fieldName).get(instance);
        } catch (IllegalAccessException e) {
            ConsoleLogger.log(Logger.LogLevel.WARNING, "Failed to access field: " + fieldName + "!");
            return null;
        }
    }

    public static void setField(Class<?> clazz, String fieldName, Object instance, Object value) {
        try {
            getField(clazz, fieldName).set(instance, value);
        } catch (IllegalAccessException e) {
            ConsoleLogger.log(Logger.LogLevel.WARNING, "Could not set new field value for: " + fieldName);
        }
    }

    public static <T> T getField(Field field, Object instance) {
        try {
            return (T) field.get(instance);
        } catch (IllegalAccessException e) {
            ConsoleLogger.log(Logger.LogLevel.WARNING, "Failed to retrieve field: " + field.getName());
            return null;
        }
    }

    /**
     * Method stuff
     */

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... params) {
        try {
            return clazz.getDeclaredMethod(methodName, params);
        } catch (NoSuchMethodException e) {
            ConsoleLogger.log(Logger.LogLevel.WARNING, "No such method: " + methodName + "!");
            return null;
        }
    }

    public static <T> T invokeMethod(Method method, Object instance, Object... args) {
        try {
            return (T) method.invoke(instance, args);
        } catch (IllegalAccessException e) {
            ConsoleLogger.log(Logger.LogLevel.WARNING, "Failed to access method: " + method.getName() + "!");
            return null;
        } catch (InvocationTargetException e) {
            ConsoleLogger.log(Logger.LogLevel.WARNING, "Failed to invoke method: " + method.getName() + "!");
            return null;
        }
    }
}
