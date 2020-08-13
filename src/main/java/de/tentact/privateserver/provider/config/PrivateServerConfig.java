package de.tentact.privateserver.provider.config;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 04.08.2020
    Uhrzeit: 21:55
*/

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceTask;
import org.bukkit.ChatColor;

public class PrivateServerConfig {

    private final String privateServerTaskName, baseCommandPermission, npcName;
    private final NPCInventory npcInventory;
    private NPCSetting npcSetting;
    private final NPCInventoryCreateServerItems serverItems;

    public PrivateServerConfig(String privateServerTaskName, String baseCommandPermission, String npcName, NPCInventory npcInventory, NPCSetting npcSetting, NPCInventoryCreateServerItems serverItems) {
        this.privateServerTaskName = privateServerTaskName;
        this.baseCommandPermission = baseCommandPermission;
        this.npcName = npcName;
        this.npcSetting = npcSetting;
        this.npcInventory = npcInventory;
        this.serverItems = serverItems;
    }

    public NPCInventory getNPCInventory() {
        return this.npcInventory;
    }

    public NPCInventoryCreateServerItems getServerItems() {
        return this.serverItems;
    }

    public String getPrivateServerTaskName() {
        return this.privateServerTaskName;
    }

    public ServiceTask getPrivateServerTask() {
        return CloudNetDriver.getInstance().getServiceTaskProvider().getServiceTask(this.privateServerTaskName);
    }

    public String getBaseCommandPermission() {
        return this.baseCommandPermission;
    }

    public NPCSetting getNPCSettings() {
        return this.npcSetting;
    }

    public void setNPCSettings(NPCSetting npcSetting) {
        this.npcSetting = npcSetting;
    }

    public String getNPCName() {
        return ChatColor.translateAlternateColorCodes('&', npcName);
    }
}
