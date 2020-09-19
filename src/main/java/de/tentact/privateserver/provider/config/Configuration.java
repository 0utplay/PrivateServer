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
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;

public class Configuration {

    private Document document = new DefaultDocument();

    private final File configFile;

    public Configuration(PrivateServer privateServer) {
        this.configFile = new File(privateServer.getDataFolder(), "config.json");
        if (this.configFile.exists()) {
            privateServer.logInfo("Found config.json. Reading config.json...");
            this.document = Documents.jsonStorage().read(configFile);
            if (!this.document.contains("config")) {
                privateServer.logInfo("Config.json has no entry... Resetting to default...");
                this.writeDefaultConfiguration();
            }
            return;
        }
        try {
            Files.createDirectories(privateServer.getDataFolder().toPath());
        } catch (IOException e) {
            privateServer.getLogger().log(Level.WARNING, "While creating directories used by the plugin an exception occurred:");
            e.printStackTrace();
        }
        privateServer.logInfo("No config.json found...");
        privateServer.logInfo("Creating new config.json");
        this.writeDefaultConfiguration();
    }

    public PrivateServerConfig getPrivateServerConfig() {
        return this.document.get("config", PrivateServerConfig.class);
    }

    private PrivateServerConfig getDefaultServerConfig() {
        return new PrivateServerConfig(
                "PServer",
                "pserver.basecommand",
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
                        "&4PrivateServer",
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
                ));
    }

    /**
     * Rewrites the current {@link Configuration}
     *
     * @param privateServerConfig the config to set
     */
    public void writeConfiguration(PrivateServerConfig privateServerConfig) {
        this.document = new DefaultDocument("config", privateServerConfig);
        this.document.json().write(configFile);
    }

    private void writeDefaultConfiguration() {
        this.writeConfiguration(this.getDefaultServerConfig());
    }
}
