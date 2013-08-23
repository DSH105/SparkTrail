package com.github.dsh105.sparktrail;

import com.github.dsh105.sparktrail.config.YAMLConfig;
import com.github.dsh105.sparktrail.config.YAMLConfigManager;
import com.github.dsh105.sparktrail.data.AutoSave;
import com.github.dsh105.sparktrail.data.EffectHandler;
import com.github.dsh105.sparktrail.logger.ConsoleLogger;
import com.github.dsh105.sparktrail.mysql.RefreshConnection;
import com.github.dsh105.sparktrail.mysql.SQLConnection;
import com.github.dsh105.sparktrail.mysql.SQLEffectHandler;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Project by DSH105
 */

public class SparkTrail extends JavaPlugin {

	private static SparkTrail plugin;
	private YAMLConfigManager configManager;
	private YAMLConfig config;
	private YAMLConfig particleData;
	public AutoSave autoSave;

	public EffectHandler effectHandler;
	public SQLEffectHandler sqlHandler;
	public SQLConnection sqlCon;
	private RefreshConnection sqlRefresh;

	public String prefix = "" + ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "SparkTrail" + ChatColor.DARK_GREEN + "] " + ChatColor.RESET;
	public String cmdName = "trail";
	public String cmdNameA = "trailadmin";

	public void onEnable() {
		this.plugin = this;

		ConsoleLogger.initiate();
	}

	public void onDisable() {

	}

	public static SparkTrail getInstance() {
		return plugin;
	}
}