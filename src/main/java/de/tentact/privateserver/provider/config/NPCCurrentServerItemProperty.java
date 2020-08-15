package de.tentact.privateserver.provider.config;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 14.08.2020
    Uhrzeit: 15:31
*/

import java.util.ArrayList;
import java.util.List;

public class NPCCurrentServerItemProperty {

    private final String displayName;

    private final String materialName;
    private final byte subId;
    private final List<String> lore;
    private final int inventorySlot;

    public NPCCurrentServerItemProperty(String displayName, String materialName, byte subId, List<String> lore, int inventorySlot) {
        this.displayName = displayName;
        this.materialName = materialName;
        this.subId = subId;
        this.lore = new ArrayList<>(lore);
        this.inventorySlot = inventorySlot;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getMaterialName() {
        return this.materialName;
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
}
