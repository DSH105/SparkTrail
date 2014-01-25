package com.dsh105.sparktrail.util;

import com.dsh105.sparktrail.SparkTrailPlugin;
import com.dsh105.sparktrail.chat.BlockData;
import io.github.dsh105.dshutils.logger.ConsoleLogger;
import io.github.dsh105.dshutils.util.EnumUtil;
import io.github.dsh105.dshutils.util.StringUtil;
import org.bukkit.*;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;


public class Serialise {

    public static BlockData findBlockData(String msg) {
        if (msg.contains(" ")) {
            String[] split = msg.split(" ");
            if (!StringUtil.isInt(split[0])) {
                return null;
            }
            if (!StringUtil.isInt(split[1])) {
                return null;
            }
            return new BlockData(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        } else {
            if (!StringUtil.isInt(msg)) {
                return null;
            }
            return new BlockData(Integer.parseInt(msg), 0);
        }
    }

    public static FireworkEffect findFirework(String msg) {
        FireworkEffect fe = null;
        ArrayList<Color> colours = new ArrayList<Color>();
        FireworkEffect.Type type = FireworkEffect.Type.BALL;
        boolean flicker = false;
        boolean trail = false;
        if (msg.equalsIgnoreCase("random")) {
            Random r = new Random();
            int colourAmount = r.nextInt(17);
            for (int i = 0; i < colourAmount; i++) {
                FireworkColour fireworkColour = FireworkColour.values()[i];
                if (colours.contains(fireworkColour.getColor())) {
                    i--;
                } else {
                    colours.add(fireworkColour.getColor());
                }
            }
            type = FireworkEffect.Type.values()[r.nextInt(4)];
            flicker = r.nextBoolean();
            trail = r.nextBoolean();
        } else {
            String[] split = msg.split(" ");

            for (String s : split) {
                if (s.equalsIgnoreCase("flicker")) {
                    flicker = true;
                }
                if (s.equalsIgnoreCase("trail")) {
                    trail = true;
                }

                if (EnumUtil.isEnumType(FireworkColour.class, s.toUpperCase())) {
                    colours.add(FireworkColour.valueOf(s.toUpperCase()).getColor());
                }

                if (EnumUtil.isEnumType(FireworkType.class, s.toUpperCase())) {
                    type = FireworkType.valueOf(s.toUpperCase()).getFireworkType();
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

    public static Entity getMob(UUID uuid) {
        for (World w : SparkTrailPlugin.getInstance().getServer().getWorlds()) {
            Iterator<Entity> i = w.getEntities().listIterator();
            while (i.hasNext()) {
                Entity entity = i.next();
                if (entity.getUniqueId().equals(uuid)) {
                    return entity;
                }
            }
        }
        return null;
    }

    public static Location getLocation(CommandSender sender, String[] args, int start) {
        World world = (sender instanceof Player) ? ((Player) sender).getWorld() : (sender instanceof BlockCommandSender) ? ((BlockCommandSender) sender).getBlock().getWorld() : Bukkit.getWorlds().get(0);
        int x;
        int y;
        int z;

        if (args[start].equalsIgnoreCase("~") && sender instanceof BlockCommandSender) {
            x = (int) ((BlockCommandSender) sender).getBlock().getLocation().getX();
        } else if (args[start].equalsIgnoreCase("~") && sender instanceof Player) {
            x = (int) ((Player) sender).getLocation().getX();
        } else if (StringUtil.isInt(args[start])) {
            x = Integer.parseInt(args[start]);
        } else {
            ConsoleLogger.log("" + start + " " + args[start]);
            Lang.sendTo(sender, Lang.INT_ONLY_WITH_ARGS.toString().replace("%string%", args[start]).replace("%argNum%", start + ""));
            return null;
        }

        start++;

        if (args[start].equalsIgnoreCase("~") && sender instanceof BlockCommandSender) {
            y = (int) ((BlockCommandSender) sender).getBlock().getLocation().getY();
        } else if (args[start].equalsIgnoreCase("~") && sender instanceof Player) {
            y = (int) ((Player) sender).getLocation().getY();
        } else if (StringUtil.isInt(args[start])) {
            y = Integer.parseInt(args[start]);
        } else {
            Lang.sendTo(sender, Lang.INT_ONLY_WITH_ARGS.toString().replace("%string%", args[start]).replace("%argNum%", start + ""));
            return null;
        }

        start++;

        if (args[start].equalsIgnoreCase("~") && sender instanceof BlockCommandSender) {
            z = (int) ((BlockCommandSender) sender).getBlock().getLocation().getZ();
        } else if (args[start].equalsIgnoreCase("~") && sender instanceof Player) {
            z = (int) ((Player) sender).getLocation().getZ();
        } else if (StringUtil.isInt(args[start])) {
            z = Integer.parseInt(args[start]);
        } else {
            Lang.sendTo(sender, Lang.INT_ONLY_WITH_ARGS.toString().replace("%string%", args[start]).replace("%argNum%", start + ""));
            return null;
        }

        return new Location(world, x, y, z);
    }

    public static String serialiseLocation(Location l) {
        return l.getWorld().getName() + "," + l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ();
    }

    public static String serialiseFireworkEffect(FireworkEffect fe) {
        List<Color> colours = fe.getColors();
        FireworkEffect.Type type = fe.getType();
        boolean flicker = fe.hasFlicker();
        boolean trail = fe.hasTrail();

        String s = "";
        for (Color c : colours) {
            s = s + FireworkColour.getByColor(c).toString() + ",";
        }
        s = s + FireworkType.getByType(type).toString().toLowerCase() + (flicker ? ",flicker" : "") + (trail ? ",trail" : "");
        return s;
    }

    public static FireworkEffect deserialiseFireworkEffect(String s) {
        FireworkEffect fe;
        ArrayList<Color> colours = new ArrayList<Color>();
        FireworkEffect.Type type = FireworkEffect.Type.BALL;
        boolean flicker = false;
        boolean trail = false;

        String[] split = s.split(",");
        for (int i = 0; i < split.length; i++) {
            if (s.equalsIgnoreCase("flicker")) {
                flicker = true;
            }
            if (s.equalsIgnoreCase("trail")) {
                trail = true;
            }

            if (EnumUtil.isEnumType(FireworkColour.class, s.toUpperCase())) {
                colours.add(FireworkColour.valueOf(s.toUpperCase()).getColor());
            }

            if (EnumUtil.isEnumType(FireworkType.class, s.toUpperCase())) {
                type = FireworkType.valueOf(s.toUpperCase()).getFireworkType();
            }
        }

        fe = FireworkEffect.builder().withColor(colours).withFade(colours).with(type).flicker(flicker).trail(trail).build();
        return fe;
    }

    public static String serialiseEffects(HashSet<com.dsh105.sparktrail.particle.Effect> effects) {
        if (effects.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (com.dsh105.sparktrail.particle.Effect e : effects) {
            builder.append(StringUtil.capitalise(e.getParticleType().toString()));
            builder.append(", ");
        }
        builder.deleteCharAt(builder.length() - 2);
        return builder.toString();
    }

}