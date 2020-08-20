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
import de.tentact.privateserver.i18n.I18N;
import de.tentact.privateserver.provider.command.PrivateServerCommand;
import de.tentact.privateserver.provider.config.Configuration;
import de.tentact.privateserver.provider.config.NPCServerItemProperty;
import de.tentact.privateserver.provider.config.NPCSetting;
import de.tentact.privateserver.provider.config.PrivateServerConfig;
import de.tentact.privateserver.provider.listener.PlayerNPCInteract;
import de.tentact.privateserver.provider.service.PrivateServerUtil;
import de.tentact.privateserver.service.CurrentPrivateServiceUtil;
import de.tentact.privateserver.service.listener.PlayerCommandPreprocessListener;
import de.tentact.privateserver.service.listener.PlayerQuitListener;
import org.bukkit.Material;
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
        I18N.createDefaultMessages(this);
        //Check if this plugin runs on a PrivateServer
        if (this.isPrivateServer()) {
            //Init this plugin as a PrivateServer
            initPrivateServer();
        } else {
            //Init this plugin as PrivateServer provider
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
        this.currentPrivateServiceUtil = new CurrentPrivateServiceUtil();
        this.currentPrivateServiceUtil.sendOwner();

        new PlayerQuitListener(this);
        new PlayerCommandPreprocessListener(this);

    }

    private boolean isPrivateServer() {
        return Wrapper.getInstance().getCurrentServiceInfoSnapshot().getProperties().contains("serverowner");
    }

    public CurrentPrivateServiceUtil getCurrentPrivateServiceUtil() {
        return this.currentPrivateServiceUtil;
    }

    //Spawn a NPC at the location given in the Configuration
    public void spawnNPC() {
        if (this.configuration.getPrivateServerConfig().getNPCSettings().getNPCLocation() != null) {
            NPCSetting settings = this.configuration.getPrivateServerConfig().getNPCSettings();
            //TODO: Input Player name to get skin and set it
            Profile profile = new Profile(UUID.randomUUID(), "187Juliorn", null);
            npcId = new NPC.Builder(profile)
                    .imitatePlayer(settings.isImitatePlayer())
                    .lookAtPlayer(settings.isLookAtPlayer())
                    .location(settings.getNPCLocation().getLocation())
                    .build(npcPool).getEntityId();
        }
    }
    //Remove a spawned NPC
    public void removeNPC() {
        this.npcPool.removeNPC(this.npcId);
    }

    public NPCPool getNPCPool() {
        return npcPool;
    }

    private void checkConfiguration() {
        PrivateServerConfig serverConfig = this.configuration.getPrivateServerConfig();

        if (serverConfig.getNPCInventory().getSize() < serverConfig.getStartItems().size()) {
            this.logInfo("Inventory size is smaller than the amount of startItems... Please check your config.json");
        }
        StringBuilder failedMaterials = new StringBuilder();
        for (NPCServerItemProperty itemProperty : serverConfig.getStartItems()) {
            if (Material.getMaterial(itemProperty.getMaterialName()) == null) {
                failedMaterials.append(itemProperty.getMaterialName()).append(" ");
            }
        }
        this.logInfo("This materials do not exist: "+failedMaterials +" Please check your config.json and update the materials. Use the right ones for your server version.");
    }

    public void logInfo(String message) {
        this.getLogger().log(Level.INFO, message);
    }
}
