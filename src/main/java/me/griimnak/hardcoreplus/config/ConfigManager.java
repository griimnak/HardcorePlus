package me.griimnak.hardcoreplus.config;

import me.griimnak.hardcoreplus.HardcorePlus;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {
    private HardcorePlus plugin;

    private static File cfile;
    public static FileConfiguration config;

    public ConfigManager(HardcorePlus plugin) {
        this.plugin = plugin;
     }

    public void createConfig() {
        config = plugin.getConfig();
        // Copy defaults if not present
        config.options().copyDefaults(true);
        plugin.saveDefaultConfig();

        cfile = new File(plugin.getDataFolder(), "config.yml");
        plugin.getLogger().info("Config loaded.");
    }

    public static void reloadConfig() {
         // re assign config to current cfile
         config = YamlConfiguration.loadConfiguration(cfile);
    }
}
