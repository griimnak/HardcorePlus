package me.griimnak.hardcoreplus;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigClass {
    private static File file;
    private static FileConfiguration config;

    public static void setupConfig() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcorePlus").getDataFolder(), "config.yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(" Failed to create config");
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return config;
    }

    public static void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            System.out.println("Failed to save config");
        }

    }
}
