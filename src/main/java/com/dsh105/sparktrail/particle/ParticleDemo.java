package com.dsh105.sparktrail.particle;

import com.dsh105.sparktrail.SparkTrailPlugin;
import com.dsh105.sparktrail.particle.type.*;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;


public class ParticleDemo extends BukkitRunnable {

    public static HashMap<String, ParticleDemo> ACTIVE = new HashMap<String, ParticleDemo>();

    private Player viewer;
    private ParticleType[] particles;
    private ParticleType currentParticle;
    int i = 0;

    public ParticleDemo(Player viewer) {
        this.viewer = viewer;
        this.particles = ParticleType.values();
        ACTIVE.put(viewer.getName(), this);
        this.runTaskTimer(SparkTrailPlugin.getInstance(), 0, 40);
    }

    public ParticleType getCurrentParticle() {
        return this.currentParticle;
    }

    @Override
    public void run() {
        if (i < particles.length) {
            ParticleType pt = particles[i];
            this.currentParticle = pt;
            if (pt == ParticleType.BLOCKBREAK) {
                BlockBreak bb1 = new BlockBreak(null, 8, 0);
                bb1.playDemo(this.viewer);
            } else if (pt == ParticleType.CRITICAL) {
                Critical c = new Critical(null, Critical.CriticalType.NORMAL);
                c.playDemo(this.viewer);
            } else if (pt == ParticleType.ITEMSPRAY) {
                ItemSpray i = new ItemSpray(null, 264, 0);
                i.playDemo(this.viewer);
            } else if (pt == ParticleType.FIREWORK) {
                FireworkEffect fe = FireworkEffect.builder().withColor(Color.WHITE).withFade(Color.WHITE).trail(true).build();
                Firework f = new Firework(null, fe);
                f.playDemo(this.viewer);
            }
            /*else if (pt == ParticleType.NOTE) {
                Note n = pt.getNoteInstance(null, Note.NoteType.BLUE);
				n.playDemo(this.viewer);
			}*/
            else if (pt == ParticleType.POTION) {
                Potion p = new Potion(null, Potion.PotionType.AQUA);
                p.playDemo(this.viewer);
            } else if (pt == ParticleType.SMOKE) {
                Smoke s = new Smoke(null, Smoke.SmokeType.BLACK);
                s.playDemo(this.viewer);
            } else if (pt == ParticleType.SWIRL) {
                Swirl s = new Swirl(null, Swirl.SwirlType.WHITE, this.viewer.getUniqueId());
                s.playDemo(this.viewer);
                s.stopDemo(this.viewer);
            } else {
                Effect e = pt.getEffectInstance(null);
                e.playDemo(this.viewer);
            }
            i++;
        } else {
            this.cancel();
        }
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
        ACTIVE.remove(this.viewer.getName());
    }
}