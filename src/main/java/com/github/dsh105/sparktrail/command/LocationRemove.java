package com.github.dsh105.sparktrail.command;

import com.github.dsh105.sparktrail.SparkTrail;
import com.github.dsh105.sparktrail.data.EffectHandler;
import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.util.Serialise;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * Project by DSH105
 */

public class LocationRemove {

	public static HashSet<LocationRemove> lrs = new HashSet<LocationRemove>();

	public CommandSender sender;
	public HashMap<Integer, EffectHolder> effects = new HashMap<Integer, EffectHolder>();

	public LocationRemove(CommandSender sender) {
		this.sender = sender;
		this.setup();
		lrs.add(this);
	}

	public void setup() {
		int i = 1;
		for (EffectHolder eh : EffectHandler.getInstance().getEffectHolders()) {
			if (eh.getEffectType() == EffectHolder.EffectType.LOCATION) {
				if (eh.getEffects() != null && !eh.getEffects().isEmpty()) {
					effects.put(i++, eh);
				}
			}
		}
	}

	public void view() {
		if (this.sender == null) {
			lrs.remove(this);
			return;
		}
		sender.sendMessage(SparkTrail.getInstance().secondaryColour + "------------ Trail Effects ------------");
		for (Map.Entry<Integer, EffectHolder> entry : effects.entrySet()) {
			int i = entry.getKey();
			EffectHolder eh = entry.getValue();
			sender.sendMessage(SparkTrail.getInstance().primaryColour + "" + i + ": " + SparkTrail.getInstance().secondaryColour + Serialise.serialiseEffects(eh.getEffects()));
		}
	}

	public static boolean contains(CommandSender sender) {
		for (LocationRemove lr : lrs) {
			if (lr.sender.equals(sender)) {
				return true;
			}
		}
		return false;
	}

	public static void remove(CommandSender sender) {
		Iterator<LocationRemove> i = lrs.iterator();
		while (i.hasNext()) {
			LocationRemove lr = i.next();
			if (lr.sender.equals(sender)) {
				i.remove();
			}
		}
	}

	public static EffectHolder get(CommandSender sender, int index) {
		Iterator<LocationRemove> i = lrs.iterator();
		while (i.hasNext()) {
			LocationRemove lr = i.next();
			if (lr.sender.equals(sender)) {
				return lr.effects.get(index);
			}
		}
		return null;
	}

	public static LocationRemove get(CommandSender sender) {
		Iterator<LocationRemove> i = lrs.iterator();
		while (i.hasNext()) {
			LocationRemove lr = i.next();
			if (lr.sender.equals(sender)) {
				return lr;
			}
		}
		return null;
	}
}