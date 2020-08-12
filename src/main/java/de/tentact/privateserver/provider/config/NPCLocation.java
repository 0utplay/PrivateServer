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

    public NPCLocation(Location location) {
        this.location = location;
    }

    public NPCLocation(String worldName, int x, int y, int z) {
        this(new Location(Bukkit.getWorld(worldName), x, y, z));
    }

    public Location getLocation() {
        return this.location;
    }
}
