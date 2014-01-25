package com.dsh105.sparktrail.sound;

import com.dsh105.sparktrail.particle.Effect;
import com.dsh105.sparktrail.particle.EffectHolder;
import com.dsh105.sparktrail.particle.ParticleType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Sound extends Effect {

    private org.bukkit.Sound sound;

    public Sound(EffectHolder effectHolder, org.bukkit.Sound sound) {
        super(effectHolder, ParticleType.SOUND);
        this.sound = sound;
    }

    @Override
    public void playDemo(Player p) {
        p.playSound(new Location(this.getWorld(), this.getX(), this.getY(), this.getZ()), this.sound, 10.0F, 1.0F);
    }

    @Override
    public boolean play() {
        boolean shouldPlay = super.play();
        if (shouldPlay) {
            this.broadcast(new Location(this.getWorld(), this.getX(), this.getY(), this.getZ()));
        }
        return shouldPlay;
    }

    public void broadcast(Location l) {
        this.getWorld().playSound(l, this.sound, 10.0F, 1.0F);
    }
}