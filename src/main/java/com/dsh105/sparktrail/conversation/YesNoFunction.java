package com.dsh105.sparktrail.conversation;

import com.dsh105.sparktrail.listeners.InteractDetails;
import com.dsh105.sparktrail.listeners.InteractListener;
import com.dsh105.sparktrail.trail.ParticleType;
import com.dsh105.sparktrail.util.Lang;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.entity.Player;

public class YesNoFunction extends InputFunction {

    private InteractDetails interactDetails;
    private boolean success = false;
    private String promptText;

    public YesNoFunction(InteractDetails interactDetails, String promptText) {
        this.interactDetails = interactDetails;
        this.promptText = promptText;
    }

    @Override
    public boolean isValid(ConversationContext conversationContext, String s) {
        return s.equalsIgnoreCase("YES") || s.equalsIgnoreCase("NO");
    }

    @Override
    public void onFunction(ConversationContext context, String input) {
        if (input.equalsIgnoreCase("YES")) {
            if (this.interactDetails.interactType == InteractDetails.InteractType.BLOCK) {
                if (context.getForWhom() instanceof Player) {
                    InteractListener.INTERACTION.put(((Player) context.getForWhom()).getName(), this.interactDetails);
                    this.success = true;
                }
            }
        }
    }

    @Override
    public String getSuccessMessage() {
        if (this.success) {
            return this.interactDetails.interactType == InteractDetails.InteractType.BLOCK ? Lang.INTERACT_BLOCK.toString() : Lang.INTERACT_MOB.toString();
        }
        return Lang.CANCEL_EFFECT_CREATION.toString();
    }

    @Override
    public String getPromptText() {
        return this.promptText;
    }
}