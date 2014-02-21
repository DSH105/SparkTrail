package com.dsh105.sparktrail.conversation;

import com.dsh105.dshutils.util.StringUtil;
import com.dsh105.sparktrail.data.EffectManager;
import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.util.Lang;
import com.dsh105.sparktrail.util.Permission;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.entity.Player;

public class TimeoutFunction extends InputFunction {

    private Player player;
    private boolean success;

    public TimeoutFunction(Player player) {
        this.player = player;
    }

    @Override
    public void onFunction(ConversationContext context, String input) {
        EffectHolder eh = EffectManager.getInstance().getEffect(player.getName());
        if (eh == null) {
            this.success = false;
        } else {
            eh.setTimeout(Integer.parseInt(input));
            this.success = true;
        }
    }

    @Override
    public String getSuccessMessage() {
        return this.success ? Lang.TIMEOUT_SET.toString().replace("%timeout%", this.getInput()) : Lang.NO_ACTIVE_EFFECTS.toString();
    }

    @Override
    public String getPromptText() {
        return Lang.INPUT_TIMEOUT.toString();
    }

    @Override
    public boolean isValid(ConversationContext conversationContext, String s) {
        return StringUtil.isInt(s) && Permission.TIMEOUT.hasPerm(this.player, true);
    }
}