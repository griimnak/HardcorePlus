package me.griimnak.hardcoreplus;

import me.griimnak.hardcoreplus.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

public class Commands implements CommandExecutor {
    private HardcorePlus plugin;

    public Commands(HardcorePlus plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("hardcoreplus")) {
            if(args.length < 1) {
                sender.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "HardcorePlus " + plugin.getDescription().getVersion() + " by griimnak");
                sender.sendMessage(ChatColor.GRAY + "Enhances the vanilla Minecraft hardcore experience.");
                sender.sendMessage(ChatColor.GOLD + "/hardcoreplus" + ChatColor.WHITE + "- This dialog.");
                sender.sendMessage(ChatColor.GOLD + "/hardcoreplus setmax <player> <hp>" + ChatColor.WHITE + "- Sets the max health of a player.");
                sender.sendMessage(ChatColor.GOLD + "/hardcoreplus disable" + ChatColor.WHITE + "- Disables the plugin.");
                return true;
            } else {
                if(!sender.hasPermission("hardcoreplus.admin")){
                    sender.sendMessage(""+ ChatColor.RED+"You don't have the permission to do this!!!");
                    return true;
                }
                if(args[0].equalsIgnoreCase("reload")) {
                    ConfigManager.reloadConfig();
                    sender.sendMessage(ChatColor.GREEN + "Configuration reloaded.");
                    return true;
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
                            player.setHealth(max_hp);
                            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(max_hp);
                            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Your max hp has been updated.");
                            sender.sendMessage(""+args[1]+"'s max hp updated successfully.");
                        }

                    } else {
                        sender.sendMessage("User '"+args[1]+"' not found.");
                    }
                    return true;

                } else if(args[0].equalsIgnoreCase("disable")) {
                    plugin.getServer().getPluginManager().disablePlugin(plugin);
                    sender.sendMessage(ChatColor.GREEN + "Disabled HardcorePlus.");
                    return true;
                }
            }
        }
        return false;
    }
}
