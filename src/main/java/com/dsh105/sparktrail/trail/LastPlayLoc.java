package com.dsh105.sparktrail.trail;

import org.bukkit.Location;

public class LastPlayLoc {

    private int locX;
    private int locY;
    private int locZ;

    public LastPlayLoc(Location l) {
        this.set(l);
    }

    public void set(Location l) {
        this.locX = l.getBlockX();
        this.locY = l.getBlockY();
        this.locZ = l.getBlockZ();
    }

    public boolean isSimilar(Location l) {
        if (l.getBlockX() == this.locX
                && l.getBlockY() == this.locY
                && l.getBlockZ() == this.locZ) {
            return true;
        }
        this.set(l);
        return false;
    }
}