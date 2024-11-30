package me.jack.warps.command;

import me.jack.warps.warp.Home;
import me.jack.warps.warp.WarpsManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {

    private WarpsManager warpsManager;

    public HomeCommand(WarpsManager warpsManager) {
        this.warpsManager = warpsManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 1) {
                player.sendMessage(ChatColor.RED + "Incorrect usage! /warp <name>");
                return true;
            }

            Home home = warpsManager.getHome(player.getUniqueId());

            if (home == null) {
                player.sendMessage(ChatColor.RED + "You do not have a home set! /sethome");
                return true;
            }
            player.teleport(home.getLocation());

            player.sendMessage(ChatColor.GRAY + "Teleporting home...");
        }
        return true;
    }
}
