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

package com.dsh105.sparktrail.conversation.effect;

import com.dsh105.sparktrail.chat.BlockData;
import com.dsh105.sparktrail.chat.WaitingData;
import com.dsh105.sparktrail.data.EffectCreator;
import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.ParticleDetails;
import com.dsh105.sparktrail.trail.ParticleType;
import com.dsh105.sparktrail.util.Lang;
import org.bukkit.FireworkEffect;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

public class EffectInputSuccessPrompt extends MessagePrompt {

    private ParticleType particleType;
    private WaitingData data;
    private BlockData blockData;
    private FireworkEffect fireworkEffect;

    public EffectInputSuccessPrompt(ParticleType particleType, WaitingData data) {
        this.particleType = particleType;
        this.data = data;
    }

    public EffectInputSuccessPrompt(ParticleType particleType, WaitingData data, BlockData blockData) {
        this(particleType, data);
        this.blockData = blockData;
    }

    public EffectInputSuccessPrompt(ParticleType particleType, WaitingData data, FireworkEffect fireworkEffect) {
        this(particleType, data);
        this.fireworkEffect = fireworkEffect;
    }

    @Override
    protected Prompt getNextPrompt(ConversationContext conversationContext) {
        return END_OF_CONVERSATION;
    }

    @Override
    public String getPromptText(ConversationContext conversationContext) {
        ParticleDetails pd = this.build();
        if (pd != null) {
            EffectHolder effectHolder = EffectCreator.prepareNew(this.data);
            if (effectHolder != null) {
                if (effectHolder.addEffect(pd, true)) {
                    return Lang.EFFECT_ADDED.toString().replace("%effect%", this.particleType.getName());
                }
            }
        }
        return Lang.WHUPS.toString();
    }

    private ParticleDetails build() {
        ParticleDetails pd = new ParticleDetails(this.particleType);
        if (this.particleType == ParticleType.BLOCKBREAK || this.particleType == ParticleType.ITEMSPRAY) {
            pd.blockId = this.blockData.id;
            pd.blockMeta = this.blockData.data;
        } else if (this.particleType == ParticleType.FIREWORK) {
            pd.fireworkEffect = this.fireworkEffect;
        }
        return pd;
    }
}