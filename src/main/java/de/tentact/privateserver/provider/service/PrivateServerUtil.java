package de.tentact.privateserver.provider.service;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 05.08.2020
    Uhrzeit: 12:51
*/

import de.dytanic.cloudnet.common.document.gson.JsonDocument;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceConfiguration;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.driver.service.ServiceTask;
import de.dytanic.cloudnet.driver.service.ServiceTemplate;
import de.tentact.privateserver.PrivateServer;
import de.tentact.privateserver.provider.config.PrivateServerConfig;

import java.util.Collection;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrivateServerUtil {

    private final PrivateServer privateServer;
    private final PrivateServerConfig privateServerConfig;
    private final CloudNetDriver cloudNetDriver = CloudNetDriver.getInstance();

    public PrivateServerUtil(PrivateServer privateServer) {
        this.privateServer = privateServer;
        this.privateServerConfig = this.privateServer.getConfiguration().getPrivateServerConfig();
    }

    public boolean startPrivateServer(UUID serverOwner, String templatePrefix, final String templateName) {
        if (this.hasPrivateServer(serverOwner)) {
            return false;
        }
        if (this.privateServer.getConfiguration().getPrivateServerConfig().getPrivateServerTask() == null) {
            return false;
        }
        ServiceTask serviceTask = this.privateServerConfig.getPrivateServerTask();

        if (serviceTask == null) {
            return false;
        }

        ServiceTemplate serviceTemplate = this.cloudNetDriver.getLocalTemplateStorageTemplates()
                .stream().filter(template -> template.getName().equalsIgnoreCase(templateName) && template.getPrefix().equalsIgnoreCase(templatePrefix)).findFirst().orElse(null);

        if (serviceTemplate == null) {
            return false;
        }

        JsonDocument document = new JsonDocument("serverowner", serverOwner).append("templatePrefix", templatePrefix).append("templateName", templateName);

        ServiceInfoSnapshot serviceInfoSnapshot = ServiceConfiguration
                .builder(serviceTask)
                .addTemplates(serviceTemplate)
                .properties(document)
                .build()
                .createNewService();
        if(serviceInfoSnapshot == null) {
            return false;
        }
        serviceInfoSnapshot.provider().startAsync();
        return true;
    }

    public boolean hasPrivateServer(UUID serverOwner) {
        for (ServiceInfoSnapshot cloudService : this.cloudNetDriver.getCloudServiceProvider().getCloudServices(this.privateServerConfig.getPrivateServerTaskName())) {
            if (cloudService.getProperties().contains("serverowner") && cloudService.getProperties().get("serverowner", UUID.class).equals(serverOwner)) {
                return true;
            }
        }
        return false;
    }

}
