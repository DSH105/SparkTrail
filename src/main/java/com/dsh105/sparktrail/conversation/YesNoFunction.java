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