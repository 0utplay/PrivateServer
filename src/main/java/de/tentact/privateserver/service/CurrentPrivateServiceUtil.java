package de.tentact.privateserver.service;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 05.08.2020
    Uhrzeit: 12:52
*/

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceTemplate;
import de.dytanic.cloudnet.ext.bridge.player.ICloudPlayer;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import de.dytanic.cloudnet.wrapper.Wrapper;
import de.tentact.privateserver.PrivateServer;
import de.tentact.privateserver.provider.config.NPCServerItemProperty;
import de.tentact.privateserver.provider.config.PrivateServerConfig;

import java.util.Optional;
import java.util.UUID;

public class CurrentPrivateServiceUtil {

    private final IPlayerManager iPlayerManager = CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class);

    private final PrivateServerConfig privateServerConfig;

    public CurrentPrivateServiceUtil(PrivateServer privateServer) {
        this.privateServerConfig = privateServer.getConfiguration().getPrivateServerConfig();
    }

    public void sendOwner() {
        if (!this.isPrivateServer()) {
            return;
        }
        UUID serverOwner = this.getProperty("serverowner", UUID.class);
        ICloudPlayer cloudPlayer = this.iPlayerManager.getOnlinePlayer(serverOwner);

        if (cloudPlayer == null) {
            return;
        }
        this.iPlayerManager.getPlayerExecutor(cloudPlayer).connect(Wrapper.getInstance().getCurrentServiceInfoSnapshot().getName());
    }

    public boolean isPrivateServer() {
        return Wrapper.getInstance().getCurrentServiceInfoSnapshot().getProperties().contains("serverowner");
    }

    public UUID getOwner() {
        return this.getProperty("serverowner", UUID.class);
    }

    public <T> T getProperty(String name, Class<T> clazz) {
        return Wrapper.getInstance().getCurrentServiceInfoSnapshot().getProperties().get(name, clazz);
    }

    public Optional<NPCServerItemProperty> getCurrentServerItemProperty() {
        return this.privateServerConfig.getStartItems()
                .stream()
                .filter(npcServerItemProperty1 ->
                        npcServerItemProperty1.getTemplateToStart().equalsIgnoreCase(this.getCurrentServiceTemplate().getPrefix()+"/"+this.getCurrentServiceTemplate().getName()))
                .findFirst();

    }

    public ServiceTemplate getCurrentServiceTemplate() {
        return CloudNetDriver.getInstance().getLocalTemplateStorageTemplates()
                .stream()
                .filter(serviceTemplate -> serviceTemplate.getName().equalsIgnoreCase(this.getProperty("templateName", String.class))
                        && serviceTemplate.getPrefix().equalsIgnoreCase(this.getProperty("templatePrefix", String.class)))
                .findFirst()
                .orElse(null);
    }
}
