package de.tentact.privateserver.provider.config;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 04.08.2020
    Uhrzeit: 21:56
*/

import org.bukkit.ChatColor;

public class NPCInventory {

    private final String name;
    private final String openPermission;
    private final int size;

    public NPCInventory(String name, String openPermission, int size) {
        this.name = name;
        this.openPermission = openPermission;
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    public String getName() {
        return ChatColor.translateAlternateColorCodes('&', this.name);
    }

    public String getOpenPermission() {
        return openPermission;
    }
}
