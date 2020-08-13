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
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
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
        if(languagePlayer == null) {
            return;
        }
        if(!player.hasPermission(this.serverConfig.getNPCInventory().getOpenPermission())) {
            languagePlayer.sendMessage(I18N.PLAYER_NO_NPC_OPEN_PERMS);
            return;
        }
        List<NPCServerItemProperty> serverItemProperties = this.serverConfig.getServerItems().getStartItems()
                .stream()
                .filter(npcServerItemProperty ->
                        player.hasPermission(npcServerItemProperty.getStartPermission()) ||
                                npcServerItemProperty.isShowIfNoPerms())
                .collect(Collectors.toList());
        Inventory serverItemInventory = Bukkit.createInventory(null, this.serverConfig.getNPCInventory().getSize(), this.serverConfig.getNPCInventory().getName());
        for (int i = 0; i < serverItemInventory.getSize(); i++) {
            NPCServerItemProperty serverItemProperty = serverItemProperties.get(i);
            ItemBuilder itemBuilder = new ItemBuilder(serverItemProperty);
            serverItemInventory.setItem(i, itemBuilder.build());
        }
    }

}
