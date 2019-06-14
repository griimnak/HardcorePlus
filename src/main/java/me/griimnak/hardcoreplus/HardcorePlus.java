package me.griimnak.hardcoreplus;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;

public final class HardcorePlus extends JavaPlugin implements Listener {
    private FileConfiguration config;
    private File cfile;
    
    @Override
    public void onEnable() {
        // config defaults
        config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();

        cfile = new File(getDataFolder(), "config.yml");

        // detect hardcore mode enabled in server.properties
        if(config.getBoolean("enforceHardcoreMode")) {
            if(!getServer().isHardcore()) {
                ConsoleCommandSender console = getServer().getConsoleSender();
                getServer().shutdown();
                console.sendMessage(ChatColor.RED + "[HardcorePlus] >> ---------------------------------------------------------------------------------");
                console.sendMessage(ChatColor.RED + "[HardcorePlus] >> Hardcore mode not enabled in server.properties! ");
                console.sendMessage(ChatColor.RED + "[HardcorePlus] >> Please set hardcore to true and generate a new world. ");
                console.sendMessage(ChatColor.RED + "[HardcorePlus] >> ---------------------------------------------------------------------------------");
                console.sendMessage(ChatColor.RED + "[HardcorePlus] >> ");
                console.sendMessage(ChatColor.RED + "[HardcorePlus] >> ");
                console.sendMessage(ChatColor.RED + "[HardcorePlus] >> ");
                console.sendMessage(ChatColor.RED + "[HardcorePlus] >> ");
                console.sendMessage(ChatColor.RED + "[HardcorePlus] >> ---------------------------------------------------------------------------------");
                console.sendMessage(ChatColor.RED + "[HardcorePlus] >> Hardcore mode not enabled in server.properties! ");
                console.sendMessage(ChatColor.RED + "[HardcorePlus] >> Please set hardcore to true and generate a new world. ");
                console.sendMessage(ChatColor.RED + "[HardcorePlus] >> ---------------------------------------------------------------------------------");
            }
        }
        System.out.println("[HardcorePlus] Welcome to griimnak's HardcorePlus plugin.");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void playerDamageReceive(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player damaged = (Player) e.getEntity();
            // shields
            if(!damaged.isBlocking()) {
                // blood effect
                if(config.getBoolean("bloodEffectEnabled")) {
                    damaged.getWorld().playEffect(damaged.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
                }

                double max_hp = damaged.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                if((damaged.getHealth()-e.getDamage()) <= 0.0D) {
                    if(max_hp - 4.0D > 0.0D) {
                        e.setCancelled(true);
                        damaged.setHealth(max_hp);
                        damaged.setSaturation(5);
                        damaged.setFoodLevel(20);
                        damaged.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(max_hp - 4.0D);
                        damaged.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + config.getString("healthLossText"));
                        // clear effects
                        damaged.getActivePotionEffects().clear();
                        damaged.setFireTicks(0);
                        // respawn
                        if(damaged.getBedSpawnLocation() != null) {
                            damaged.teleport(damaged.getBedSpawnLocation());
                            damaged.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + config.getString("bedRespawnText"));
                        } else {
                            damaged.teleport(damaged.getWorld().getSpawnLocation());
                            damaged.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + config.getString("noBedRespawnText"));
                        }
                        System.out.println(damaged.getDisplayName() + " has lost permanent health.");

                        // trippy effect
                        if(config.getBoolean("trippySoundEnabled")) {
                            damaged.getWorld().playEffect(damaged.getLocation(), Effect.ZOMBIE_CONVERTED_VILLAGER, 0);
                        }

                        if(config.getBoolean("trippyEffectEnabled")) {
                            damaged.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 220,1));
                            damaged.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100,0));
                        }

                        if(config.getBoolean("weaknessOnRespawn")) {
                            damaged.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 3500,1));
                        }

                    }
                }

            }

        }
    }

    @EventHandler
    public void Death(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.setDeathMessage(ChatColor.RED + "" +ChatColor.BOLD + player.getDisplayName() + config.getString("permaDeathServerText"));
        player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + config.getString("permaDeathPlayerText"));
        player.setGameMode(GameMode.SPECTATOR);
    }

    @EventHandler
    public void onEnderDragonKill(EntityDeathEvent event) {
        if(config.getBoolean("restoreHpOnDragonKill")) {
            // If Entity is Dragon
            if(event.getEntity() instanceof EnderDragon) {
                // For each player in the server
                for(Player player: getServer().getOnlinePlayers()) {
                    // If player is in the end, reward full HP
                    if(player.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
                        player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + config.getString("dragonKillText"));
                        // restore max health
                        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0D);
                        player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + config.getString("maxHpRestoreText"));
                    }
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("hardcoreplus")) {
            if(args.length < 1) {
                sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Hardcore+ "+ getDescription().getVersion() +" by griimnak");
                sender.sendMessage(ChatColor.GOLD + "Enhances the vanilla Minecraft hardcore experience by degrading players' max health on death.");
                sender.sendMessage(ChatColor.GOLD + "Once you've ran out of hearts, you die permanently, the dream is over.");
                return true;
            } else {
                if(!sender.hasPermission("hardcoreplus.admin")){
                    sender.sendMessage(""+ ChatColor.RED+"You don't have the permission to do this!!!");
                    return true;
                }
                if(args[0].equalsIgnoreCase("reload")) {
                    config = YamlConfiguration.loadConfiguration(cfile);
                    sender.sendMessage(ChatColor.GREEN + "Configuration reloaded.");
                }
                if(args[0].equalsIgnoreCase("setmax")) {
                    if(args.length < 3) {
                        sender.sendMessage("usage: /hardcoreplus setmax <player> <max_hp value (20 default)>");
                        return true;
                    }
                    if(getServer().getPlayer(args[1]) != null) {
                        double max_hp = 20.0D;
                        try {
                            max_hp = Double.parseDouble(args[2]);
                        } catch(NumberFormatException e) {
                            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Invalid max HP value.");
                            return true;
                        }
                        if(max_hp < 0.0D) {
                            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Max hp value should be 0.0 and above");
                        } else {
                            Player player = getServer().getPlayer(args[1]);
                            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(max_hp);
                            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Your max hp has been updated.");
                            sender.sendMessage(""+args[1]+"'s max hp updated successfully.");
                        }

                    } else {
                        sender.sendMessage("User '"+args[1]+"' not found.");
                    }
                    return true;

                } else if(args[0].equalsIgnoreCase("enable")) {
                        sender.sendMessage("Not yet implemented");
                } else if(args[0].equalsIgnoreCase("disable")) {
                        sender.sendMessage("Not yet implemented");
                }
            }
        }
        return false;
    }

    @Override
    public void onDisable() { System.out.println("[HardcorePlus] Goodbye."); }
}
