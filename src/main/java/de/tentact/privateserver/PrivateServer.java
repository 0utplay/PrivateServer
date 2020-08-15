package de.tentact.privateserver;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 04.08.2020
    Uhrzeit: 21:55
*/

import com.github.juliarn.npc.NPC;
import com.github.juliarn.npc.NPCPool;
import com.github.juliarn.npc.profile.Profile;
import de.dytanic.cloudnet.wrapper.Wrapper;
import de.tentact.privateserver.provider.command.PrivateServerCommand;
import de.tentact.privateserver.provider.config.Configuration;
import de.tentact.privateserver.provider.config.NPCServerItemProperty;
import de.tentact.privateserver.provider.config.NPCSetting;
import de.tentact.privateserver.provider.config.PrivateServerConfig;
import de.tentact.privateserver.provider.i18n.I18N;
import de.tentact.privateserver.provider.listener.PlayerNPCInteract;
import de.tentact.privateserver.provider.service.PrivateServerUtil;
import de.tentact.privateserver.service.CurrentPrivateServiceUtil;
import de.tentact.privateserver.service.listener.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

public class PrivateServer extends JavaPlugin {

    private Configuration configuration;
    private PrivateServerUtil privateServerUtil;
    private CurrentPrivateServiceUtil currentPrivateServiceUtil;

    private NPCPool npcPool;
    private int npcId;

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
        this.getLogger().log(Level.INFO, "Initialising PrivateServer Provider...");
        I18N.createDefaultMessages(this);
        this.configuration = new Configuration(this);
        this.checkConfiguration();
        this.privateServerUtil = new PrivateServerUtil(this);
        npcPool = new NPCPool(this);
        this.spawnNPC();
        new PlayerNPCInteract(this);
    }

    void initPrivateServer() {
        this.getLogger().log(Level.INFO, "Initialising PrivateServer");
        this.configuration = new Configuration(this);
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

    public void spawnNPC() {
        if (this.configuration.getPrivateServerConfig().getNPCSettings().getNPCLocation() != null) {
            NPCSetting settings = this.configuration.getPrivateServerConfig().getNPCSettings();
            Profile profile = new Profile(UUID.randomUUID(), "187Juliorn", null);
            npcId = new NPC.Builder(profile)
                    .imitatePlayer(settings.isImitatePlayer())
                    .lookAtPlayer(settings.isLookAtPlayer())
                    .location(settings.getNPCLocation().getLocation())
                    .build(npcPool).getEntityId();
        }
    }

    public void removeNPC() {
        this.npcPool.removeNPC(this.npcId);
    }

    public NPCPool getNPCPool() {
        return npcPool;
    }

    private void checkConfiguration() {
        PrivateServerConfig serverConfig = this.configuration.getPrivateServerConfig();

        if (serverConfig.getNPCInventory().getSize() < serverConfig.getStartItems().size()) {
            throw new UnsupportedOperationException("Inventory size is smaller than the amount of startItems");
        }
        for (NPCServerItemProperty itemProperty : serverConfig.getStartItems()) {
            if (Material.getMaterial(itemProperty.getMaterialName()) == null) {
                throw new UnsupportedOperationException("Material: " + itemProperty.getMaterialName() + " was not found. Please check your config.json");
            }
        }
    }

    public void logInfo(String message) {
        this.getLogger().log(Level.INFO, message);
    }
}
