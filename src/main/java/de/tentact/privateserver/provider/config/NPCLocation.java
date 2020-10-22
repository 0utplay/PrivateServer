package de.tentact.privateserver.provider.config;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 12.08.2020
    Uhrzeit: 17:01
*/

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class NPCLocation {

    private transient Location location;

    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;
    private final String worldName;

    public NPCLocation(Location location) {
        this(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), location.getWorld().getName());
    }

    public NPCLocation(double x, double y, double z, float yaw, float pitch, String worldName) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.worldName = worldName;
    }

    public Location getLocation() {
        if(this.location != null) {
            return this.location;
        }
        this.location = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
        return this.location;
    }
}
