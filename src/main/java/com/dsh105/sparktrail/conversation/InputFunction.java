package com.dsh105.sparktrail.conversation;

import com.dsh105.sparktrail.util.Lang;
import org.bukkit.conversations.ConversationContext;

public abstract class InputFunction {

    private String input;

    public String getInput() {
        return input;
    }

    protected void function(ConversationContext context, String input) {
        this.input = input;
        this.onFunction(context, input);
    }

    public abstract void onFunction(ConversationContext context, String input);

    public abstract String getSuccessMessage();

    public abstract String getPromptText();

    public String getFailedText() {
        return Lang.INVALID_INPUT.toString();
    }

    public boolean isValid(ConversationContext conversationContext, String s) {
        return true;
    }
}