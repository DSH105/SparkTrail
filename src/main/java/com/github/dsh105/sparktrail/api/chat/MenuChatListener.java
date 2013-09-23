package com.github.dsh105.sparktrail.api.chat;

import com.github.dsh105.sparktrail.data.EffectCreator;
import com.github.dsh105.sparktrail.data.EffectHandler;
import com.github.dsh105.sparktrail.logger.Logger;
import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.ParticleDetails;
import com.github.dsh105.sparktrail.particle.ParticleType;
import com.github.dsh105.sparktrail.util.Colour;
import com.github.dsh105.sparktrail.util.EnumUtil;
import com.github.dsh105.sparktrail.util.Lang;
import com.github.dsh105.sparktrail.util.StringUtil;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.*;

/**
 * Project by DSH105
 */

public class MenuChatListener implements Listener{

	public static HashMap<String, WaitingData> AWAITING_DATA = new HashMap<String, WaitingData>();
	public static  HashMap<String, WaitingData> AWAITING_RETRY = new HashMap<String, WaitingData>();

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String msg = event.getMessage();

		if (this.AWAITING_DATA.containsKey(player.getName())) {
			event.setCancelled(true);

			WaitingData wd = this.AWAITING_DATA.get(player.getName());

			ParticleType pt = wd.particleType;
			if (pt == ParticleType.BLOCKBREAK) {
				BlockData bd = findBlockBreak(msg);
				if (bd == null) {
					Lang.sendTo(player, Lang.INCORRECT_EFFECT_ARGS.toString()
							.replace("%effect%", "Block Break")
							.replace("%string%", msg));
					this.AWAITING_RETRY.put(player.getName(), wd);
					this.AWAITING_DATA.remove(player.getName());
					return;
				}

				EffectHolder eh = getHolder(wd);

				if (eh == null) {
					Lang.sendTo(player, Lang.EFFECT_CREATION_FAILED.toString());
					Logger.log(Logger.LogLevel.SEVERE, "Effect creation failed while adding Particle Type (" + wd.particleType.toString() + ", Player: " + player.getName() + ") [Reported from MenuChatListener].", true);
					this.AWAITING_DATA.remove(player.getName());
					return;
				}

				ParticleDetails pd = new ParticleDetails(pt);
				pd.blockId = bd.id;
				pd.blockMeta = bd.data;
				eh.addEffect(pd);

				this.AWAITING_DATA.remove(player.getName());
				return;
			}
			else if (pt == ParticleType.FIREWORK) {
				FireworkEffect fe = findFirework(msg);
				if (fe == null) {
					Lang.sendTo(player, Lang.INCORRECT_EFFECT_ARGS.toString()
							.replace("%effect%", "Firework")
							.replace("%string%", msg));
					this.AWAITING_RETRY.put(player.getName(), wd);
					this.AWAITING_DATA.remove(player.getName());
					return;
				}

				EffectHolder eh = getHolder(wd);

				if (eh == null) {
					Lang.sendTo(player, Lang.EFFECT_CREATION_FAILED.toString());
					Logger.log(Logger.LogLevel.SEVERE, "Effect creation failed while adding Particle Type (" + wd.particleType.toString() + ", Player: " + player.getName() + ") [Reported from MenuChatListener].", true);
					this.AWAITING_DATA.remove(player.getName());
					return;
				}

				ParticleDetails pd = new ParticleDetails(pt);
				pd.fireworkEffect = fe;
				eh.addEffect(pd);

				this.AWAITING_DATA.remove(player.getName());
				return;
			}
		}
		else if (this.AWAITING_RETRY.containsKey(player.getName())) {
			if (msg.equalsIgnoreCase("YES")) {
				if (this.AWAITING_RETRY.get(player.getName()).particleType.equals(ParticleType.BLOCKBREAK)) {
					Lang.sendTo(player, Lang.ENTER_BLOCK.toString());
				}
				else if (this.AWAITING_RETRY.get(player.getName()).equals(ParticleType.FIREWORK)) {
					Lang.sendTo(player, Lang.ENTER_FIREWORK.toString());
				}
				this.AWAITING_DATA.put(player.getName(), this.AWAITING_RETRY.get(player.getName()));
				this.AWAITING_RETRY.remove(player.getName());
			}
			else if (msg.equalsIgnoreCase("NO")) {
				this.AWAITING_RETRY.remove(player.getName());
			}
			else {
				Lang.sendTo(player, Lang.YES_OR_NO.toString());
			}
		}
	}

	private BlockData findBlockBreak(String msg) {
		String[] split = msg.split(" ");
		if (split[1] == null) {
			if (!StringUtil.isInt(split[0])) {
				return null;
			}
			return new BlockData(Integer.parseInt(split[0]), 0);
		}
		else {
			if (!StringUtil.isInt(split[0])) {
				return null;
			}
			if (!StringUtil.isInt(split[1])) {
				return null;
			}
			return new BlockData(Integer.parseInt(split[0]), Integer.parseInt(split[1]));

		}
	}

	private FireworkEffect findFirework(String msg) {
		FireworkEffect fe = null;
		ArrayList<Color> colours = new ArrayList<Color>();
		FireworkEffect.Type type = FireworkEffect.Type.BALL;
		boolean flicker = false;
		boolean trail = true;
		if (msg.equalsIgnoreCase("random")) {
			Random r = new Random();
			int colourAmount = r.nextInt(17);
			for (int i = 0; i < colourAmount; i++) {
				Colour colour = Colour.values()[i];
				if (colours.contains(colour.getColor())) {
					i--;
				}
				else {
					colours.add(colour.getColor());
				}
			}
			type = FireworkEffect.Type.values()[r.nextInt(4)];
			flicker = r.nextBoolean();
			trail = r.nextBoolean();
		}
		else {
			String[] split = msg.split(" ");

			for (String s : split) {
				if (s.equalsIgnoreCase("flicker")) {
					flicker = true;
				}
				if (s.equalsIgnoreCase("trail")) {
					trail = true;
				}

				if (EnumUtil.isEnumType(Colour.class, s.toUpperCase())) {
					colours.add(Colour.valueOf(s.toUpperCase()).getColor());
				}

				if (EnumUtil.isEnumType(FireworkEffect.Type.class, s.toUpperCase())) {
					type = FireworkEffect.Type.valueOf(s.toUpperCase());
				}
			}

		}

		if (colours.isEmpty()) {
			colours.add(Color.WHITE);
		}

		fe = FireworkEffect.builder().withColor(colours).withFade(colours).with(type).flicker(flicker).trail(trail).build();

		if (fe == null) {
			fe = FireworkEffect.builder().withColor(Color.WHITE).withFade(Color.WHITE).build();
		}
		return fe;
	}

	private EffectHolder getHolder(WaitingData data) {
		EffectHolder eh = null;
		if (data.effectType == EffectHolder.EffectType.LOCATION) {
			try {
				eh = EffectHandler.getInstance().getEffect(data.location);
			} catch (Exception e) {
				Logger.log(Logger.LogLevel.SEVERE, "Failed to create Location (" + data + ") whilst removing an Effect (" + data.particleType.toString() + ")", e, true);
				return null;
			}
		}
		else if (data.effectType == EffectHolder.EffectType.PLAYER) {
			eh = EffectHandler.getInstance().getEffect(data.playerName);
		}
		else if (data.effectType == EffectHolder.EffectType.MOB) {
			eh = EffectHandler.getInstance().getEffect(data.mobUuid);
		}

		if (eh == null) {
			HashSet<ParticleDetails> hashSet = new HashSet<ParticleDetails>();
			if (data.effectType == EffectHolder.EffectType.PLAYER) {
				eh = EffectCreator.createPlayerHolder(hashSet, data.effectType, data.playerName, data.mobUuid);
			}
			else if (data.effectType == EffectHolder.EffectType.LOCATION) {
				Location l = data.location;
				if (l == null) {
					Logger.log(Logger.LogLevel.SEVERE, "Failed to create Location Effect (" + data + ") whilst finding EffectHolder (" + data.particleType.toString() + ") [Reported from MenuChatListener].", true);
					return null;
				}
				eh = EffectCreator.createLocHolder(hashSet, data.effectType, l);
			}
			else if (data.effectType == EffectHolder.EffectType.MOB) {
				eh = EffectCreator.createMobHolder(hashSet, data.effectType, data.mobUuid);
			}
		}
		return eh;
	}
}