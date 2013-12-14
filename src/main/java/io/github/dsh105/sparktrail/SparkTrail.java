package io.github.dsh105.sparktrail;

import io.github.dsh105.dshutils.Metrics;
import io.github.dsh105.dshutils.Updater;
import io.github.dsh105.dshutils.Version;
import io.github.dsh105.dshutils.command.CustomCommand;
import io.github.dsh105.dshutils.config.YAMLConfig;
import io.github.dsh105.dshutils.config.YAMLConfigManager;
import io.github.dsh105.dshutils.logger.ConsoleLogger;
import io.github.dsh105.dshutils.logger.Logger;
import io.github.dsh105.dshutils.util.ReflectionUtil;
import io.github.dsh105.sparktrail.api.SparkTrailAPI;
import io.github.dsh105.sparktrail.chat.MenuChatListener;
import io.github.dsh105.sparktrail.command.CommandComplete;
import io.github.dsh105.sparktrail.command.TrailCommand;
import io.github.dsh105.sparktrail.config.ConfigOptions;
import io.github.dsh105.sparktrail.data.AutoSave;
import io.github.dsh105.sparktrail.data.EffectHandler;
import io.github.dsh105.sparktrail.listeners.InteractListener;
import io.github.dsh105.sparktrail.listeners.PlayerListener;
import io.github.dsh105.sparktrail.menu.MenuListener;
import io.github.dsh105.sparktrail.mysql.RefreshConnection;
import io.github.dsh105.sparktrail.mysql.SQLConnection;
import io.github.dsh105.sparktrail.mysql.SQLEffectHandler;
import io.github.dsh105.sparktrail.util.Lang;
import io.github.dsh105.sparktrail.util.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R1.CraftServer;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;


public class SparkTrail extends JavaPlugin {

    private static SparkTrail plugin;
    private YAMLConfigManager configManager;
    private YAMLConfig config;
    private YAMLConfig dataConfig;
    private YAMLConfig langConfig;
    public AutoSave AS;
    public SparkTrailAPI api;

    public EffectHandler EH;
    public SQLEffectHandler SQLH;
    public SQLConnection sqlCon;
    private RefreshConnection sqlRefresh;

    // Update data
    public boolean update = false;
    public String name = "";
    public long size = 0;
    public boolean updateCheck = false;

    public CommandMap CM;

    public ChatColor primaryColour = ChatColor.GREEN;
    public ChatColor secondaryColour = ChatColor.YELLOW;
    public String prefix = "" + ChatColor.GOLD + "[" + ChatColor.DARK_GREEN + "SparkTrail" + ChatColor.GOLD + "] " + ChatColor.RESET;
    public String cmdString = "trail";

    public void onEnable() {
        this.plugin = this;
        PluginManager manager = getServer().getPluginManager();

        ConsoleLogger.initiate(this);
        Logger.initiate(this, "SparkTrail", "[SparkTrail]");

        if (!(Version.getNMSPackage()).equalsIgnoreCase(ReflectionUtil.getVersionString(this))) {
            ConsoleLogger.log(Logger.LogLevel.NORMAL, ChatColor.GREEN + "SparkTrail " + ChatColor.YELLOW
                    + this.getDescription().getVersion() + ChatColor.GREEN
                    + " is only compatible with:");
            ConsoleLogger.log(Logger.LogLevel.NORMAL, ChatColor.YELLOW + "    " + Version.getMinecraftVersion() + "-" + Version.getCraftBukkitVersion() + ".");
            ConsoleLogger.log(Logger.LogLevel.NORMAL, ChatColor.GREEN + "Initialisation failed. Please update the plugin.");
            return;
        }

        this.api = new SparkTrailAPI();

        configManager = new YAMLConfigManager(this);
        String[] header = {"SparkTrail By DSH105", "---------------------",
                "Configuration for SparkTrail 3",
                "See the SparkTrail Wiki before editing this file"};
        try {
            config = configManager.getNewConfig("config.yml", header);
            new ConfigOptions(config);
        } catch (Exception e) {
            Logger.log(Logger.LogLevel.SEVERE, "Failed to generate Configuration File (config.yml).", e, true);
        }

        config.reloadConfig();

        ChatColor colour1 = ChatColor.getByChar(this.getConfig(ConfigType.MAIN).getString("primaryChatColour", "a"));
        if (colour1 != null) {
            this.primaryColour = colour1;
        }
        ChatColor colour2 = ChatColor.getByChar(this.getConfig(ConfigType.MAIN).getString("secondaryChatColour", "e"));
        if (colour2 != null) {
            this.secondaryColour = colour2;
        }

        try {
            dataConfig = configManager.getNewConfig("data.yml");
        } catch (Exception e) {
            Logger.log(Logger.LogLevel.SEVERE, "Failed to generate Configuration File (data.yml).", e, true);
        }
        dataConfig.reloadConfig();

        String[] langHeader = {"SparkTrail By DSH105", "---------------------",
                "Language Configuration File"};
        try {
            langConfig = configManager.getNewConfig("language.yml", langHeader);
            try {
                for (Lang l : Lang.values()) {
                    String[] desc = l.getDescription();
                    langConfig.set(l.getPath(), langConfig.getString(l.getPath(), l.toString_()
                            .replace("&a", "&" + this.primaryColour.getChar())
                            .replace("&e", "&" + this.secondaryColour.getChar())),
                            desc);
                }
                langConfig.saveConfig();
            } catch (Exception e) {
                Logger.log(Logger.LogLevel.SEVERE, "Failed to generate Configuration File (language.yml).", e, true);
            }

        } catch (Exception e) {
        }
        langConfig.reloadConfig();

        this.EH = new EffectHandler();
        this.SQLH = new SQLEffectHandler();

        if (this.config.getBoolean("useSql", false)) {
            String host = config.getString("sql.host", "localhost");
            int port = config.getInt("sql.port", 3306);
            String db = config.getString("sql.database", "SparkTrail");
            String user = config.getString("sql.username", "none");
            String pass = config.getString("sql.password", "none");
            sqlCon = new SQLConnection(host, port, db, user, pass);
            Connection con = sqlCon.getConnection();
            if (con != null) {
                try {
                    con.prepareStatement("CREATE TABLE IF NOT EXISTS PlayerEffects (" +
                            "PlayerName varchar(255)," +
                            "Effects varchar(255)," +
                            "PRIMARY KEY (PlayerName)" +
                            ");").executeUpdate();

                    con.prepareStatement("CREATE TABLE IF NOT EXISTS LocationEffects (" +
                            "Location varchar(255)," +
                            "Effects varchar(255)," +
                            "PRIMARY KEY (Location)" +
                            ");").executeUpdate();

                    con.prepareStatement("CREATE TABLE IF NOT EXISTS MobEffects (" +
                            "MobUUID varchar(255)," +
                            "Effects varchar(255)," +
                            "PRIMARY KEY (MobUUID)" +
                            ");").executeUpdate();
                } catch (SQLException e) {
                    Logger.log(Logger.LogLevel.SEVERE, "MySQL DataBase Table initiation has failed.", e, true);
                }
                this.sqlRefresh = new RefreshConnection(getConfig(ConfigType.MAIN).getInt("sql.timeout") * 20 * 60);
            }
        }

        if (this.config.getBoolean("autosave", true)) {
            this.AS = new AutoSave(this.config.getInt("autosaveTimer", 180));
        }

        // Register custom commands
        // Command string based off the string defined in config.yml
        CustomCommand.initiate(this);
        try {
            if (Bukkit.getServer() instanceof CraftServer) {
                final Field f = CraftServer.class.getDeclaredField("commandMap");
                f.setAccessible(true);
                CM = (CommandMap) f.get(Bukkit.getServer());
            }
        } catch (Exception e) {
            Logger.log(Logger.LogLevel.SEVERE, "Command registration has failed.", e, true);
        }

        String cmdString = this.config.getString("command", "trail");
        if (CM.getCommand(cmdString) != null) {
            Logger.log(Logger.LogLevel.WARNING, "A command under the name " + ChatColor.RED + "/" + cmdString + ChatColor.YELLOW + " already exists. Trail Command temporarily registered under " + ChatColor.RED + "/st:" + cmdString, true);
        }

        CustomCommand cmd = new CustomCommand(cmdString);
        CM.register("st", cmd);
        cmd.setExecutor(new TrailCommand(cmdString));
        cmd.setTabCompleter(new CommandComplete());
        this.cmdString = cmdString;

        manager.registerEvents(new MenuListener(), this);
        manager.registerEvents(new MenuChatListener(), this);
        manager.registerEvents(new PlayerListener(), this);
        manager.registerEvents(new InteractListener(), this);

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            Logger.log(Logger.LogLevel.WARNING, "Plugin Metrics (MCStats) has failed to start.", e, false);
        }

        this.checkUpdates();
    }

    public void onDisable() {

    }

    public static SparkTrail getInstance() {
        return plugin;
    }

    public SparkTrailAPI getAPI() {
        return this.api;
    }

    public YAMLConfig getConfig(ConfigType type) {
        if (type == ConfigType.MAIN) {
            return this.config;
        } else if (type == ConfigType.DATA) {
            return this.dataConfig;
        } else if (type == ConfigType.LANG) {
            return this.langConfig;
        }
        return null;
    }

    public enum ConfigType {
        MAIN, DATA, LANG;
    }

    protected void checkUpdates() {
        if (this.getConfig(ConfigType.MAIN).getBoolean("checkForUpdates", true)) {
            final File file = this.getFile();
            final Updater.UpdateType updateType = this.getConfig(ConfigType.MAIN).getBoolean("autoUpdate", false) ? Updater.UpdateType.DEFAULT : Updater.UpdateType.NO_DOWNLOAD;
            getServer().getScheduler().runTaskAsynchronously(this, new Runnable() {
                @Override
                public void run() {
                    Updater updater = new Updater(plugin, 47704, file, updateType, false);
                    update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;
                    if (update) {
                        name = updater.getLatestName();
                        ConsoleLogger.log(ChatColor.GOLD + "An update is available: " + name);
                        ConsoleLogger.log(ChatColor.GOLD + "Type /stupdate to update.");
                    }
                }
            });
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("stupdate")) {
            if (Permission.UPDATE.hasPerm(sender, true, true)) {
                if (updateCheck) {
                    @SuppressWarnings("unused")
                    Updater updater = new Updater(this, 47704, this.getFile(), Updater.UpdateType.NO_VERSION_CHECK, true);
                    return true;
                } else {
                    sender.sendMessage(this.prefix + ChatColor.GREEN + " An update is not available.");
                    return true;
                }
            } else return true;
        } else if (commandLabel.equalsIgnoreCase("sparktrail")) {
            if (Permission.TRAIL.hasPerm(sender, true, true)) {
                PluginDescriptionFile pdFile = this.getDescription();
                sender.sendMessage(secondaryColour + "-------- SparkTrail --------");
                sender.sendMessage(primaryColour + "Author: " + ChatColor.YELLOW + "DSH105");
                sender.sendMessage(primaryColour + "Description: " + secondaryColour + pdFile.getDescription());
                sender.sendMessage(primaryColour + "Version: " + secondaryColour + pdFile.getVersion());
                sender.sendMessage(primaryColour + "Website: " + secondaryColour + pdFile.getWebsite());
                return true;
            } else return true;
        }
        return false;
    }
}