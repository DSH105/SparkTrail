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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class SafeMethod<T> implements MethodAccessor<T> {

    private Method method;
    private Class[] params;
    private boolean isStatic;

    public SafeMethod() {
    }

    public SafeMethod(Method method) {
        setMethod(method);
    }

    public SafeMethod(Class<?> coreClass, String methodname, Class<?>... params) {
        try {
            Method method = coreClass.getDeclaredMethod(methodname, params);
            setMethod(method);
        } catch (NoSuchMethodException e) {
            ConsoleLogger.log(Logger.LogLevel.WARNING, "Failed to find a matching method with name: " + methodname);
            e.printStackTrace();
        }
    }

    protected void setMethod(Method method) {
        if (method == null) {
            ConsoleLogger.log(Logger.LogLevel.WARNING, "Cannot create a SafeMethod with a null method!");
        }
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        this.method = method;
        this.params = method.getParameterTypes();
        this.isStatic = Modifier.isStatic(method.getModifiers());
    }

    @Override
    public T invoke(Object instance, Object... args) {
        if (this.method != null) {

            //check if the instance is right
            if (instance == null && !isStatic) {
                throw new UnsupportedOperationException("Non-static methods require a valid instance passed in!");
            }

            //check if param lenght is right
            if (args.length != this.params.length) {
                throw new UnsupportedOperationException("Not enough arguments!");
            }

            //if we got trough the above stuff then eventually invoke the method
            try {
                return (T) this.method.invoke(instance, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
