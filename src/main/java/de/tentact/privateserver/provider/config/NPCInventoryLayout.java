package de.tentact.privateserver.provider.config;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 19.08.2020
    Uhrzeit: 12:36
*/

import java.util.ArrayList;
import java.util.List;

public class NPCInventoryLayout {

    private final String materialName;
    private final String displayName;
    private final byte subId;
    private final List<String> lore;

    public NPCInventoryLayout(String materialName, String displayName, byte subId, List<String> lore) {
        this.materialName = materialName;
        this.displayName = displayName;
        this.subId = subId;
        this.lore = new ArrayList<>(lore);
    }

    public String getMaterialName() {
        return this.materialName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public byte getSubId() {
        return this.subId;
    }

    public List<String> getLore() {
        return this.lore;
    }

}
