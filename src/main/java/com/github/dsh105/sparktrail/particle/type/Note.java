package com.github.dsh105.sparktrail.particle.type;

import com.github.dsh105.sparktrail.particle.Effect;
import com.github.dsh105.sparktrail.particle.EffectType;
import com.github.dsh105.sparktrail.particle.ParticleType;
import com.github.dsh105.sparktrail.util.ReflectionUtil;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Project by DSH105
 */

public class Note extends Effect {

	private NoteType noteType;

	public Note(ParticleType particleType, EffectType effectType, NoteType noteType) {
		super(particleType, effectType);
		this.noteType = noteType;
	}

	@Override
	public boolean play() {
		boolean shouldPlay = super.play();
		if (shouldPlay) {
			Location l = new Location(this.world, this.locX, this.locY, this.locZ);
			Material m = l.getBlock().getType();
			byte b = l.getBlock().getData();
			for (Entity e : ReflectionUtil.getNearbyEntities(l, 20)) {
				if (e instanceof Player) {
					((Player) e).sendBlockChange(l, Material.NOTE_BLOCK, (byte) 0);
					((Player) e).playNote(l, Instrument.PIANO, this.noteType.getNote());
					((Player) e).sendBlockChange(l, m, b);
				}
			}
		}
		return shouldPlay;
	}

	public enum NoteType {
		OLIVEGREEN(2),
		YELLOW(3),
		ORANGE(4),
		CRIMSON(5),
		PALERED(8),
		MAGENTA(9),
		VIOLET(11),
		BLUE(13),
		TURQUOISE(18),
		GREEN(23);

		private int value;
		NoteType(int value) {
			this.value = value;
		}
		public int getValue() {
			return this.value;
		}

		public org.bukkit.Note getNote() {
			switch (value) {
				case 0: return org.bukkit.Note.sharp(0, org.bukkit.Note.Tone.F);
				case 1: return org.bukkit.Note.natural(0, org.bukkit.Note.Tone.G);
				case 2: return org.bukkit.Note.sharp(0, org.bukkit.Note.Tone.G);
				case 3: return org.bukkit.Note.natural(0, org.bukkit.Note.Tone.A);
				case 4: return org.bukkit.Note.sharp(0, org.bukkit.Note.Tone.A);
				case 5: return org.bukkit.Note.natural(0, org.bukkit.Note.Tone.B);
				case 6: return org.bukkit.Note.natural(0, org.bukkit.Note.Tone.B);
				case 7: return org.bukkit.Note.sharp(0, org.bukkit.Note.Tone.C);
				case 8: return org.bukkit.Note.natural(0, org.bukkit.Note.Tone.D);
				case 9: return org.bukkit.Note.sharp(0, org.bukkit.Note.Tone.D);
				case 10: return org.bukkit.Note.natural(0, org.bukkit.Note.Tone.E);
				case 11: return org.bukkit.Note.natural(0, org.bukkit.Note.Tone.F);
				case 12: return org.bukkit.Note.sharp(0, org.bukkit.Note.Tone.F);
				case 13: return org.bukkit.Note.natural(1, org.bukkit.Note.Tone.G);
				case 14: return org.bukkit.Note.sharp(1, org.bukkit.Note.Tone.G);
				case 15: return org.bukkit.Note.natural(1, org.bukkit.Note.Tone.A);
				case 16: return org.bukkit.Note.sharp(1, org.bukkit.Note.Tone.A);
				case 17: return org.bukkit.Note.natural(1, org.bukkit.Note.Tone.B);
				case 18: return org.bukkit.Note.natural(1, org.bukkit.Note.Tone.B);
				case 19: return org.bukkit.Note.sharp(1, org.bukkit.Note.Tone.C);
				case 20: return org.bukkit.Note.natural(1, org.bukkit.Note.Tone.D);
				case 21: return org.bukkit.Note.sharp(1, org.bukkit.Note.Tone.D);
				case 22: return org.bukkit.Note.natural(1, org.bukkit.Note.Tone.E);
				case 23: return org.bukkit.Note.natural(1, org.bukkit.Note.Tone.F);
				case 24: return org.bukkit.Note.sharp(1, org.bukkit.Note.Tone.F);
				default: return org.bukkit.Note.sharp(1, org.bukkit.Note.Tone.F);
			}
		}
	}
}