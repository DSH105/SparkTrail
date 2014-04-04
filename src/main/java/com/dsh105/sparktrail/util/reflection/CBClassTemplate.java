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
 * This file is part of EchoPet.
 *
 * EchoPet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EchoPet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EchoPet.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.dsh105.sparktrail.util.reflection;

import com.dsh105.dshutils.logger.ConsoleLogger;
import com.dsh105.dshutils.logger.Logger;
import com.dsh105.sparktrail.util.ReflectionUtil;

public class CBClassTemplate extends ClassTemplate<Object> {

    public CBClassTemplate() {
        setCBClass(getClass().getSimpleName());
    }

    public CBClassTemplate(String className) {
        setCBClass(className);
    }

    protected void setCBClass(String name) {
        Class clazz = ReflectionUtil.getCBCClass(name);
        if (clazz == null) {
            ConsoleLogger.log(Logger.LogLevel.WARNING, "Failed to find a matching class with name: " + name);
        }
        setClass(clazz);
    }

    public static CBClassTemplate create(String className) {
        return new CBClassTemplate(className);
    }
}
