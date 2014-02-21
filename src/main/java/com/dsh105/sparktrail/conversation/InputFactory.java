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