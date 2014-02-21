package com.dsh105.sparktrail.conversation;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;

public class InputPrompt extends ValidatingPrompt {

    private InputFunction function;

    public InputPrompt(InputFunction function) {
        this.function = function;
    }

    @Override
    protected boolean isInputValid(ConversationContext conversationContext, String s) {
        return this.function.isValid(conversationContext, s);
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext conversationContext, String s) {
        this.function.function(conversationContext, s);
        return new InputSuccessPrompt(this.function.getSuccessMessage());
    }

    @Override
    public String getPromptText(ConversationContext conversationContext) {
        return this.function.getPromptText();
    }

    @Override
    protected String getFailedValidationText(ConversationContext context, String invalidInput) {
        return this.function.getFailedText();
    }
}