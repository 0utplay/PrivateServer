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

package de.tentact.privateserver;

import de.dytanic.cloudnet.wrapper.Wrapper;
import de.tentact.privateserver.provider.command.PrivateServerCommand;
import de.tentact.privateserver.provider.config.Configuration;
import de.tentact.privateserver.provider.config.NPCServerItemProperty;
import de.tentact.privateserver.provider.config.PrivateServerConfig;
import de.tentact.privateserver.provider.listener.PlayerNPCInteract;
import de.tentact.privateserver.provider.service.PrivateServerUtil;
import de.tentact.privateserver.service.CurrentPrivateServiceUtil;
import de.tentact.privateserver.service.listener.PlayerCommandPreprocessListener;
import de.tentact.privateserver.service.listener.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class PrivateServer extends JavaPlugin {

    private Configuration configuration;
    private PrivateServerUtil privateServerUtil;
    private CurrentPrivateServiceUtil currentPrivateServiceUtil;

    public void onEnable() {
        //Check if this plugin runs on a PrivateServer
        if (this.isPrivateServer()) {
            //Init this plugin as a PrivateServer
            this.initPrivateServer();
        } else {
            //Init this plugin as PrivateServer provider
            this.initProvider();
        }
        this.getCommand("privateserver").setExecutor(new PrivateServerCommand(this));
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public PrivateServerUtil getPrivateServerUtil() {
        return this.privateServerUtil;
    }

    private void initProvider() {
        this.getLogger().info("Initialising PrivateServer Provider...");
        this.configuration = new Configuration(this);
        this.checkConfiguration();
        this.privateServerUtil = new PrivateServerUtil(this);
        new PlayerNPCInteract(this);
    }

    private void initPrivateServer() {
        this.getLogger().info("Initialising PrivateServer...");
        this.configuration = new Configuration(this);
        this.currentPrivateServiceUtil = new CurrentPrivateServiceUtil();
        Bukkit.getScheduler().runTaskLaterAsynchronously(this,
                () -> this.currentPrivateServiceUtil.sendOwner(), 20L);

        new PlayerQuitListener(this);
        new PlayerCommandPreprocessListener(this);

    }

    private boolean isPrivateServer() {
        return Wrapper.getInstance().getCurrentServiceInfoSnapshot().getProperties().contains("serverowner");
    }

    public CurrentPrivateServiceUtil getCurrentPrivateServiceUtil() {
        return this.currentPrivateServiceUtil;
    }

    private void checkConfiguration() {
        PrivateServerConfig serverConfig = this.configuration.getPrivateServerConfig();

        if (serverConfig.getNPCInventory().getSize() < serverConfig.getStartItems().size()) {
            this.logInfo("Inventory size is smaller than the amount of startItems... Please check your config.json");
        }
        StringBuilder failedMaterials = new StringBuilder();
        for (NPCServerItemProperty itemProperty : serverConfig.getStartItems()) {
            if (Material.getMaterial(itemProperty.getMaterialName()) == null) {
                failedMaterials.append(itemProperty.getMaterialName()).append(", ");
            }
        }
        if (!failedMaterials.toString().isEmpty()) {
            this.logInfo("This materials do not exist: " + failedMaterials + " Please check your config.json and update the materials. Use the right ones for your server version.");
        }

    }

    public void logInfo(String message) {
        this.getLogger().info(message);
    }
}
