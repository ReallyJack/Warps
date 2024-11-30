package me.jack.warps;

import me.jack.warps.command.HomeCommand;
import me.jack.warps.command.SethomeCommand;
import me.jack.warps.command.WarpCommand;
import me.jack.warps.listener.Join;
import me.jack.warps.listener.Quit;
import me.jack.warps.warp.WarpsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Warps extends JavaPlugin {

    private WarpsManager warpsManager;

    @Override
    public void onEnable() {
        warpsManager = new WarpsManager(this);
        registerCommands();
        registerListeners();

        warpsManager.loadWarps();
    }

    @Override
    public void onDisable() {
        warpsManager.saveWarps();
    }

    private void registerCommands() {
        getCommand("warp").setExecutor(new WarpCommand(warpsManager));
        getCommand("sethome").setExecutor(new SethomeCommand(warpsManager));
        getCommand("home").setExecutor(new HomeCommand(warpsManager));
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new Join(), this);
        Bukkit.getPluginManager().registerEvents(new Quit(), this);
    }
}
