package com.dsh105.sparktrail.api.event;

import com.dsh105.sparktrail.trail.Effect;
import com.dsh105.sparktrail.trail.EffectHolder;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class EffectPlayEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    private Effect effect;

    public EffectPlayEvent(Effect effect) {
        this.effect = effect;
    }

    /**
     * Get the Effect that is being played
     *
     * @return effect being played
     */
    public Effect getEffect() {
        return this.effect;
    }

    /**
     * Get the {@link com.dsh105.sparktrail.trail.EffectHolder.EffectType} of the effect being played
     *
     * @return type of effect being played
     */
    public EffectHolder.EffectType getEffectType() {
        return this.effect.getEffectType();
    }

    /**
     * Gets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins
     *
     * @return true if this event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Sets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins.
     *
     * @param cancel true if you wish to cancel this event
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return this.handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
