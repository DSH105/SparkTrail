package com.dsh105.sparktrail.particle.type;

import com.dsh105.sparktrail.particle.Effect;
import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.ParticleType;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public class Ender extends Effect {

    public Ender(EffectHolder effectHolder, ParticleType particleType) {
        super(effectHolder, particleType);
    }

    @Override
    public boolean play() {
        boolean shouldPlay = super.play();
        if (shouldPlay) {
            for (Location l : this.displayType.getLocations(new Location(this.getWorld(), this.getX(), this.getY(), this.getZ()))) {
                this.getWorld().playEffect(new Location(l.getWorld(), l.getX(), l.getY(), l.getZ()), org.bukkit.Effect.ENDER_SIGNAL, 0);
            }
        }
        return shouldPlay;
    }

    public void playDemo(Player p) {
        p.playEffect(p.getLocation(), org.bukkit.Effect.ENDER_SIGNAL, 0);
    }
}