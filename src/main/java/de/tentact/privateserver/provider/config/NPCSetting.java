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

public class NPCSetting {

    private final String npcName;
    private final boolean shouldImitatePlayer;
    private final boolean shouldLookAtPlayer;
    private final String skinValue;
    private final String skinSignature;
    private NPCLocation npcLocation;

    /**
     * Settings of the spawned NPC
     *
     * @param shouldImitatePlayer if the npc shouldImitatePlayer
     * @param shouldLookAtPlayer  if the npc shouldLookAtPlayer
     * @param skinValue           the skin that should be applied to the NPC
     * @param skinSignature       the signature of the skin that should be applied to the NPC
     * @param npcLocation         the npcLocation to spawn the NPC at
     */
    public NPCSetting(String npcName, boolean shouldImitatePlayer, boolean shouldLookAtPlayer, String skinValue, String skinSignature, NPCLocation npcLocation) {
        this.npcName = npcName;
        this.shouldImitatePlayer = shouldImitatePlayer;
        this.shouldLookAtPlayer = shouldLookAtPlayer;
        this.skinValue = skinValue;
        this.skinSignature = skinSignature;
        this.npcLocation = npcLocation;
    }

    public boolean isImitatePlayer() {
        return this.shouldImitatePlayer;
    }

    public boolean isLookAtPlayer() {
        return this.shouldLookAtPlayer;
    }

    public String getSkinValue() {
        return this.skinValue;
    }

    public NPCLocation getNPCLocation() {
        return this.npcLocation;
    }

    public NPCSetting setNPCLocation(NPCLocation npcLocation) {
        this.npcLocation = npcLocation;
        return this;
    }

    public String getSkinSignature() {
        return this.skinSignature;
    }

    public String getNPCName() {
        return ChatColor.translateAlternateColorCodes('&', this.npcName);
    }
}
