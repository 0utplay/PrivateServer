package de.tentact.privateserver.provider.config;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 04.08.2020
    Uhrzeit: 22:16
*/

import java.util.Collection;

public class NPCInventoryCreateServerItems {

    private final Collection<NPCServerItemProperty> startItems;

    public NPCInventoryCreateServerItems(Collection<NPCServerItemProperty> startItems) {
        this.startItems = startItems;
    }

    public Collection<NPCServerItemProperty> getStartItems() {
        return this.startItems;
    }

}
