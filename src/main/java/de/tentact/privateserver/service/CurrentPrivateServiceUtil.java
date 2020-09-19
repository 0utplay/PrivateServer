package de.tentact.privateserver.service;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 05.08.2020
    Uhrzeit: 12:52
*/

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import de.dytanic.cloudnet.wrapper.Wrapper;

import java.util.UUID;

public class CurrentPrivateServiceUtil {

    private final IPlayerManager iPlayerManager = CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class);

    /**
     * Sends the owner to this server by fetching the attached properties
     */
    public void sendOwner() {
        if (!this.isPrivateServer()) {
            return;
        }
        UUID serverOwner = this.getProperty("serverowner", UUID.class);
        this.iPlayerManager.getPlayerExecutor(serverOwner).connect(Wrapper.getInstance().getCurrentServiceInfoSnapshot().getName());
    }

    /**
     * Checks if the server the plugin runs on is a PrivateServer
     * @return if the server is a PrivateServer
     */
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
