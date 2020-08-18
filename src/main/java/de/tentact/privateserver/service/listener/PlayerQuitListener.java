package de.tentact.privateserver.service.listener;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 06.08.2020
    Uhrzeit: 11:27
*/

import de.tentact.languageapi.LanguageAPI;
import de.tentact.privateserver.PrivateServer;
import de.tentact.privateserver.i18n.I18N;
import de.tentact.privateserver.service.CurrentPrivateServiceUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final CurrentPrivateServiceUtil currentPrivateServiceUtil;
    private final LanguageAPI languageAPI = LanguageAPI.getInstance();
    private final PrivateServer privateServer;

    public PlayerQuitListener(PrivateServer privateServer) {
        this.privateServer = privateServer;
        this.currentPrivateServiceUtil = privateServer.getCurrentPrivateServiceUtil();
        Bukkit.getPluginManager().registerEvents(this, privateServer);
    }

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!this.currentPrivateServiceUtil.isPrivateServer()) {
            return;
        }
        if (this.currentPrivateServiceUtil.isAutoStopOnOwnerLeave() && player.getUniqueId().equals(this.currentPrivateServiceUtil.getOwner())) {
            languageAPI.getPlayerExecutor().broadcastMessage(I18N.OWNER_LEFT_STOPPING_SERVER.replace("%OWNER%", player.getName()));

            Bukkit.getScheduler().runTaskLater(privateServer, () -> {
                languageAPI.getPlayerExecutor().kickAll(I18N.OWNER_LEFT_KICK_MESSAGE.replace("%OWNER%", player.getName()));
                Bukkit.shutdown();
            }, 20 * 15L);
            return;
        }
        if (!this.currentPrivateServiceUtil.isAutoStopOnEmpty()) {
            return;
        }
        if (Bukkit.getOnlinePlayers().size() != 0) {
            return;
        }
        Bukkit.getScheduler().runTaskLater(privateServer, Bukkit::shutdown, 20 * 2L);
    }
}
