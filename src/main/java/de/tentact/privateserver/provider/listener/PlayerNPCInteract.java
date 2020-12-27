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

package de.tentact.privateserver.provider.listener;

import com.github.juliarn.npc.event.PlayerNPCInteractEvent;
import de.tentact.languageapi.LanguageAPI;
import de.tentact.languageapi.player.LanguagePlayer;
import de.tentact.languageapi.player.PlayerExecutor;
import de.tentact.privateserver.PrivateServer;
import de.tentact.privateserver.i18n.I18N;
import de.tentact.privateserver.provider.config.NPCServerItemProperty;
import de.tentact.privateserver.provider.config.PrivateServerConfig;
import de.tentact.privateserver.provider.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
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
            languagePlayer.sendMessage(I18N.PLAYER_NO_NPC_OPEN_PERMS.get());
            return;
        }
        Inventory serverItemInventory = Bukkit.createInventory(null, this.serverConfig.getNPCInventory().getSize(), this.serverConfig.getNPCInventory().getName());

        ItemStack fillItem = new ItemBuilder(this.serverConfig.getNPCInventory().getInventoryFillItem()).build();
        for (int i = 0; i < serverItemInventory.getSize(); i++) {
            serverItemInventory.setItem(i, fillItem);
        }

        //TODO: Rework this (this leaves some places empty...)
        this.getServerItemProperties(player).forEach(serverItemProperty -> {
            ItemBuilder itemBuilder = new ItemBuilder(serverItemProperty);
            serverItemInventory.setItem(serverItemProperty.getInventorySlot(), itemBuilder.build());
        });

        this.privateServer.getPrivateServerUtil().getServiceInfoSnapshot(player.getUniqueId()).ifPresent(serviceInfoSnapshot -> {
            String template = this.privateServer.getPrivateServerUtil().getPropertyAsString(serviceInfoSnapshot, "templatePrefix")
                    + "/" + this.privateServer.getPrivateServerUtil().getPropertyAsString(serviceInfoSnapshot, "templateName");
            if (this.serverConfig.getCurrentServerItem() != null) {
                serverItemInventory.setItem(this.serverConfig.getCurrentServerItem().getInventorySlot(), new ItemBuilder(this.serverConfig.getCurrentServerItem(), template).build());
            }
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
            event.setCancelled(true);
            int clickedSlot = event.getSlot();
            Player player = (Player) event.getWhoClicked();
            this.getServerItemProperties(player)
                    .stream()
                    .filter(serverItemProperty ->
                            serverItemProperty.getDisplayName().equalsIgnoreCase(itemStack.getItemMeta().getDisplayName()))
                    .filter(serverItemProperty -> serverItemProperty.getInventorySlot() == event.getSlot())
                    .findFirst()
                    .ifPresent(serverItemProperty -> {
                        LanguagePlayer languagePlayer = this.playerExecutor.getLanguagePlayer(player.getUniqueId());
                        if (languagePlayer == null) {
                            return;
                        }
                        String template = serverItemProperty.getTemplateToStart();
                        String templatePrefix = template.split("/")[0];
                        String templateName = template.split("/")[1];
                        if (!player.hasPermission(serverItemProperty.getStartPermission())) {
                            languagePlayer.sendMessage(I18N.PLAYER_NO_TEMPLATE_START_PERMS.get()
                                    .replace("%TEMPLATE%", serverItemProperty.getTemplateToStart())
                                    .replace("%TEMPLATE_NAME%", templateName)
                                    .replace("%TEMPLATE_PREFIX%", templatePrefix));
                            return;
                        }
                        this.privateServer.getPrivateServerUtil().createPrivateServer(player, template);
                    });
            if (!this.privateServer.getPrivateServerUtil().hasPrivateServer(player.getUniqueId())) {
                return;
            }
            if (this.serverConfig.getCurrentServerItem() == null) {
                return;
            }
            if (clickedSlot != this.serverConfig.getCurrentServerItem().getInventorySlot()) {
                return;
            }
            this.privateServer.getPrivateServerUtil().sendOwner(player.getUniqueId());
        }
    }

    public Collection<NPCServerItemProperty> getServerItemProperties(Player player) {
        return this.serverConfig.getStartItems()
                .stream()
                .filter(npcServerItemProperty ->
                        player.hasPermission(npcServerItemProperty.getStartPermission()) ||
                                npcServerItemProperty.isShowIfNoPerms())
                .collect(Collectors.toList());
    }
}
