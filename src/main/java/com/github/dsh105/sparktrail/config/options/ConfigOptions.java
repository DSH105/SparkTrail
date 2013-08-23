package com.github.dsh105.sparktrail.config.options;

import com.github.dsh105.sparktrail.config.YAMLConfig;
import com.github.dsh105.sparktrail.particle.ParticleType;
import com.github.dsh105.sparktrail.util.StringUtil;

/**
 * Project by DSH105
 */

public class ConfigOptions extends Options{

	public ConfigOptions(YAMLConfig config) {
		super(config);
	}

	public

	@Override
	public void setDefaults() {
		set("command", "trail");

		set("autoUpdate", false);
		set("checkForUpdates", true);

		set("sql.overrideFile", true, "If true, data saved to the MySQL Database will override data saved to a file");
		set("sql.timeout", 30);
		set("sql.use", false);
		set("sql.host", "localhost");
		set("sql.port", 3306);
		set("sql.database", "SparkTrail");
		set("sql.username", "none");
		set("sql.password", "none");

		set("autosave", true, "If true, SparkTrail will autosave all pet data to prevent data", "loss in the event of a server crash.");
		set("autosaveTimer", 180, "AutoSave interval (in seconds)");

		for (ParticleType pt : ParticleType.values()) {
			String name = StringUtil.capitalise(pt.toString());
			set(name + ".enable", true);
			set(name + ".frequency", 20);
			set(name + ".playType", "normal");
		}
	}
}