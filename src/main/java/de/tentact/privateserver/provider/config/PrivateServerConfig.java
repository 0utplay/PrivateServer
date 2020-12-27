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

package de.tentact.privateserver.provider.config;

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
