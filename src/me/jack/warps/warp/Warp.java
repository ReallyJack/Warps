package me.jack.warps.warp;

import org.bukkit.Location;

import java.util.UUID;

public class Warp extends OwnedLocation {

    private String name;

    public Warp(String name, Location location, UUID uuid) {
        super(location, uuid);

        this.name = name;
    }

    public String getName()  {
        return this.name;
    }

}
