package me.griimnak.hardplus;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class HardPlus extends JavaPlugin implements Listener {
    // HardPlus! https://github.com/griimnak
    @Override
    public void onEnable() {
        System.out.println("[HardPlus] Welcome to griimnak's HardPlus plugin.");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void Death(PlayerDeathEvent event) {
        Player player = event.getEntity();
        double cur_hp = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double calc_hp = cur_hp - 2.0D;
        // for debugging
        // System.out.println(calc_hp);

        if(calc_hp > 0.0D) {
            player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + ChatColor.BOLD + "You permanently lost 1 heart, watch out!");
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(cur_hp - 2.0D);
        } else {
            event.setDeathMessage(ChatColor.RED + "" +ChatColor.BOLD + player.getDisplayName() + " is permanently dead!");
            player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "You are dead forever.");
            player.setGameMode(GameMode.SPECTATOR);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equals("hardplus")) {
            sender.sendMessage(ChatColor.GOLD + "Hardplus " + getDescription().getVersion() + " : By griimnak");
            sender.sendMessage(ChatColor.GRAY + "===================================");
            sender.sendMessage(ChatColor.GOLD + "Enhances the vanilla Hard difficulty by gradually decreasing players's max health on death.");
            sender.sendMessage(ChatColor.GOLD + "Once you've ran out of hearts, you die permanently (spectator mode). Sorta like Hardcore mode.");
        }
        return false;
    }

    @Override
    public void onDisable() { System.out.println("[HardPlus] Goodbye."); }
}
