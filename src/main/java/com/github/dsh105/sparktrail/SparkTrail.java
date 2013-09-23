package com.github.dsh105.sparktrail;

import com.github.dsh105.sparktrail.api.SparkTrailAPI;
import com.github.dsh105.sparktrail.api.chat.MenuChatListener;
import com.github.dsh105.sparktrail.command.AdminCommand;
import com.github.dsh105.sparktrail.command.CommandComplete;
import com.github.dsh105.sparktrail.command.CustomCommand;
import com.github.dsh105.sparktrail.command.TrailCommand;
import com.github.dsh105.sparktrail.config.YAMLConfig;
import com.github.dsh105.sparktrail.config.YAMLConfigManager;
import com.github.dsh105.sparktrail.config.options.ConfigOptions;
import com.github.dsh105.sparktrail.data.AutoSave;
import com.github.dsh105.sparktrail.data.EffectHandler;
import com.github.dsh105.sparktrail.listeners.PlayerListener;
import com.github.dsh105.sparktrail.logger.ConsoleLogger;
import com.github.dsh105.sparktrail.logger.Logger;
import com.github.dsh105.sparktrail.menu.MenuListener;
import com.github.dsh105.sparktrail.mysql.RefreshConnection;
import com.github.dsh105.sparktrail.mysql.SQLConnection;
import com.github.dsh105.sparktrail.mysql.SQLEffectHandler;
import com.github.dsh105.sparktrail.util.EnumUtil;
import com.github.dsh105.sparktrail.util.Lang;
import com.github.dsh105.sparktrail.util.ReflectionUtil;
import com.github.dsh105.sparktrail.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_6_R3.CraftServer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Project by DSH105
 */

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
	public String prefix = "" + secondaryColour + "[" + primaryColour + "SparkTrail" + secondaryColour + "] " + ChatColor.RESET;
	public String cmdString = "trail";
	public String adminCmdString = "trailadmin";

	public void onEnable() {
		this.plugin = this;
		PluginManager manager = getServer().getPluginManager();

		ConsoleLogger.initiate();
		Logger.initiate();

		if (!(Version.getNMSPackage()).equalsIgnoreCase(ReflectionUtil.getVersionString())) {
			ConsoleLogger.log(Logger.LogLevel.NORMAL, ChatColor.GREEN + "EchoPet " + ChatColor.YELLOW
					+ this.getDescription().getVersion() + ChatColor.GREEN
					+ " is only compatible with:");
			ConsoleLogger.log(Logger.LogLevel.NORMAL, ChatColor.YELLOW + "    " + Version.getMinecraftVersion() + "-" + Version.getCraftBukkitVersion() + ".");
			ConsoleLogger.log(Logger.LogLevel.NORMAL, ChatColor.GREEN + "Initialisation failed. Please update the plugin.");
			this.checkForUpdates();
			return;
		}

		this.api = new SparkTrailAPI();

		configManager = new YAMLConfigManager(this);
		String[] header = { "SparkTrail By DSH105", "---------------------",
				"Configuration for SparkTrail 3",
				"See the SparkTrail Wiki before editing this file" };
		try {
			config = configManager.getNewConfig("config.yml", header);
			new ConfigOptions(this.getConfig(ConfigType.MAIN)).setDefaults();
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

		String[] langHeader = { "SparkTrail By DSH105", "---------------------",
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

		} catch (Exception e) {}
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
		try {
			if (Bukkit.getServer() instanceof CraftServer) {
				final Field f = CraftServer.class.getDeclaredField("commandMap");
				f.setAccessible(true);
				CM = (CommandMap) f.get(Bukkit.getServer());
			}
		}
		catch (Exception e) {
			Logger.log(Logger.LogLevel.SEVERE, "Command registration has failed.", e, true);
		}

		String cmdString = this.config.getString("command", "trail");
		if (CM.getCommand(cmdString) != null) {
			Logger.log(Logger.LogLevel.WARNING, "A command under the name " + ChatColor.RED + "/" + cmdString + ChatColor.YELLOW + " already exists. Trail Command temporarily registered under " + ChatColor.RED + "/st:" + cmdString, true);
		}
		String adminCmdString = this.config.getString("adminCommand", "trailadmin");
		if (CM.getCommand(adminCmdString) != null) {
			Logger.log(Logger.LogLevel.WARNING, "A command under the name " + ChatColor.RED + "/" + adminCmdString + ChatColor.YELLOW + " already exists. Trail Command temporarily registered under " + ChatColor.RED + "/st:" + adminCmdString, true);
		}

		CustomCommand petCmd = new CustomCommand(cmdString);
		CM.register("st", petCmd);
		petCmd.setExecutor(new TrailCommand(cmdString));
		petCmd.setTabCompleter(new CommandComplete());
		this.cmdString = cmdString;

		CustomCommand petAdminCmd = new CustomCommand(adminCmdString);
		CM.register("st", petAdminCmd);
		petAdminCmd.setExecutor(new AdminCommand(adminCmdString));
		this.adminCmdString = adminCmdString;

		manager.registerEvents(new MenuListener(), this);
		manager.registerEvents(new MenuChatListener(), this);

		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			Logger.log(Logger.LogLevel.WARNING, "PluginMetrics (MCStats) has failed to start.", e, false);
		}

		this.checkForUpdates();
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
		}
		else if (type == ConfigType.DATA) {
			return this.dataConfig;
		}
		else if (type == ConfigType.LANG) {
			return this.langConfig;
		}
		return null;
	}

	public enum ConfigType {
		MAIN, DATA, LANG;
	}

	public void checkForUpdates() {
		if (this.getConfig(ConfigType.MAIN).getBoolean("autoUpdate", false) == true) {
			@SuppressWarnings("unused")
			Updater updater = new Updater(this, "sparktrail", this.getFile(), Updater.UpdateType.DEFAULT, true);
		} else {
			if (this.getConfig(ConfigType.MAIN).getBoolean("checkForUpdates", true) == true) {
				Updater updater = new Updater(this, "sparktrail", this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
				update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;
				if (this.update) {
					name = updater.getLatestVersionString();
					size = updater.getFileSize();
					ConsoleLogger.log(Logger.LogLevel.NORMAL, ChatColor.GREEN + "An update is available: " + this.name + " (" + this.size + " bytes).");
					ConsoleLogger.log(Logger.LogLevel.NORMAL, ChatColor.GREEN + "Type /stupdate to update.");
					if (updateCheck == false) {
						updateCheck = true;
					}
				}
			}
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("stupdate")) {
			if (sender.hasPermission("sparktrail.update")) {
				if (updateCheck == true) {
					@SuppressWarnings("unused")
					Updater updater = new Updater(this, "sparktrail", this.getFile(), Updater.UpdateType.NO_VERSION_CHECK, true);
					return true;
				}
				else {
					sender.sendMessage(this.prefix + ChatColor.GREEN + " An update is not available.");
					return true;
				}
			}
			else {
				sender.sendMessage(this.prefix + ChatColor.YELLOW + "sparktrail.update " + ChatColor.DARK_GREEN + "permission needed.");
				return true;
			}
		}
		return false;
	}
}