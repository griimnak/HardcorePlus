package me.griimnak.hardcoreplus;

import me.griimnak.hardcoreplus.config.ConfigManager;
import me.griimnak.hardcoreplus.listeners.DragonDeathListener;
import me.griimnak.hardcoreplus.listeners.PlayerDamageListener;
import me.griimnak.hardcoreplus.listeners.PlayerDeathListener;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class HardcorePlus extends JavaPlugin {
    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        this.getLogger().info(ChatColor.YELLOW + "Goodbye");
    }

    private void init() {
        this.getLogger().info(ChatColor.GREEN + "Hello Minecraft!");
        new ConfigManager(this).createConfig(); // load conf
        checkHardcore(); // enforce hardcore mode
        regEvents(); // register event listeners

        // listen for commands
        this.getCommand("hardcoreplus").setExecutor(new Commands(this));
    }

    private void checkHardcore() {
        // if server is not hardcore
        if(!getServer().isHardcore()) {
            // warn
            getLogger().warning(ChatColor.YELLOW + "Please set hardcore to true in server.properties!");
            // disable plugin
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void regEvents() {
        // HardcorePlus event listeners
        regEvent(new PlayerDamageListener(this));
        regEvent(new PlayerDeathListener(this));
        regEvent(new DragonDeathListener(this));
    }

    private void regEvent(Listener event) {
        // Event register function
        getServer().getPluginManager().registerEvents(event, this);
    }

}
