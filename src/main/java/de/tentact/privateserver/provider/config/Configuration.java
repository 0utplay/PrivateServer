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
    private final PrivateServerConfig privateServerConfig;

    public Configuration(PrivateServer privateServer) {
        this.configFile = new File(privateServer.getDataFolder(), "config.json");
        if (this.configFile.exists()) {
            privateServer.logInfo("Found config.json. Reading config.json...");
            this.document = Documents.jsonStorage().read(configFile);
            if (!this.document.contains("config")) {
                privateServer.logInfo("Config.json has no entry... Resetting to default...");
                this.writeDefaultConfiguration();
            }
            this.privateServerConfig = this.document.get("config", PrivateServerConfig.class);
            this.privateServerConfig.setServiceTask();
            return;
        }
        privateServer.logInfo("No config.json found...");
        privateServer.logInfo("Creating new config.json");
        try {
            Files.createDirectories(privateServer.getDataFolder().toPath());
            Files.createFile(this.configFile.toPath());
        } catch (IOException e) {
            privateServer.getLogger().log(Level.WARNING, "While creating directories used by the plugin an exception occurred:", e);
        }
        this.writeDefaultConfiguration();
        this.privateServerConfig = this.document.get("config", PrivateServerConfig.class);
        this.privateServerConfig.setServiceTask();
    }

    public PrivateServerConfig getPrivateServerConfig() {
        return this.privateServerConfig;
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
                        "Dein aktueller Server %SERVER%",
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
        this.document.json().write(this.configFile);
    }

    private void writeDefaultConfiguration() {
        this.writeConfiguration(this.getDefaultServerConfig());
    }
}
