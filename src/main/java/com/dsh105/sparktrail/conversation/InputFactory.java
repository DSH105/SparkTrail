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

package com.dsh105.sparktrail.conversation;

import com.dsh105.sparktrail.SparkTrailPlugin;
import com.dsh105.sparktrail.chat.WaitingData;
import com.dsh105.sparktrail.conversation.effect.EffectInputPrompt;
import com.dsh105.sparktrail.trail.ParticleType;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationFactory;

public class InputFactory {

    public static void prompt(Conversable forWhom, ParticleType particleType, WaitingData data) {
        buildFactory().withFirstPrompt(new EffectInputPrompt(particleType, data)).buildConversation(forWhom).begin();
    }

    public static void promptInt(Conversable forWhom, InputFunction function) {
        buildFactory().withFirstPrompt(new InputPrompt(function)).buildConversation(forWhom).begin();
    }

    private static ConversationFactory buildFactory() {
        return new ConversationFactory(SparkTrailPlugin.getInstance())
                .withModality(true)
                .withLocalEcho(true)
                .withPrefix(new InputConversationPrefix())
                .withTimeout(90)
                .withEscapeSequence("exit")
                .withEscapeSequence("quit");
    }
}