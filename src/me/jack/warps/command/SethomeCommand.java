package me.jack.warps.command;

import me.jack.warps.warp.Home;
import me.jack.warps.warp.WarpsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SethomeCommand implements CommandExecutor {

    private WarpsManager warpsManager;

    public SethomeCommand(WarpsManager warpsManager) {
        this.warpsManager = warpsManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (args.length > 1) {
            player.sendMessage(ChatColor.RED + "Incorrect usage! /sethome");
            return true;
        }
        Home home = warpsManager.getHome(player.getUniqueId());

        if (home == null) {
            player.sendMessage(ChatColor.RED + "You do not have a home set! /sethome");
            return true;
        }

        warpsManager.getHomes().remove(home);
        warpsManager.getHomes().add(new Home(player.getLocation(), player.getUniqueId()));
        player.sendMessage(ChatColor.GRAY + "Home set!");

        return true;
    }
}
