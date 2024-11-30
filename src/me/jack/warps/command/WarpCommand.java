package me.jack.warps.command;

import me.jack.warps.warp.Warp;
import me.jack.warps.warp.WarpsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WarpCommand implements CommandExecutor {

    private WarpsManager warpsManager;

    public WarpCommand(WarpsManager warpsManager) {
        this.warpsManager = warpsManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length < 1) {
                player.sendMessage(ChatColor.RED + "Incorrect usage! /warp <name>");
                return true;
            }

            if (args[0].equalsIgnoreCase("set")) {

                if (args.length == 1) {
                    player.sendMessage(ChatColor.RED + "Incorrect usage! /warp set <name>");
                    return true;
                }

                if (args.length > 2) {
                    player.sendMessage(ChatColor.RED + "Incorrect format! Warp names can not have spaces.");
                    return true;
                }

                Warp warp = warpsManager.getWarp(player.getUniqueId(), args[1]);

                if (warp != null) {
                    player.sendMessage(ChatColor.RED + "You already have a warp with this name!");
                    return true;
                }
                warpsManager.getWarps().add(new Warp(args[1], player.getLocation(), player.getUniqueId()));
                player.sendMessage(ChatColor.GRAY + "Warp " + args[1] + " has been set!");

            } else if (args[0].equalsIgnoreCase("remove")) {

                if (args.length == 1) {
                    player.sendMessage(ChatColor.RED + "Incorrect usage! /warp remove <name>");
                    return true;
                }

                if (args.length > 2) {
                    player.sendMessage(ChatColor.RED + "Incorrect format! Warp names can not have spaces.");
                    return true;
                }

                Warp warp = warpsManager.getWarp(player.getUniqueId(), args[1]);

                if (warp == null) {
                    player.sendMessage(ChatColor.RED + "You do not have a warp with this name!");
                    return true;
                }
                warpsManager.getWarps().remove(warpsManager.getWarp(player.getUniqueId(), args[1]));
                player.sendMessage(ChatColor.GRAY + "Warp " + args[1] + " has been removed!");

            } else if (args[0].equalsIgnoreCase("list")) {

                if (args.length > 1) {
                    player.sendMessage(ChatColor.RED + "Incorrect usage! /warp list");
                    return true;
                }

                List<String> list = new ArrayList<>();

                for (Warp warps : warpsManager.getWarps()) {
                    list.add(warps.getName());
                }
                player.sendMessage(ChatColor.GRAY + "Your warps(" + warpsManager.getWarps().size() + "): " + list);

            } else {

                if (args.length > 1) {
                    player.sendMessage(ChatColor.RED + "Incorrect usage! /warp <name>");
                    return true;
                }

                Warp warp = warpsManager.getWarp(player.getUniqueId(), args[0]);

                if (warp == null) {
                    player.sendMessage(ChatColor.RED + "You do not have a warp with this name!");
                    return true;
                }
                player.teleport(warp.getLocation());
                player.sendMessage(ChatColor.GRAY + "Warped to " + warp.getName());
            }
        }
        return true;
    }
}
