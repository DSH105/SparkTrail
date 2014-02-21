package com.dsh105.sparktrail.conversation;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

public class InputSuccessPrompt extends MessagePrompt {

    private String successMessage;

    public InputSuccessPrompt(String successMessage) {
        this.successMessage = successMessage;
    }

    @Override
    protected Prompt getNextPrompt(ConversationContext conversationContext) {
        return END_OF_CONVERSATION;
    }

    @Override
    public String getPromptText(ConversationContext conversationContext) {
        return this.successMessage;
    }
}