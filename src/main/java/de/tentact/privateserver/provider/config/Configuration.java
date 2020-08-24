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
import de.tentact.privateserver.PrivateServer;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

public class Configuration {

    private Document document = new DefaultDocument();

    private final File configFile = new File("plugins/PrivateServer", "config.json");

    public Configuration(PrivateServer privateServer) {
        if (configFile.exists()) {
            privateServer.logInfo("Found config.json. Reading config.json...");
            document = Documents.jsonStorage().read(configFile);
            return;
        }
        configFile.getParentFile().mkdirs();
        privateServer.logInfo("No config.json found...");
        privateServer.logInfo("Creating new config.json");
        document.append("config", new PrivateServerConfig(
                "PServer",
                "pserver.basecommand",
                "&4PrivateServer",
                new NPCInventory(
                        "&4PrivateServer",
                        "pserver.opennpc",
                        9,
                        new NPCInventoryLayout(
                                "BLACK_STAINED_GLASS",
                                "",
                                (byte) -1,
                                Collections.singletonList("")
                        )
                ),
                new NPCSetting(
                        true,
                        true,
                        null,
                        null,
                        null
                ),

                new NPCCurrentServerItemProperty(
                        "Dein aktueller Server %displayname%",
                        "DIAMOND_BLOCK",
                        (byte) -1,
                        Collections.singletonList("Klicke um deinem bereits gestarten Server zu joinen"),
                        8),
                Collections.singletonList(
                        new NPCServerItemProperty(
                                "&5BedWars - Map",
                                "GRASS",
                                "TemplatePrefix/TemplateName",
                                "start.permission.template",
                                (byte) -1,
                                Arrays.asList("Click to start server", "%TEMPLATE%"),
                                0,
                                true,
                                true,
                                false)
                )
        )).json().write(configFile);
    }

    public PrivateServerConfig getPrivateServerConfig() {
        return document.get("config", PrivateServerConfig.class);
    }

    /**
     * Rewrites the current {@link Configuration}
     *
     * @param privateServerConfig the config to set
     */
    public void writeConfiguration(PrivateServerConfig privateServerConfig) {
        document = new DefaultDocument("config", privateServerConfig);
        document.json().write(configFile);
    }
}
