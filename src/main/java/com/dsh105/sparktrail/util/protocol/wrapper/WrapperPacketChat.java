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

package com.dsh105.sparktrail.util.protocol.wrapper;

import com.dsh105.sparktrail.SparkTrailPlugin;
import com.dsh105.sparktrail.util.ReflectionUtil;
import com.dsh105.sparktrail.util.protocol.Packet;
import com.dsh105.sparktrail.util.protocol.PacketFactory;
import com.dsh105.sparktrail.util.reflection.SafeMethod;

public class WrapperPacketChat extends Packet {

    public WrapperPacketChat() {
        super(PacketFactory.PacketType.CHAT);
    }

    public void setMessage(String chatComponent) {
        if (!SparkTrailPlugin.isUsingNetty) {
            if (!(chatComponent instanceof String)) {
                throw new IllegalArgumentException("Chat component for 1.6 chat packet must be a String!");
            }
        }
        this.write("a", new SafeMethod(ReflectionUtil.getNMSClass("ChatSerializer"), "a", String.class).invoke(null, chatComponent));
    }

    public String getMessage() {
        if (!SparkTrailPlugin.isUsingNetty) {
            return (String) this.read("message");
        }
        return (String) new SafeMethod(ReflectionUtil.getNMSClass("ChatSerializer"), "a", ReflectionUtil.getNMSClass("IChatBaseComponent")).invoke(null, this.read("a"));
    }
}