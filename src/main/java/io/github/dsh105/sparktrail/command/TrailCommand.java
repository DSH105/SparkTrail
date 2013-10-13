package io.github.dsh105.sparktrail.command;

import io.github.dsh105.sparktrail.SparkTrail;
import io.github.dsh105.sparktrail.chat.BlockData;
import io.github.dsh105.sparktrail.data.EffectCreator;
import io.github.dsh105.sparktrail.data.EffectHandler;
import io.github.dsh105.sparktrail.listeners.InteractDetails;
import io.github.dsh105.sparktrail.listeners.InteractListener;
import io.github.dsh105.sparktrail.menu.ParticleMenu;
import io.github.dsh105.sparktrail.particle.*;
import io.github.dsh105.sparktrail.particle.type.Critical;
import io.github.dsh105.sparktrail.particle.type.Potion;
import io.github.dsh105.sparktrail.particle.type.Smoke;
import io.github.dsh105.sparktrail.particle.type.Swirl;
import io.github.dsh105.sparktrail.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Project by DSH105
 */

public class TrailCommand implements CommandExecutor {

    public String label;
    ChatColor c1 = SparkTrail.getInstance().primaryColour;
    ChatColor c2 = SparkTrail.getInstance().secondaryColour;

    public TrailCommand(String name) {
        this.label = name;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        boolean error = true;

        if (args.length == 0) {
            if (Permission.TRAIL.hasPerm(sender, true, false)) {
                Player p = (Player) sender;
                ParticleMenu pm = new ParticleMenu(p, p.getName());
                if (pm.fail) {
                    Lang.sendTo(sender, Lang.MENU_ERROR.toString());
                    return true;
                }
                pm.open(true);
                return true;
            } else error = false;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("location")) {
            if (Permission.TRAIL_LOC.hasPerm(sender, true, false)) {
                Player p = (Player) sender;
                InteractListener.INTERACTION.put(p.getName(), new InteractDetails(InteractDetails.InteractType.BLOCK, InteractDetails.ModifyType.ADD));
                Lang.sendTo(sender, Lang.INTERACT_BLOCK.toString());
                return true;
            } else error = false;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("mob")) {

        } else if (args.length == 1 || (args.length >= 2 && (args[0].equalsIgnoreCase("blockbreak") || args[0].equalsIgnoreCase("firework")))) {
            if (args[0].equalsIgnoreCase("help")) {
                if (Permission.TRAIL.hasPerm(sender, true, true)) {
                    sender.sendMessage(c2 + "------------ SparkTrail Help 1/4 ------------");
                    sender.sendMessage(c2 + "Parameters: <> = Required      [] = Optional");
                    for (String s : HelpPage.getPage(1)) {
                        sender.sendMessage(s);
                    }
                    return true;
                } else error = false;
            } else if (args[0].equalsIgnoreCase("random")) {
                if (!(sender instanceof Player)) {
                    Lang.sendTo(sender, Lang.IN_GAME_ONLY.toString());
                    return true;
                }
                Player p = (Player) sender;

                EffectHolder eh = EffectHandler.getInstance().getEffect(p.getName());
                if (eh == null) {
                    eh = EffectCreator.createPlayerHolder(new HashSet<ParticleDetails>(), EffectHolder.EffectType.PLAYER, p.getName());
                }

                ArrayList<ParticleType> list = new ArrayList<ParticleType>();
                for (ParticleType pt : ParticleType.values()) {
                    if (!pt.requiresDataMenu()) {
                        if (Permission.hasEffectPerm(p, false, pt, EffectHolder.EffectType.PLAYER) && !eh.hasEffect(pt)) {
                            list.add(pt);
                        }
                    }
                }

                if (list.isEmpty()) {
                    Lang.sendTo(p, Lang.RANDOM_SELECT_FAILED.toString());
                    return true;
                }
                ParticleType pt = list.get(new Random().nextInt(list.size()));
                eh.addEffect(pt);
                Lang.sendTo(p, Lang.EFFECT_ADDED.toString().replace("%effect%", pt.getName()));
                return true;
            } else if (args[0].equalsIgnoreCase("demo")) {
                if (Permission.DEMO.hasPerm(sender, true, false)) {
                    Player p = (Player) sender;
                    if (ParticleDemo.ACTIVE.containsKey(p.getName())) {
                        ParticleDemo.ACTIVE.get(p.getName()).cancel();
                        Lang.sendTo(sender, Lang.DEMO_STOP.toString());
                        return true;
                    }
                    Lang.sendTo(sender, Lang.DEMO_BEGIN.toString());
                    ParticleDemo pd = new ParticleDemo((Player) sender);
                    return true;
                } else error = false;
            } else if (args[0].equalsIgnoreCase("info")) {
                if (Permission.INFO.hasPerm(sender, true, false)) {
                    EffectHolder eh = EffectHandler.getInstance().getEffect(((Player) sender).getName());
                    if (eh == null || eh.getEffects().isEmpty()) {
                        Lang.sendTo(sender, Lang.NO_ACTIVE_EFFECTS.toString());
                        return true;
                    }
                    sender.sendMessage(c2 + "------------ Trail Effects ------------");
                    for (Effect e : eh.getEffects()) {
                        if (e.getParticleType().requiresDataMenu()) {
                            sender.sendMessage(c1 + StringUtil.capitalise(e.getParticleType().toString()) + ": " + c2 + e.getParticleData());
                        } else {
                            sender.sendMessage(c1 + StringUtil.capitalise(e.getParticleType().toString()));
                        }
                    }
                    return true;
                } else error = false;
            } else if (args[0].equalsIgnoreCase("start")) {
                if (Permission.START.hasPerm(sender, true, false)) {
                    EffectHolder eh = EffectHandler.getInstance().createFromFile(sender.getName());
                    if (eh == null || eh.getEffects().isEmpty()) {
                        Lang.sendTo(sender, Lang.NO_EFFECTS_TO_LOAD.toString());
                        EffectHandler.getInstance().clear(eh);
                        return true;
                    }
                    Lang.sendTo(sender, Lang.EFFECTS_LOADED.toString());
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("stop")) {
                if (Permission.STOP.hasPerm(sender, true, false)) {
                    EffectHolder eh = EffectHandler.getInstance().getEffect(sender.getName());
                    if (eh == null) {
                        Lang.sendTo(sender, Lang.NO_ACTIVE_EFFECTS.toString());
                        return true;
                    }
                    EffectHandler.getInstance().remove(eh);
                    Lang.sendTo(sender, Lang.EFFECTS_STOPPED.toString());
                    return true;
                } else error = false;
            } else if (args[0].equalsIgnoreCase("clear")) {
                if (Permission.CLEAR.hasPerm(sender, true, false)) {
                    EffectHolder eh = EffectHandler.getInstance().getEffect(sender.getName());
                    if (eh == null) {
                        Lang.sendTo(sender, Lang.NO_ACTIVE_EFFECTS.toString());
                        return true;
                    }
                    EffectHandler.getInstance().clear(eh);
                    Lang.sendTo(sender, Lang.EFFECTS_CLEARED.toString());
                    return true;
                } else error = false;
            } else {
                if (EnumUtil.isEnumType(ParticleType.class, args[0].toUpperCase())) {
                    ParticleType pt = ParticleType.valueOf(args[0].toUpperCase());
                    if (!(sender instanceof Player)) {
                        Lang.sendTo(sender, Lang.IN_GAME_ONLY.toString());
                        return true;
                    }
                    Player p = (Player) sender;
                    if (pt.requiresDataMenu()) {
                        if (pt == ParticleType.BLOCKBREAK) {
                            if (Permission.hasEffectPerm(p, true, pt, EffectHolder.EffectType.PLAYER)) {
                                BlockData bd = Serialise.findBlockBreak(StringUtil.combineSplit(1, args, " "));
                                ParticleDetails pd = new ParticleDetails(pt);
                                pd.blockId = bd.id;
                                pd.blockMeta = bd.data;
                                EffectHolder eh = EffectHandler.getInstance().getEffect(p.getName());
                                if (eh == null) {
                                    eh = EffectCreator.createPlayerHolder(new HashSet<ParticleDetails>(), EffectHolder.EffectType.PLAYER, p.getName());
                                }
                                if (eh.hasEffect(pt)) {
                                    eh.removeEffect(pt);
                                    Lang.sendTo(p, Lang.EFFECT_REMOVED.toString().replace("%effect%", pt.getName()));
                                } else {
                                    eh.addEffect(pt);
                                    Lang.sendTo(p, Lang.EFFECT_ADDED.toString().replace("%effect%", pt.getName()));
                                }
                            }
                            return true;
                        } else if (pt == ParticleType.FIREWORK) {
                            if (Permission.hasEffectPerm(p, true, pt, EffectHolder.EffectType.PLAYER)) {
                                FireworkEffect fe = Serialise.findFirework(StringUtil.combineSplit(1, args, " "));
                                ParticleDetails pd = new ParticleDetails(pt);
                                pd.fireworkEffect = fe;
                                EffectHolder eh = EffectHandler.getInstance().getEffect(p.getName());
                                if (eh == null) {
                                    eh = EffectCreator.createPlayerHolder(new HashSet<ParticleDetails>(), EffectHolder.EffectType.PLAYER, p.getName());
                                }
                                if (eh.hasEffect(pt)) {
                                    eh.removeEffect(pt);
                                    Lang.sendTo(p, Lang.EFFECT_REMOVED.toString().replace("%effect%", pt.getName()));
                                } else {
                                    eh.addEffect(pt);
                                    Lang.sendTo(p, Lang.EFFECT_ADDED.toString().replace("%effect%", pt.getName()));
                                }
                            }
                            return true;
                        } else if (pt == ParticleType.CRITICAL) {
                            Lang.sendTo(sender, Lang.CRITICAL_HELP.toString());
                            return true;
                        } else if (pt == ParticleType.POTION) {
                            Lang.sendTo(sender, Lang.POTION_HELP.toString());
                            return true;
                        } else if (pt == ParticleType.SMOKE) {
                            Lang.sendTo(sender, Lang.SMOKE_HELP.toString());
                            return true;
                        } else if (pt == ParticleType.SWIRL) {
                            Lang.sendTo(sender, Lang.SWIRL_HELP.toString());
                            return true;
                        }
                    } else if (Permission.hasEffectPerm(p, true, pt, EffectHolder.EffectType.PLAYER)) {
                        EffectHolder eh = EffectHandler.getInstance().getEffect(p.getName());
                        if (eh == null) {
                            eh = EffectCreator.createPlayerHolder(new HashSet<ParticleDetails>(), EffectHolder.EffectType.PLAYER, p.getName());
                        }
                        if (eh.hasEffect(pt)) {
                            eh.removeEffect(pt);
                            Lang.sendTo(p, Lang.EFFECT_REMOVED.toString().replace("%effect%", pt.getName()));
                        } else {
                            eh.addEffect(pt);
                            Lang.sendTo(p, Lang.EFFECT_ADDED.toString().replace("%effect%", pt.getName()));
                        }
                        return true;
                    } else error = false;
                }
            }
        } else if (args.length == 2) {
            if (args[0].equals("help")) {
                if (Permission.TRAIL.hasPerm(sender, true, true)) {
                    if (StringUtil.isInt(args[1])) {
                        String[] help = HelpPage.getPage(Integer.parseInt(args[1]));
                        if (help == null) {
                            Lang.sendTo(sender, Lang.HELP_INDEX_TOO_BIG.toString().replace("%index%", args[1]));
                            return true;
                        }
                        sender.sendMessage(c2 + "------------ SparkTrail Help " + args[1] + "/4 ------------");
                        sender.sendMessage(c2 + "Parameters: <> = Required      [] = Optional");
                        for (String s : help) {
                            sender.sendMessage(s);
                        }
                    }
                    return true;
                } else error = false;
            } else if (args[0].equalsIgnoreCase("player")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    Lang.sendTo(sender, Lang.NULL_PLAYER.toString().replace("%player%", args[1]));
                    return true;
                }
                ParticleMenu pm = new ParticleMenu(((Player) sender), target.getName());
                if (pm.fail) {
                    Lang.sendTo(sender, Lang.MENU_ERROR.toString());
                    return true;
                }
                pm.open(false);
                Lang.sendTo(sender, Lang.ADMIN_OPEN_MENU.toString().replace("%player%", target.getName()));
                return true;
            } else if (args[0].equalsIgnoreCase("location")) {
                if (args[1].equalsIgnoreCase("list")) {
                    if (Permission.LOC_LIST.hasPerm(sender, true, true)) {
                        ArrayList<EffectHolder> list = new ArrayList<EffectHolder>();
                        for (EffectHolder eh : EffectHandler.getInstance().getEffectHolders()) {
                            if (eh.getEffectType().equals(EffectHolder.EffectType.LOCATION)) {
                                list.add(eh);
                            }
                        }
                        if (list.isEmpty()) {
                            Lang.sendTo(sender, Lang.LOC_NO_ACTIVE_EFFECTS.toString());
                            return true;
                        }
                        sender.sendMessage(SparkTrail.getInstance().secondaryColour + "------------ Trail Effects ------------");
                        for (EffectHolder eh : list) {
                            sender.sendMessage(SparkTrail.getInstance().primaryColour + Serialise.serialiseLocation(eh.getLocation()));
                            sender.sendMessage(SparkTrail.getInstance().primaryColour + " ---> " + SparkTrail.getInstance().secondaryColour + Serialise.serialiseEffects(eh.getEffects()));
                        }
                        return true;
                    } else error = false;
                } else if (args[1].equalsIgnoreCase("stop")) {
                    if (Permission.LOC_STOP.hasPerm(sender, true, false)) {
                        InteractListener.INTERACTION.put(sender.getName(), new InteractDetails(InteractDetails.InteractType.BLOCK, InteractDetails.ModifyType.REMOVE));
                        Lang.sendTo(sender, Lang.INTERACT_BLOCK.toString());
                        return true;
                    } else error = false;
                }
            } else {
                if (EnumUtil.isEnumType(ParticleType.class, args[0].toUpperCase())) {
                    ParticleType pt = ParticleType.valueOf(args[0].toUpperCase());
                    Player p = (Player) sender;
                    ParticleDetails pd = null;
                    if (pt == ParticleType.CRITICAL) {
                        if (EnumUtil.isEnumType(Critical.CriticalType.class, args[1].toUpperCase())) {
                            Critical.CriticalType type = Critical.CriticalType.valueOf(args[1].toUpperCase());
                            if (Permission.hasEffectPerm(p, true, pt, type.toString().toLowerCase(), EffectHolder.EffectType.PLAYER)) {
                                pd = new ParticleDetails(pt);
                                pd.criticalType = type;
                            } else error = false;
                        } else {
                            Lang.sendTo(sender, Lang.CRITICAL_HELP.toString());
                            return true;
                        }
                    } else if (pt == ParticleType.POTION) {
                        if (EnumUtil.isEnumType(Potion.PotionType.class, args[1].toUpperCase())) {
                            Potion.PotionType type = Potion.PotionType.valueOf(args[1].toUpperCase());
                            if (Permission.hasEffectPerm(p, true, pt, type.toString().toLowerCase(), EffectHolder.EffectType.PLAYER)) {
                                pd = new ParticleDetails(pt);
                                pd.potionType = type;
                            } else error = false;
                        } else {
                            Lang.sendTo(sender, Lang.POTION_HELP.toString());
                            return true;
                        }
                    } else if (pt == ParticleType.SMOKE) {
                        if (EnumUtil.isEnumType(Smoke.SmokeType.class, args[1].toUpperCase())) {
                            Smoke.SmokeType type = Smoke.SmokeType.valueOf(args[1].toUpperCase());
                            if (Permission.hasEffectPerm(p, true, pt, type.toString().toLowerCase(), EffectHolder.EffectType.PLAYER)) {
                                pd = new ParticleDetails(pt);
                                pd.smokeType = type;
                            } else error = false;
                        } else {
                            Lang.sendTo(sender, Lang.SMOKE_HELP.toString());
                            return true;
                        }
                    } else if (pt == ParticleType.SWIRL) {
                        if (EnumUtil.isEnumType(Swirl.SwirlType.class, args[1].toUpperCase())) {
                            Swirl.SwirlType type = Swirl.SwirlType.valueOf(args[1].toUpperCase());
                            if (Permission.hasEffectPerm(p, true, pt, type.toString().toLowerCase(), EffectHolder.EffectType.PLAYER)) {
                                pd = new ParticleDetails(pt);
                                pd.swirlType = type;
                                pd.setPlayer(p.getName(), p.getUniqueId());
                            } else error = false;
                        } else {
                            Lang.sendTo(sender, Lang.SWIRL_HELP.toString());
                            return true;
                        }
                    }
                    if (pd != null) {
                        EffectHolder eh = EffectHandler.getInstance().getEffect(p.getName());
                        if (eh == null) {
                            eh = EffectCreator.createPlayerHolder(new HashSet<ParticleDetails>(), EffectHolder.EffectType.PLAYER, p.getName());
                        }
                        if (eh.hasEffect(pd)) {
                            eh.removeEffect(pd);
                            Lang.sendTo(p, Lang.EFFECT_REMOVED.toString().replace("%effect%", pt.getName()));
                        } else {
                            eh.addEffect(pd);
                            Lang.sendTo(p, Lang.EFFECT_ADDED.toString().replace("%effect%", pt.getName()));
                        }
                        return true;
                    }
                }
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("player")) {
                if (args[2].equalsIgnoreCase("info")) {

                } else if (args[2].equalsIgnoreCase("start")) {

                } else if (args[2].equalsIgnoreCase("stop")) {

                } else if (args[2].equalsIgnoreCase("clear")) {

                }
            }
        } else if (args.length == 5 && args[0].equals("location") && args[1].equalsIgnoreCase("stop")) {
            Location l = Serialise.getLocation(sender, args, 2);
            if (l == null) {
                return true;
            }

            EffectHolder eh = EffectHandler.getInstance().getEffect(l);
            if (eh == null) {
                Lang.sendTo(sender, Lang.LOC_NO_ACTIVE_EFFECTS.toString());
                return true;
            }

            EffectHandler.getInstance().remove(eh);
            Lang.sendTo(sender, Lang.LOC_EFFECTS_STOPPED.toString());
            return true;
        } else if (args.length == 5 || (args.length >= 6 && (args[4].equalsIgnoreCase("blockbreak") || args[4].equalsIgnoreCase("firework")))) {
            if (args[0].equalsIgnoreCase("location")) {
                Location l = Serialise.getLocation(sender, args, 1);
                if (l == null) {
                    return true;
                }

                if (EnumUtil.isEnumType(ParticleType.class, args[4].toUpperCase())) {
                    ParticleType pt = ParticleType.valueOf(args[4].toUpperCase());
                    if (pt == ParticleType.BLOCKBREAK) {
                        if (!(sender instanceof Player) || Permission.hasEffectPerm(((Player) sender), true, pt, EffectHolder.EffectType.PLAYER)) {
                            BlockData bd = Serialise.findBlockBreak(StringUtil.combineSplit(5, args, " "));
                            ParticleDetails pd = new ParticleDetails(pt);
                            pd.blockId = bd.id;
                            pd.blockMeta = bd.data;
                            add(l, pt, sender, pd);
                        }
                        return true;
                    } else if (pt == ParticleType.FIREWORK) {
                        if (!(sender instanceof Player) || Permission.hasEffectPerm(((Player) sender), true, pt, EffectHolder.EffectType.PLAYER)) {
                            FireworkEffect fe = Serialise.findFirework(StringUtil.combineSplit(5, args, " "));
                            ParticleDetails pd = new ParticleDetails(pt);
                            pd.fireworkEffect = fe;
                            add(l, pt, sender, pd);
                        }
                        return true;
                    } else if (pt == ParticleType.CRITICAL) {
                        Lang.sendTo(sender, Lang.CRITICAL_HELP.toString());
                        return true;
                    } else if (pt == ParticleType.POTION) {
                        Lang.sendTo(sender, Lang.POTION_HELP.toString());
                        return true;
                    } else if (pt == ParticleType.SMOKE) {
                        Lang.sendTo(sender, Lang.SMOKE_HELP.toString());
                        return true;
                    } else if (pt == ParticleType.SWIRL) {
                        Lang.sendTo(sender, Lang.SWIRL_NOT_ALLOWED.toString());
                        return true;
                    } else if (!(sender instanceof Player) || Permission.hasEffectPerm(((Player) sender), true, pt, EffectHolder.EffectType.PLAYER)) {
                        EffectHolder eh = EffectHandler.getInstance().getEffect(l);
                        if (eh == null) {
                            eh = EffectCreator.createLocHolder(new HashSet<ParticleDetails>(), EffectHolder.EffectType.LOCATION, l);
                        }
                        if (eh.hasEffect(pt)) {
                            eh.removeEffect(pt);
                            Lang.sendTo(sender, Lang.EFFECT_REMOVED.toString().replace("%effect%", pt.getName()));
                        } else {
                            eh.addEffect(pt);
                            Lang.sendTo(sender, Lang.EFFECT_ADDED.toString().replace("%effect%", pt.getName()));
                        }
                        return true;
                    } else error = false;
                }
            }
        } else if (args.length == 6) {
            if (args[0].equalsIgnoreCase("location")) {
                Location l = Serialise.getLocation(sender, args, 1);
                if (l == null) {
                    return true;
                }

                ParticleDetails pd = null;

                if (EnumUtil.isEnumType(ParticleType.class, args[5].toUpperCase())) {
                    ParticleType pt = ParticleType.valueOf(args[5].toUpperCase());
                    if (pt == ParticleType.CRITICAL) {
                        if (EnumUtil.isEnumType(Critical.CriticalType.class, args[5].toUpperCase())) {
                            Critical.CriticalType type = Critical.CriticalType.valueOf(args[5].toUpperCase());
                            if (!(sender instanceof Player) || Permission.hasEffectPerm(((Player) sender), true, pt, type.toString().toLowerCase(), EffectHolder.EffectType.LOCATION)) {
                                pd = new ParticleDetails(pt);
                                pd.criticalType = type;
                            } else error = false;
                        } else {
                            Lang.sendTo(sender, Lang.CRITICAL_HELP.toString());
                            return true;
                        }
                    } else if (pt == ParticleType.POTION) {
                        if (EnumUtil.isEnumType(Potion.PotionType.class, args[5].toUpperCase())) {
                            Potion.PotionType type = Potion.PotionType.valueOf(args[5].toUpperCase());
                            if (!(sender instanceof Player) || Permission.hasEffectPerm(((Player) sender), true, pt, type.toString().toLowerCase(), EffectHolder.EffectType.LOCATION)) {
                                pd = new ParticleDetails(pt);
                                pd.potionType = type;
                            } else error = false;
                        } else {
                            Lang.sendTo(sender, Lang.POTION_HELP.toString());
                            return true;
                        }
                    } else if (pt == ParticleType.SMOKE) {
                        if (EnumUtil.isEnumType(Smoke.SmokeType.class, args[5].toUpperCase())) {
                            Smoke.SmokeType type = Smoke.SmokeType.valueOf(args[5].toUpperCase());
                            if (!(sender instanceof Player) || Permission.hasEffectPerm(((Player) sender), true, pt, type.toString().toLowerCase(), EffectHolder.EffectType.LOCATION)) {
                                pd = new ParticleDetails(pt);
                                pd.smokeType = type;
                            } else error = false;
                        } else {
                            Lang.sendTo(sender, Lang.SMOKE_HELP.toString());
                            return true;
                        }
                    } else if (pt == ParticleType.SWIRL) {
                        Lang.sendTo(sender, Lang.SWIRL_NOT_ALLOWED.toString());
                        return true;
                    }
                    if (pd != null) {
                        EffectHolder eh = EffectHandler.getInstance().getEffect(l);
                        if (eh == null) {
                            eh = EffectCreator.createLocHolder(new HashSet<ParticleDetails>(), EffectHolder.EffectType.LOCATION, l);
                        }
                        if (eh.hasEffect(pd)) {
                            eh.removeEffect(pd);
                            Lang.sendTo(sender, Lang.EFFECT_REMOVED.toString().replace("%effect%", pt.getName()));
                        } else {
                            eh.addEffect(pd);
                            Lang.sendTo(sender, Lang.EFFECT_ADDED.toString().replace("%effect%", pt.getName()));
                        }
                        return true;
                    }
                }
            }
        }

        if (error) {
            Lang.sendTo(sender, Lang.COMMAND_ERROR.toString()
                    .replace("%cmd%", "/" + cmd.getLabel() + " " + (args.length == 0 ? "" : StringUtil.combineSplit(0, args, " "))));
        }
        return true;
    }

    public void add(Location l, ParticleType pt, CommandSender sender, ParticleDetails pd) {
        EffectHolder eh = EffectHandler.getInstance().getEffect(l);
        if (eh == null) {
            eh = EffectCreator.createLocHolder(new HashSet<ParticleDetails>(), EffectHolder.EffectType.LOCATION, l);
        }
        if (eh.hasEffect(pd)) {
            eh.removeEffect(pd);
            Lang.sendTo(sender, Lang.EFFECT_REMOVED.toString().replace("%effect%", pt.getName()));
        } else {
            eh.addEffect(pd);
            Lang.sendTo(sender, Lang.EFFECT_ADDED.toString().replace("%effect%", pt.getName()));
        }
    }
}