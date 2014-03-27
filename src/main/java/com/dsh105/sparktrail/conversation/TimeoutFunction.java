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