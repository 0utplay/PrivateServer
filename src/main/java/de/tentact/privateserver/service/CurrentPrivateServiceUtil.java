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

package de.tentact.privateserver.service;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import de.dytanic.cloudnet.wrapper.Wrapper;

import java.util.UUID;

public class CurrentPrivateServiceUtil {

    private final IPlayerManager playerManager = CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class);

    /**
     * Sends the owner to this server by fetching the attached properties
     */
    public void sendOwner() {
        UUID serverOwner = this.getOwner();
        if (serverOwner == null) {
            return;
        }
        this.playerManager
                .getPlayerExecutor(serverOwner)
                 .connect(Wrapper.getInstance().getServiceId().getName());
    }

    /**
     * Checks if the server the plugin runs on is a PrivateServer
     *
     * @return if the server is a PrivateServer
     */
    public boolean isPrivateServer() {
        return this.getOwner() != null;
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
