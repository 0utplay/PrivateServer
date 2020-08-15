package de.tentact.privateserver.service;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 05.08.2020
    Uhrzeit: 12:52
*/

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.ext.bridge.player.ICloudPlayer;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import de.dytanic.cloudnet.wrapper.Wrapper;

import java.util.UUID;

public class CurrentPrivateServiceUtil {

    private final IPlayerManager iPlayerManager = CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class);

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

    public boolean isAutoStopOnOwnerLeave() {
        return this.getProperty("autoStopOnOwnerLeave", Boolean.class);
    }

    public boolean isAutoStopOnEmpty() {
        return this.getProperty("autoStopOnEmpty", Boolean.class);
    }
}
