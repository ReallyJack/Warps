package me.jack.warps.warp;

import me.jack.warps.Warps;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WarpsManager {

    private Warps instance;
    private ArrayList<Warp> warpList = new ArrayList<>();
    private ArrayList<Home> homeList = new ArrayList<>();

    public WarpsManager(Warps instance) {
        this.instance = instance;
    }

    public Home getHome(UUID uuid) {
        for (Home home : getHomes()) {
            if (home.getOwner().equals(uuid)) {
                return home;
            }
        }
        return null;
    }

    public Warp getWarp(UUID uuid, String name) {
        for (Warp warp : getWarps()) {
            if (warp.getName().equals(name) && warp.getOwner().equals(uuid)) {
                return warp;
            }
        }
        return null;
    }

    public void loadWarp(UUID uuid) {
        File file = new File(instance.getDataFolder(), "warps.yml");

        if (!file.exists()) return;

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (String ownerUUIDString : config.getKeys(false)) {
            if (ownerUUIDString.equals(uuid)) {
                ConfigurationSection ownerSection = config.getConfigurationSection(ownerUUIDString);
                ConfigurationSection warpSection = ownerSection.getConfigurationSection("warps");

                UUID ownerUUID = UUID.fromString(ownerUUIDString);

                for (String warpName : warpSection.getKeys(false)) {
                    Location warpLocation = warpSection.getLocation(warpName);

                    warpList.add(new Warp(warpName, warpLocation, ownerUUID));
                }

                Location homeLocation = ownerSection.getLocation("home");

                homeList.add(new Home(homeLocation, ownerUUID));
            }
        }
    }

    public void loadWarps() {
        File file = new File(instance.getDataFolder(), "warps.yml");

        if (!file.exists()) return;

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (String ownerUUIDString : config.getKeys(false)) {
            ConfigurationSection ownerSection = config.getConfigurationSection(ownerUUIDString);
            ConfigurationSection warpSection = ownerSection.getConfigurationSection("warps");

            UUID ownerUUID = UUID.fromString(ownerUUIDString);

            for (String warpName : warpSection.getKeys(false)) {
                Location warpLocation = warpSection.getLocation(warpName);

                //Location.deserialize((Map<String, Object>) warpSection.get(warpName));

                warpList.add(new Warp(warpName, warpLocation, ownerUUID));
            }

            Location homeLocation = ownerSection.getLocation("home");

            homeList.add(new Home(homeLocation, ownerUUID));
        }
    }

    public void saveWarp(UUID uuid) {
        File file = new File(instance.getDataFolder(), "warps.yml");

        if (!file.exists()) {
            instance.getDataFolder().mkdir();
            try {
                file.createNewFile();
            }catch(IOException ex) {
                ex.printStackTrace();
            }
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (Warp warp : getWarps()) {
            if (warp.getOwner().equals(uuid)) {
                Location loc = warp.getLocation();
                String name = warp.getName();

                config.set(uuid + ".warps." + name, loc);
            }
        }

        for (Home home : getHomes()) {
            if (home.getOwner().equals(uuid)) {
                Location loc = home.getLocation();

                config.set(uuid + ".home", loc);
            }
        }

        try {
            config.save(file);
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveWarps() {
        File file = new File(instance.getDataFolder(), "warps.yml");

        if (!file.exists()) {
            instance.getDataFolder().mkdir();
            try {
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (Warp warp : getWarps()) {
            UUID uuid = warp.getOwner();
            Location loc = warp.getLocation();
            String name = warp.getName();

            config.set(uuid + ".warps." + name, loc);
        }

        for (Home home : getHomes()) {
            UUID uuid = home.getOwner();
            Location loc = home.getLocation();

            config.set(uuid + ".home", loc);
        }

        try {
            config.save(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        warpList.clear();
        homeList.clear();
    }

    public List<Warp> getWarps() {
        return warpList;
    }

    public List<Home> getHomes() {
        return homeList;
    }

}
