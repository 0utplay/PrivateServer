package de.tentact.privateserver.service.listener;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 17.08.2020
    Uhrzeit: 12:30
*/

import de.tentact.languageapi.LanguageAPI;
import de.tentact.privateserver.PrivateServer;
import de.tentact.privateserver.i18n.I18N;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocessListener implements Listener {

    private final PrivateServer privateServer;
    private final LanguageAPI languageAPI = LanguageAPI.getInstance();

    public PlayerCommandPreprocessListener(PrivateServer privateServer) {
        this.privateServer = privateServer;
        Bukkit.getPluginManager().registerEvents(this, privateServer);
    }

    public void handlePlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if(event.getMessage().startsWith("/stop")) {
            if(this.privateServer.getCurrentPrivateServiceUtil() == null) {
                return;
            }
            if(!this.privateServer.getCurrentPrivateServiceUtil().getOwner().equals(player.getUniqueId())) {
                return;
            }
            this.languageAPI.getPlayerExecutor().kickAll(I18N.OWNER_REQUESTED_STOP_KICK_ALL.replace("%OWNER%", player.getDisplayName()));
        }
    }

}
