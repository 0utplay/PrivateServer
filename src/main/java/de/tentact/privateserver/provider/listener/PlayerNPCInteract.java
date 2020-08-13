package de.tentact.privateserver.provider.listener;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 13.08.2020
    Uhrzeit: 10:50
*/

import com.github.juliarn.npc.event.PlayerNPCInteractEvent;
import de.tentact.languageapi.LanguageAPI;
import de.tentact.languageapi.player.LanguagePlayer;
import de.tentact.languageapi.player.PlayerExecutor;
import de.tentact.privateserver.PrivateServer;
import de.tentact.privateserver.provider.config.NPCServerItemProperty;
import de.tentact.privateserver.provider.config.PrivateServerConfig;
import de.tentact.privateserver.provider.i18n.I18N;
import de.tentact.privateserver.provider.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerNPCInteract implements Listener {

    private final PrivateServer privateServer;
    private final PrivateServerConfig serverConfig;
    private final PlayerExecutor playerExecutor = LanguageAPI.getInstance().getPlayerExecutor();

    public PlayerNPCInteract(PrivateServer privateServer) {
        this.privateServer = privateServer;
        this.serverConfig = privateServer.getConfiguration().getPrivateServerConfig();
        Bukkit.getPluginManager().registerEvents(this, privateServer);
    }

    @EventHandler
    public void handlePlayerNPCInteract(PlayerNPCInteractEvent event) {
        Player player = event.getPlayer();
        LanguagePlayer languagePlayer = playerExecutor.getLanguagePlayer(player.getUniqueId());
        if (languagePlayer == null) {
            return;
        }
        if (!player.hasPermission(this.serverConfig.getNPCInventory().getOpenPermission())) {
            languagePlayer.sendMessage(I18N.PLAYER_NO_NPC_OPEN_PERMS);
            return;
        }
        List<NPCServerItemProperty> serverItemProperties = this.getServerItemProperties(player);
        Inventory serverItemInventory = Bukkit.createInventory(null, this.serverConfig.getNPCInventory().getSize(), this.serverConfig.getNPCInventory().getName());
        serverItemProperties.forEach(serverItemProperty -> {
            ItemBuilder itemBuilder = new ItemBuilder(serverItemProperty);
            serverItemInventory.setItem(serverItemProperty.getInventorySlot(), itemBuilder.build());
        });
        player.openInventory(serverItemInventory);
    }

    @EventHandler
    public void handleInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        InventoryView inventoryView = event.getView();
        ItemStack itemStack = event.getCurrentItem();

        if (itemStack != null && event.getWhoClicked() instanceof Player) {
            if (!inventoryView.getTitle().equalsIgnoreCase(this.serverConfig.getNPCInventory().getName())) {
                return;
            }
            if (inventory.getSize() != this.serverConfig.getNPCInventory().getSize()) {
                return;
            }
            int clickedSlot = event.getSlot();
            Player player = (Player) event.getWhoClicked();

            this.getServerItemProperties(player)
                    .stream()
                    .filter(serverItemProperty ->
                            serverItemProperty.getDisplayName().equalsIgnoreCase(inventoryView.getTitle()) && serverItemProperty.getInventorySlot() == clickedSlot)
                    .findFirst()
                    .ifPresent(serverItemProperty -> {
                        LanguagePlayer languagePlayer = this.playerExecutor.getLanguagePlayer(player.getUniqueId());
                        if(languagePlayer == null) {
                            return;
                        }
                        String template = serverItemProperty.getTemplateToStart();
                        String templatePrefix = template.split("/")[0];
                        String templateName = template.split("/")[1];
                        if(!player.hasPermission(serverItemProperty.getStartPermission())) {
                            languagePlayer.sendMessage(I18N.PLAYER_NO_TEMPLATE_START
                                    .replace("%TEMPLATE%", serverItemProperty.getTemplateToStart())
                                    .replace("%TEMPLATE_NAME%", templateName)
                                    .replace("%TEMPLATE_PREFIX%", templatePrefix));
                            return;
                        }
                        player.performCommand("pserver create "+template);
                    });

        }
    }

    public List<NPCServerItemProperty> getServerItemProperties(Player player) {
        return this.serverConfig.getServerItems().getStartItems()
                .stream()
                .filter(npcServerItemProperty ->
                        player.hasPermission(npcServerItemProperty.getStartPermission()) ||
                                npcServerItemProperty.isShowIfNoPerms())
                .collect(Collectors.toList());
    }

}
