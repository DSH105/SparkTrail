package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.Effect;
import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.ParticleType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Sound extends Effect {

    public org.bukkit.Sound sound;

    public Sound(EffectHolder effectHolder, org.bukkit.Sound sound) {
        super(effectHolder, ParticleType.SOUND);
        this.sound = sound;
    }

    @Override
    public void playDemo(Player p) {
        p.playSound(this.getHolder().getEffectPlayLocation(), this.sound, 10.0F, 1.0F);
    }

    @Override
    public boolean play() {
        boolean shouldPlay = super.play();
        if (shouldPlay) {
            this.broadcast(this.getHolder().getEffectPlayLocation());
        }
        return shouldPlay;
    }

    public void broadcast(Location l) {
        this.getWorld().playSound(l, this.sound, 10.0F, 1.0F);
    }
}