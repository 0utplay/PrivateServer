package de.tentact.privateserver.provider.config;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 12.08.2020
    Uhrzeit: 17:28
*/

public class NPCSetting {

    private final boolean shouldImitatePlayer;
    private final boolean shouldLookAtPlayer;
    private NPCLocation npcLocation;

    public NPCSetting(boolean shouldImitatePlayer, boolean shouldLookAtPlayer, NPCLocation npcLocation) {
        this.shouldImitatePlayer = shouldImitatePlayer;
        this.shouldLookAtPlayer = shouldLookAtPlayer;
        this.npcLocation = npcLocation;
    }

    public boolean isImitatePlayer() {
        return this.shouldImitatePlayer;
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
