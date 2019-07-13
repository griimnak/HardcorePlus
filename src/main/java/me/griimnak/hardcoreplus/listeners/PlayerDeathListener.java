package me.griimnak.hardcoreplus.listeners;

import me.griimnak.hardcoreplus.HardcorePlus;
import me.griimnak.hardcoreplus.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PlayerDeathListener implements Listener {
    HardcorePlus plugin;
    public PlayerDeathListener(HardcorePlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.setDeathMessage(ChatColor.RED + "" + ChatColor.BOLD + player.getName() + ConfigManager.config.getString("PermaDeathServerText"));
        player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + ConfigManager.config.getString("PermaDeathPlayerText"));

        updateStatsFile();

        // if perma ban enabled
        if(ConfigManager.config.getBoolean("PermaBanOnFinalDeathEnabled")) {
            // delay 10 ticks
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    // ban
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "ban " + player.getName() + " " + ConfigManager.config.getString("PermaBanText"));
                }
            }, 10);
        }
    }

    private static boolean loadStatsFile(File statsFile, Properties properties) {
        if(!statsFile.exists() || !statsFile.isFile()){
            properties=new Properties();
        }
        else {
            try {
                properties.load(new FileInputStream(statsFile));
            } catch (IOException e) {
                e.printStackTrace();
                return true;
            }
        }
        return false;
    }

    private boolean updateStatsFile() {
        File statsFile = new File(plugin.getDataFolder(),"stats.properties");
        Properties properties = new Properties();
        if(loadStatsFile(statsFile, properties)) return true;

        if(properties.getProperty("total-perm-dead-players") == null){
            properties.setProperty("total-perm-dead-players", "0");
        }

        int total=Integer.parseInt(properties.getProperty("total-perm-dead-players"));
        properties.setProperty("total-perm-dead-players", total + 1 + "");

        try {
            properties.store(new FileOutputStream(statsFile),"");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
