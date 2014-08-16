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

package sparktrailv4.trail;

import com.dsh105.commodus.StringUtil;
import com.dsh105.commodus.particle.Particle;
import com.dsh105.commodus.particle.ParticleBuilder;
import org.bukkit.Effect;
import sparktrailv4.trail.executor.EffectTrailExecutor;
import sparktrailv4.trail.executor.SoundExecutor;
import sparktrailv4.trail.executor.SwirlExecutor;
import sparktrailv4.trail.executor.TrailExecutor;

import java.util.*;

public enum TrailParticle {

    BLOCK(Particle.Data.BLOCK_BREAK),
    DUST(Particle.Data.BLOCK_DUST),
    ITEMSPRAY(),

    FIREWORK(),

    CLOUD(Particle.DEATH_CLOUD),
    COOKIE(Particle.ANGRY_VILLAGER),
    DRIP(Particle.WATER_DRIP),
    EMBER(Particle.LAVA_SPARK),
    ENDER(Build.ENDER),
    FIRE,
    FOOTSTEP,
    HEART,
    LAVA_DRIP,
    MAGIC(Particle.WITCH_MAGIC),
    NOTE,
    PORTAL,
    RUNES(Particle.MAGIC_RUNES),
    SHOVEL(Particle.SNOW_SHOVEL),
    SLIME(Particle.SLIME_SPLAT),
    SNOWBALL,
    SPLASH,
    SPARKLE,
    VOID,
    WAKE,

    CRITICAL(Build.empty()),
    CRITICAL_NORMAL(CRITICAL, Particle.CRITICAL),
    CRITICAL_MAGIC(CRITICAL, Particle.MAGIC_CRITICAL),

    EXPLOSION(Build.empty()),
    EXPLOSION_SMALL(EXPLOSION, Particle.EXPLOSION),
    EXPLOSION_LARGE(EXPLOSION, Particle.HUGE_EXPLOSION),

    POTION(Build.empty()),
    POTION_AQUA(Build.potion().withData(2)),
    POTION_BLACK(Build.potion().withData(8)),
    POTION_BLUE(Build.potion().withData(0)),
    POTION_CRIMSON(Build.potion().withData(5)),
    POTION_DARK_BLUE(Build.potion().withData(6)),
    POTION_DARK_GREEN(Build.potion().withData(4)),
    POTION_DARK_RED(Build.potion().withData(12)),
    POTION_GOLD(Build.potion().withData(3)),
    POTION_GRAY(Build.potion().withData(10)),
    POTION_GREEN(Build.potion().withData(20)),
    POTION_PINK(Build.potion().withData(1)),
    POTION_RED(Build.potion().withData(9)),

    SWIRL(Build.empty()),
    SWIRL_LIGHT_BLUE(Build.swirl(0x00FFFF)),
    SWIRL_BLUE(Build.swirl(0x0000FF)),
    SWIRL_DARK_BLUE(Build.swirl(0x0000CC)),
    SWIRL_RED(Build.swirl(0xFF3300)),
    SWIRL_DARK_RED(Build.swirl(0x660000)),
    SWIRL_GREEN(Build.swirl(0x00FF00)),
    SWIRL_DARK_GREEN(Build.swirl(0x339900)),
    SWIRL_YELLOW(Build.swirl(0xFF9900)),
    SWIRL_ORANGE(Build.swirl(0xFF6600)),
    SWIRL_GRAY(Build.swirl(0xCCCCCC)),
    SWIRL_BLACK(Build.swirl(0x333333)),
    SWIRL_WHITE(Build.swirl(0xFFFFFF)),
    SWIRL_PURPLE(Build.swirl(0x9900CC)),
    SWIRL_PINK(Build.swirl(0xFF00CC)),

    SMOKE(Build.empty()),
    SMOKE_BLACK(SMOKE, Particle.SMOKE),
    SMOKE_RED(SMOKE, Particle.RED_SMOKE),
    SMOKE_RAINBOW(SMOKE, Particle.RAINBOW_SMOKE),

    SOUND(new SoundExecutor());

    private static Map<TrailParticle, List<TrailParticle>> PARENT_TO_NODE_MAP;

    private TrailParticle parent;
    private TrailExecutor executor;

    private TrailParticle() {
        this.init(Particle.valueOf(name()).builder());
    }

    private TrailParticle(TrailParticle parent, Particle particle) {
        this.init(parent, particle.builder());
    }

    private TrailParticle(Particle particle) {
        this.init(particle == null ? Build.empty() : TrailExecutor.build(particle.builder(), this));
    }

    private TrailParticle(Particle.Data particle) {
        this.init(particle == null ? Build.empty() : TrailExecutor.build(particle.builder(), this));
    }

    private TrailParticle(TrailExecutor executor) {
        this.init(executor);
    }

    private void init(TrailParticle parent, ParticleBuilder builder) {
        this.init(parent, TrailExecutor.build(builder, this));
    }

    private void init(TrailParticle parent, TrailExecutor executor) {
        this.init(executor);
        this.parent = parent;
    }

    private void init(ParticleBuilder builder) {
        this.init(TrailExecutor.build(builder, this));
    }

    private void init(TrailExecutor executor) {
        this.executor = executor.withTrail(this);
    }

    public TrailParticle getParent() {
        return parent;
    }

    public TrailExecutor copyExecutor() {
        try {
            return executor.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException("Oh no, Something went wrong!");
        }
    }

    public String dataName() {
        return toString().toLowerCase();
    }

    public String humanName() {
        String name = StringUtil.capitalise(dataName().replace("_", " ")).trim();
        if (getParent() != null) {
            name = name.replace(getParent().name() + "_", "");
        }
        return name;
    }

    public String commandName() {
        return dataName().replace("_", "");
    }

    public static List<TrailParticle> getByParent(TrailParticle parent) {
        if (PARENT_TO_NODE_MAP == null) {
            PARENT_TO_NODE_MAP = new HashMap<>();

            for (TrailParticle trail : TrailParticle.values()) {
                if (trail.getParent() != null) {
                    List<TrailParticle> nodes = PARENT_TO_NODE_MAP.get(trail.getParent());
                    if (nodes == null) {
                        nodes = new ArrayList<>();
                    }
                    nodes.add(trail);
                    PARENT_TO_NODE_MAP.put(trail.getParent(), nodes);
                }
            }
        }
        List<TrailParticle> nodes = PARENT_TO_NODE_MAP.get(parent);
        return Collections.unmodifiableList(nodes == null ? new ArrayList<TrailParticle>() : nodes);
    }

    /**
     * For convenience
     */
    private static class Build {

        private static TrailExecutor EMPTY = new TrailExecutor(SMOKE_BLACK, "", 0, 0);
        private static EffectTrailExecutor EFFECT = new EffectTrailExecutor(null, null, 0);
        private static SwirlExecutor SWIRL = new SwirlExecutor(null, 0);
        private static EffectTrailExecutor ENDER = effect(Effect.ENDER_SIGNAL);

        public static TrailExecutor empty() {
            try {
                return EMPTY.clone();
            } catch (CloneNotSupportedException e) {
                throw new UnsupportedOperationException("Oh no, Something went wrong!");
            }
        }

        public static EffectTrailExecutor effect(Effect effect) {
            try {
                return EFFECT.clone().withEffect(effect);
            } catch (CloneNotSupportedException e) {
                throw new UnsupportedOperationException("Oh no, Something went wrong!");
            }
        }

        public static EffectTrailExecutor potion() {
            return effect(Effect.POTION_BREAK);
        }

        public static SwirlExecutor swirl(int data) {
            try {
                return SWIRL.clone().withData(data);
            } catch (CloneNotSupportedException e) {
                throw new UnsupportedOperationException("Oh no, Something went wrong!");
            }
        }
    }
}