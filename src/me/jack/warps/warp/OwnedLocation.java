package me.jack.warps.warp;

import org.bukkit.Location;

import java.util.UUID;

public abstract class OwnedLocation {

    private Location location;
    private UUID uuid;

    public OwnedLocation(Location location, UUID uuid) {
        this.location = location;
        this.uuid = uuid;
    }

    public Location getLocation() {
        return this.location;
    }

    public UUID getOwner() {
        return this.uuid;
    }
}
