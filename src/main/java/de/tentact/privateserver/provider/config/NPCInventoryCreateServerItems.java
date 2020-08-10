package de.tentact.privateserver.provider.config;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 04.08.2020
    Uhrzeit: 22:16
*/

import java.util.Collection;

public class NPCInventoryCreateServerItems {

    private final String name;
    private final int size;
    private final Collection<NPCServerItemProperty> startItems;

    public NPCInventoryCreateServerItems(String name, int size, Collection<NPCServerItemProperty> startItems) {
        this.name = name;
        this.size = size;
        this.startItems = startItems;
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public Collection<NPCServerItemProperty> getStartItems() {
        return this.startItems;
    }

}
