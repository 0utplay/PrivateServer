/*
 * MIT License
 *
 * Copyright (c) 2020 0utplay (Aldin Sijamhodzic)
 * Copyright (c) 2020 contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.tentact.privateserver.provider.config;

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

    /**
     * ServerItemProperty that is displayed in the npc's inventory
     *
     * @param displayName          the name of the startItem
     * @param materialName         the material of the startItem
     * @param templateToStart      the template of the startItem
     * @param startPermission      the needed permission to start this template
     * @param subId                the subID for legacy ItemStacks
     * @param lore                 the lore of the startItem
     * @param inventorySlot        the inventorySlot the startItem is placed at
     * @param showIfNoPerms        whether the item is shown if the player can not start it
     * @param autoStopOnOwnerLeave whether the server should stop if the owner leaves
     * @param autoStopIfEmpty      whether the server should stop if it is empty
     */
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
        return this.autoStopIfEmpty;
    }
}
