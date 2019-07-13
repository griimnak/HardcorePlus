package me.griimnak.hardcoreplus.listeners;

import me.griimnak.hardcoreplus.HardcorePlus;
import me.griimnak.hardcoreplus.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class DragonDeathListener implements Listener {
    private HardcorePlus plugin;

    public DragonDeathListener(HardcorePlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEnderDragonKill(EntityDeathEvent event) {
        // if enabled in config
        if(ConfigManager.config.getBoolean("DragonKillRestoresMaxHealthEnabled")) {
            if(!(event.getEntity() instanceof EnderDragon)) { return; }
            // for players in server
            for(Player player: plugin.getServer().getOnlinePlayers()) {
                // if present in the end when dragon is killed
                if(player.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
                    player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + ConfigManager.config.getString("DragonKillText"));
                    // restore max health
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0D);
                    player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + ConfigManager.config.getString("MaxHealthRestoreText"));
                }
            }
        }
    }

}
