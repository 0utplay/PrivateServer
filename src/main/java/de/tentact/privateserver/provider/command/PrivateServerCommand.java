package de.tentact.privateserver.provider.command;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 05.08.2020
    Uhrzeit: 13:59
*/

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.driver.service.ServiceTemplate;
import de.tentact.languageapi.LanguageAPI;
import de.tentact.languageapi.player.LanguagePlayer;
import de.tentact.languageapi.player.PlayerExecutor;
import de.tentact.privateserver.PrivateServer;
import de.tentact.privateserver.provider.config.NPCLocation;
import de.tentact.privateserver.provider.config.NPCServerItemProperty;
import de.tentact.privateserver.provider.config.NPCSetting;
import de.tentact.privateserver.provider.i18n.I18N;
import de.tentact.privateserver.provider.config.PrivateServerConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PrivateServerCommand implements CommandExecutor {

    private final PrivateServerConfig privateServerConfig;
    private final PrivateServer privateServer;
    private final PlayerExecutor playerExecutor = LanguageAPI.getInstance().getPlayerExecutor();
    private final CloudNetDriver cloudNetDriver = CloudNetDriver.getInstance();

    public PrivateServerCommand(PrivateServer privateServer) {
        this.privateServer = privateServer;
        this.privateServerConfig = privateServer.getConfiguration().getPrivateServerConfig();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        LanguagePlayer languagePlayer = this.playerExecutor.getLanguagePlayer(player.getUniqueId());
        if (languagePlayer == null) {
            return false;
        }
        if (!player.hasPermission(privateServerConfig.getBaseCommandPermission())) {
            languagePlayer.sendMessage(I18N.NO_BASECOMMAND_PERMISSION);
            return false;
        }
        if (args.length < 2) {
            languagePlayer.sendMessage(I18N.COMMAND_HELP);
            return false;
        }
        if (this.privateServer.getCurrentPrivateServiceUtil() != null) {
            languagePlayer.sendMessage(I18N.NO_BASECOMMAND_ON_PSERVER);
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "create":
                if (this.hasPrivateServer(player.getUniqueId())) {
                    languagePlayer.sendMessage(I18N.PLAYER_ALREADY_HAS_PSERVER);
                }

                String template = args[1];
                if (!template.matches("([A-Za-z0-9]+\\/[A-Za-z0-9]+)")) {
                    languagePlayer.sendMessage(I18N.WRONG_TEMPLATE_FORMAT);
                    return false;
                }


                String templatePrefix = template.split("/")[0];
                String templateName = template.split("/")[1];
                NPCServerItemProperty serverItem = this.privateServerConfig.getServerItems()
                        .getStartItems()
                        .stream()
                        .filter(npcServerItemProperty -> npcServerItemProperty.getTemplateToStart().equalsIgnoreCase(template)).findFirst().orElse(null);

                if (serverItem == null) {
                    languagePlayer.sendMessage(I18N.TEMPLATE_NOT_FOUND
                            .replace("%TEMPLATE%", template)
                            .replace("%TEMPLATE_NAME%", templateName)
                            .replace("%TEMPLATE_PREFIX%", templatePrefix));
                    return false;
                }

                if (!player.hasPermission(serverItem.getStartPermission())) {
                    languagePlayer.sendMessage(I18N.NO_TEMPLATE_START_PERMISSION
                            .replace("%TEMPLATE%", template)
                            .replace("%TEMPLATE_NAME%", templateName)
                            .replace("%TEMPLATE_PREFIX%", templatePrefix));
                    return false;
                }

                ServiceTemplate serviceTemplate = this.cloudNetDriver.getLocalTemplateStorageTemplates()
                        .stream()
                        .filter(sTemplate -> sTemplate.getPrefix().equalsIgnoreCase(templatePrefix) && sTemplate.getName().equalsIgnoreCase(templateName)).findFirst().orElse(null);

                if (serviceTemplate == null) {
                    languagePlayer.sendMessage(I18N.TEMPLATE_NOT_FOUND
                            .replace("%TEMPLATE%", template)
                            .replace("%TEMPLATE_NAME%", templateName)
                            .replace("%TEMPLATE_PREFIX%", templatePrefix));
                    return false;
                }

                boolean started = this.privateServer.getPrivateServerUtil().startPrivateServer(languagePlayer.getUniqueId(), templatePrefix, templateName);
                if (started) {
                    languagePlayer.sendMessage(I18N.STARTING_PSERVER
                            .replace("%TEMPLATE%", template)
                            .replace("%TEMPLATE_NAME%", templateName)
                            .replace("%TEMPLATE_PREFIX%", templatePrefix));
                    return true;
                }
                languagePlayer.sendMessage(I18N.STARTING_PSERVER_ERROR);
                return false;
            case "npc": //pserver npc delete || //pserver npc create imitadeplayer lookatPlayer
                if (args[1].equalsIgnoreCase("delete")) {
                    this.privateServer.removeNPC();
                    NPCSetting currentNPCSetting = this.privateServerConfig.getNPCSettings();
                    this.privateServerConfig.setNPCSettings(currentNPCSetting.setNPCLocation(null));
                    this.privateServer.getConfiguration().writeConfiguration(this.privateServerConfig);
                } else if (args[1].equalsIgnoreCase("create")) {
                    if (this.privateServer.getNPCPool().getNPCs().size() == 0) {
                        this.privateServer.removeNPC();
                    }
                    if(args.length < 4) {
                        languagePlayer.sendMessage(I18N.COMMAND_HELP);
                        return false;
                    }
                    if (!this.isBoolean(args[2])|| !this.isBoolean(args[3])) {
                        languagePlayer.sendMessage(I18N.COMMAND_HELP);
                        return false;
                    }
                    boolean imitadePlayer = Boolean.parseBoolean(args[2]);
                    boolean lookAtPlayer = Boolean.parseBoolean(args[3]);
                    NPCSetting setting = new NPCSetting(imitadePlayer, lookAtPlayer, new NPCLocation(player.getLocation()));
                    this.privateServerConfig.setNPCSettings(setting);
                    this.privateServer.getConfiguration().writeConfiguration(this.privateServerConfig);
                    this.privateServer.spawnNPC();
                } else {
                    languagePlayer.sendMessage(I18N.COMMAND_HELP);
                }
        }
        return false;
    }

    public boolean hasPrivateServer(UUID serverOwner) {
        for (ServiceInfoSnapshot cloudService : this.cloudNetDriver.getCloudServiceProvider().getCloudServices(this.privateServerConfig.getPrivateServerTaskName())) {
            if (cloudService.getProperties().contains("serverowner") && cloudService.getProperties().get("serverowner", UUID.class).equals(serverOwner)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBoolean(String input) {
        return input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false");
    }

}
