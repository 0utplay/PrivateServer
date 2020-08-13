package de.tentact.privateserver.provider.config;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 12.08.2020
    Uhrzeit: 17:28
*/

public class NPCSetting {

    private final boolean shouldImitadePlayer;
    private final boolean shouldLookAtPlayer;
    private NPCLocation npcLocation;

    public NPCSetting(boolean shouldImitadePlayer, boolean shouldLookAtPlayer, NPCLocation npcLocation) {
        this.shouldImitadePlayer = shouldImitadePlayer;
        this.shouldLookAtPlayer = shouldLookAtPlayer;
        this.npcLocation = npcLocation;
    }

    public boolean isImitadePlayer() {
        return this.shouldImitadePlayer;
    }

    public boolean isLookAtPlayer() {
        return this.shouldLookAtPlayer;
    }

    public NPCLocation getNPCLocation() {
        return this.npcLocation;
    }

    public NPCSetting setNPCLocation(NPCLocation npcLocation) {
        this.npcLocation = npcLocation;
        return this;
    }
}
