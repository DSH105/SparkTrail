package io.github.dsh105.sparktrail.config;

import io.github.dsh105.dshutils.config.YAMLConfig;
import io.github.dsh105.dshutils.config.options.Options;
import io.github.dsh105.sparktrail.particle.ParticleType;


public class ConfigOptions extends Options {

    public static ConfigOptions instance;

    public ConfigOptions(YAMLConfig config) {
        super(config);
        instance = this;
        this.setDefaults();
        this.setMaxTick();
    }

    public int maxTick = 0;

    public void setMaxTick() {
        for (String key : config.getConfigurationSection("effects").getKeys(false)) {
            int i = config.getInt("effects." + key + ".frequency", 20);
            if (this.maxTick < i) {
                this.maxTick = i;
            }
        }

        for (int i = maxTick; i >= maxTick; i++) {
            if (i % 20 == 0) {
                maxTick = i;
                break;
            }
        }
    }

    public boolean useSql() {
        return this.config.getBoolean("sql.use", false);
    }

    public boolean sqlOverride() {
        if (useSql()) {
            return this.config.getBoolean("sql.overrideFile");
        }
        return false;
    }

    @Override
    public void setDefaults() {
        set("command", "trail");

        set("primaryChatColour", "a");
        set("secondaryChatColour", "e");

        set("autoUpdate", false);
        set("checkForUpdates", true);

        set("sql.overrideFile", true, "If true, data saved to the MySQL Database will override data saved to a file");
        set("sql.use", false);
        set("sql.host", "localhost");
        set("sql.port", 3306);
        set("sql.database", "SparkTrail");
        set("sql.username", "none");
        set("sql.password", "none");

        set("autosave", true, "If true, SparkTrail will autosave all pet data to prevent data", "loss in the event of a server crash.");
        set("autosaveTimer", 180, "AutoSave interval (in seconds)");

        for (ParticleType pt : ParticleType.values()) {
            String name = pt.toString().toLowerCase();
            set("effects." + name + ".enable", true);
            set("effects." + name + ".frequency", 20);
            set("effects." + name + ".playType", "normal");
        }
        this.config.saveConfig();
    }
}