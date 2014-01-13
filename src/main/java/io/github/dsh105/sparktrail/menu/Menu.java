package io.github.dsh105.sparktrail.menu;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;


public abstract class Menu {

    public boolean fail = false;

    public String viewer;
    public String playerName;
    public Location location = null;
    public UUID mobUuid = null;

    public Player getViewer() {
        return Bukkit.getPlayerExact(this.viewer);
    }

    public abstract void setItems();

    public abstract void open(boolean sendMessage);
}