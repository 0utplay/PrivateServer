package de.tentact.privateserver;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 04.08.2020
    Uhrzeit: 21:55
*/

import de.dytanic.cloudnet.wrapper.Wrapper;
import de.tentact.privateserver.provider.command.PrivateServerCommand;
import de.tentact.privateserver.provider.config.Configuration;
import de.tentact.privateserver.provider.i18n.I18N;
import de.tentact.privateserver.service.CurrentPrivateServiceUtil;
import de.tentact.privateserver.provider.service.PrivateServerUtil;
import de.tentact.privateserver.service.listener.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrivateServer extends JavaPlugin {

    private Configuration configuration;
    private PrivateServerUtil privateServerUtil;
    private CurrentPrivateServiceUtil currentPrivateServiceUtil;

    public void onEnable() {
        if (this.isPrivateServer()) {
            initPrivateServer();
        } else {
            initProvider();
        }
        Objects.requireNonNull(this.getCommand("privateserver")).setExecutor(new PrivateServerCommand(this));
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public PrivateServerUtil getPrivateServerUtil() {
        return this.privateServerUtil;
    }

    void initProvider() {
        this.getLogger().log(Level.INFO, "Init provider");
        I18N.createDefaultMessages();
        this.configuration = new Configuration();
        this.privateServerUtil = new PrivateServerUtil(this);
    }

    void initPrivateServer() {
        this.getLogger().log(Level.INFO, "Init PServer");
        this.configuration = new Configuration();
        this.currentPrivateServiceUtil = new CurrentPrivateServiceUtil(this);
        this.currentPrivateServiceUtil.sendOwner();

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new PlayerQuitListener(this), this);

    }

    public boolean isPrivateServer() {
        return Wrapper.getInstance().getCurrentServiceInfoSnapshot().getProperties().contains("serverowner");
    }

    public CurrentPrivateServiceUtil getCurrentPrivateServiceUtil() {
        return this.currentPrivateServiceUtil;
    }
}
