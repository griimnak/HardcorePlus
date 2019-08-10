package me.griimnak.hardcoreplus;

import me.griimnak.hardcoreplus.config.ConfigManager;
import me.griimnak.hardcoreplus.listeners.DragonDeathListener;
import me.griimnak.hardcoreplus.listeners.PlayerDamageListener;
import me.griimnak.hardcoreplus.listeners.PlayerDeathListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class HardcorePlus extends JavaPlugin {
    // since disabling the plugin is buggy, i made my own state handler.
    State state = new State();

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Goodbye");
    }

    private void init() {
        this.getLogger().info("Hello Minecraft!");
        new ConfigManager(this).createConfig(); // load conf

        // enforce hardcore
        if (checkHardcore()) {
            regEvents();
        } else {
            getLogger().warning("Hardcore mode not set, not registering event listeners.");
        }

        // listen for commands
        this.getCommand("hardcoreplus").setExecutor(new Commands(this));
    }

    private boolean checkHardcore() {
        // if server is not hardcore
        if(!getServer().isHardcore()) {
            // warn
            getLogger().warning("Please set hardcore to true in server.properties!");
            // disable plugin
            state.set(false);
            // getServer().getPluginManager().disablePlugin(this);

            return false;
        }
        return true;
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
