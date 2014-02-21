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