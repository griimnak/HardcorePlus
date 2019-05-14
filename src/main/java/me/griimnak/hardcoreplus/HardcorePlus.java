package me.griimnak.hardcoreplus;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class HardcorePlus extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        System.out.println("[HardcorePlus] Welcome to griimnak's HardcorePlus plugin.");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        // Test if entity is a Player
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            // Calculate health difference
            double result_hp = player.getHealth() - event.getDamage();
            // Get current max health value
            double max_hp = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            // If calculated health difference is less then or equal to 0
            if (result_hp <= 0.0D) {
                double result_max_hp = max_hp - 4.0D;
                if(result_max_hp > 0.0D) {
                    // Simulate full health
                    player.setHealth(max_hp);
                    // Simulate full food level
                    player.setSaturation(5);
                    player.setFoodLevel(20);
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(max_hp - 4.0D);
                    player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You have permanently lost health.");
                    // respawn
                    if(player.getBedSpawnLocation() != null) {
                        player.teleport(player.getBedSpawnLocation());
                        player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You awake from your dream in confusion.");
                    } else {
                        player.teleport(getServer().getWorld("world").getSpawnLocation());
                        player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "With no place of rest set, you awake from your dream, in familiar land.");
                    }
                    // trippy effect
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 220,1));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 600,1));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100,0));
                    return;
                } else {
                    // Die
                    return;
                }
            }
        } else {
            return;
        }
    }

    @EventHandler
    public void Death(PlayerDeathEvent event) {
        Player player = event.getEntity();

           event.setDeathMessage(ChatColor.RED + "" +ChatColor.BOLD + player.getDisplayName() + " is permanently dead!");
           player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "You are dead forever.");
           player.setGameMode(GameMode.SPECTATOR);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equals("hardcoreplus")) {
            sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Hardcore+ "+ getDescription().getVersion() +" by griimnak");
            sender.sendMessage(ChatColor.GOLD + "Enhances the vanilla Minecraft hardcore experience by degrading players' max health on death.");
            sender.sendMessage(ChatColor.GOLD + "Once you've ran out of hearts, you die permanently, the dream is over.");
        }
        return false;
    }

    @Override
    public void onDisable() { System.out.println("[HardcorePlus] Goodbye."); }
}
