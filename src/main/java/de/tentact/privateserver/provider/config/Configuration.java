package de.tentact.privateserver.provider.config;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 04.08.2020
    Uhrzeit: 22:50
*/

import com.github.derrop.documents.DefaultDocument;
import com.github.derrop.documents.Document;
import com.github.derrop.documents.Documents;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configuration {

    private Document document = new DefaultDocument();

    public Configuration() {
        File configFile = new File("plugins/PrivateServer", "config.json");
        if (configFile.exists()) {
            Logger.getAnonymousLogger().log(Level.INFO, "Config already exists");
            document = Documents.jsonStorage().read(configFile);
            return;
        }
        configFile.getParentFile().mkdirs();
        Logger.getAnonymousLogger().log(Level.INFO, "Config created");
        document.append("config", new PrivateServerConfig(
                "PServer",
                "pserver.basecommand",
                new NPCInventory(
                        "Test",
                        1
                ),
                new NPCInventoryCreateServerItems(
                        "187",
                        1,
                        Collections.singletonList(
                                new NPCServerItemProperty(
                                        "DisplayName",
                                        "MaterialName",
                                        "TemplatePrefix/TemplateName",
                                        "start.permission.template",
                                        (byte) 0,
                                        Arrays.asList("Lore1", "Lore2"),
                                        0,
                                        true,
                                        true
                                )
                        )
                )
        )).json().write(configFile);
    }

    public PrivateServerConfig getPrivateServerConfig() {
        return document.get("config", PrivateServerConfig.class);
    }

}
