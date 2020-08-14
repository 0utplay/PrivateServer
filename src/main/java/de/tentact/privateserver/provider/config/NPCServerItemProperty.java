package de.tentact.privateserver.provider.config;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 04.08.2020
    Uhrzeit: 22:25
*/

import org.bukkit.ChatColor;

import java.util.List;

public class NPCServerItemProperty {

    private final String displayName, materialName, templateToStart, startPermission;
    private final byte subId;
    private final List<String> lore;
    private final int inventorySlot;
    private final boolean showIfNoPerms;
    private final boolean autoStopOnOwnerLeave;

    public NPCServerItemProperty(String displayName, String materialName, String templateToStart, String startPermission, byte subId, List<String> lore, int inventorySlot, boolean showIfNoPerms, boolean autoStopOnOwnerLeave) {
        this.displayName = displayName;
        this.materialName = materialName;
        this.templateToStart = templateToStart;
        this.startPermission = startPermission;
        this.subId = subId;
        this.lore = lore;
        this.inventorySlot = inventorySlot;
        this.showIfNoPerms = showIfNoPerms;
        this.autoStopOnOwnerLeave = autoStopOnOwnerLeave;
    }

    public String getDisplayName() {
        return ChatColor.translateAlternateColorCodes('&', this.displayName);
    }

    public String getMaterialName() {
        return this.materialName;
    }

    public String getTemplateToStart() {
        return this.templateToStart;
    }

    public String getStartPermission() {
        return this.startPermission;
    }

    public byte getSubId() {
        return this.subId;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public int getInventorySlot() {
        return this.inventorySlot;
    }

    public boolean isShowIfNoPerms() {
        return this.showIfNoPerms;
    }

    public boolean isAutoStopOnOwnerLeave() {
        return this.autoStopOnOwnerLeave;
    }
}
