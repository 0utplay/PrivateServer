package de.tentact.privateserver.provider.config;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 04.08.2020
    Uhrzeit: 21:55
*/

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceTask;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class PrivateServerConfig {

    private final String privateServerTaskName, baseCommandPermission;
    private final NPCInventory npcInventory;
    private NPCSetting npcSetting;
    private final NPCCurrentServerItemProperty currentServerItem;
    private final Collection<NPCServerItemProperty> startItems;
    private final transient ServiceTask serviceTask;

    public PrivateServerConfig(String privateServerTaskName, String baseCommandPermission, NPCInventory npcInventory, NPCSetting npcSetting, NPCCurrentServerItemProperty currentServerItem, Collection<NPCServerItemProperty> startItems) {
        this.privateServerTaskName = privateServerTaskName;
        this.baseCommandPermission = baseCommandPermission;
        this.npcSetting = npcSetting;
        this.currentServerItem = currentServerItem;
        this.npcInventory = npcInventory;
        this.startItems = startItems;
        this.serviceTask = CloudNetDriver.getInstance().getServiceTaskProvider().getServiceTask(this.privateServerTaskName);
    }

    public NPCInventory getNPCInventory() {
        return this.npcInventory;
    }

    public Collection<NPCServerItemProperty> getStartItems() {
        return this.startItems;
    }

    public String getPrivateServerTaskName() {
        return this.privateServerTaskName;
    }
    @Nullable
    public ServiceTask getPrivateServerTask() {
       return this.serviceTask;
    }

    public String getBaseCommandPermission() {
        return this.baseCommandPermission;
    }

    public NPCSetting getNPCSettings() {
        return this.npcSetting;
    }

    public NPCCurrentServerItemProperty getCurrentServerItem() {
        return this.currentServerItem;
    }

    public void setNPCSettings(NPCSetting npcSetting) {
        this.npcSetting = npcSetting;
    }
}
