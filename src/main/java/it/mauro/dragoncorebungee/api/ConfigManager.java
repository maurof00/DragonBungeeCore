package it.mauro.dragoncorebungee.api;


import it.mauro.dragoncorebungee.DragonCoreBungee;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigManager {

    private final DragonCoreBungee plugin;

    public ConfigManager(DragonCoreBungee plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        File configFile = new File(plugin.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            createDefaultConfig(configFile);
        }

        try {
            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            plugin.setBlacklist(config.getStringList("blacklist"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDefaultConfig(File configFile) {
        try {
            configFile.createNewFile();
            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            config.set("blacklist", getDefaultBlacklist());
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getDefaultBlacklist() {
        return List.of("cazzo", "merda");
    }
}

