package com.dsh105.sparktrail.conversation;

import com.dsh105.sparktrail.SparkTrailPlugin;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationPrefix;

public class InputConversationPrefix implements ConversationPrefix {

    @Override
    public String getPrefix(ConversationContext conversationContext) {
        return SparkTrailPlugin.getInstance().prefix;
    }
}