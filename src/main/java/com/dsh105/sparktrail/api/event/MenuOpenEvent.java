package com.dsh105.sparktrail.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class MenuOpenEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    private Player viewer;
    private MenuType menuType;

    public MenuOpenEvent(Player viewer, MenuType menuType) {
        this.viewer = viewer;
        this.menuType = menuType;
    }

    /**
     * Gets the {@link Player} involved in this event
     *
     * @return the {@link Player} involved
     */
    public Player getViewer() {
        return this.viewer;
    }

    /**
     * Returns the type of Menu opened
     *
     * @return {@link MenuType} representing the menu opened
     */
    public MenuType getMenuType() {
        return this.menuType;
    }

    /**
     * Gets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins
     *
     * @return true if this event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins
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

    public enum MenuType {
        MAIN,
        DATA;
    }
}