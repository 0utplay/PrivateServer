package de.tentact.privateserver.provider.config;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 04.08.2020
    Uhrzeit: 22:25
*/

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NPCServerItemProperty {

    private final String displayName, materialName, templateToStart, startPermission;
    private final byte subId;
    private final List<String> lore;
    private final int inventorySlot;
    private final boolean showIfNoPerms;
    private final boolean autoStopOnOwnerLeave;
    private final boolean autoStopIfEmpty;

    public NPCServerItemProperty(String displayName, String materialName, String templateToStart, String startPermission, byte subId, List<String> lore, int inventorySlot, boolean showIfNoPerms, boolean autoStopOnOwnerLeave, boolean autoStopIfEmpty) {
        this.displayName = displayName;
        this.materialName = materialName;
        this.templateToStart = templateToStart;
        this.startPermission = startPermission;
        this.subId = subId;
        this.lore = new ArrayList<>(lore);
        this.inventorySlot = inventorySlot;
        this.showIfNoPerms = showIfNoPerms;
        this.autoStopOnOwnerLeave = autoStopOnOwnerLeave;
        this.autoStopIfEmpty = autoStopIfEmpty;
    }

    public String getDisplayName() {
        return ChatColor.translateAlternateColorCodes('&', this.displayName)
                .replace("%TEMPLATE%", this.templateToStart)
                .replace("%TEMPLATE_PREFIX%", this.templateToStart.split("/")[0])
                .replace("%TEMPLATE_NAME%", this.templateToStart.split("/")[1]);
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
        Collections.replaceAll(lore, "%TEMPLATE%", this.templateToStart);
        Collections.replaceAll(lore, "%TEMPLATE_PREFIX%", this.templateToStart.split("/")[0]);
        Collections.replaceAll(lore, "%TEMPLATE_NAME%", this.templateToStart.split("/")[1]);
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

    public boolean isAutoStopIfEmpty() {
        return autoStopIfEmpty;
    }
}
