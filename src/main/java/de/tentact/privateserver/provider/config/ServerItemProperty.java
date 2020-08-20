package de.tentact.privateserver.provider.config;

import java.util.List;

/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 20.08.2020
    Uhrzeit: 16:38
*/

public interface ServerItemProperty {

    String getDisplayName();

    String getMaterialName();

    byte getSubId();

    List<String> getLore();

    int getInventorySlot();

}
