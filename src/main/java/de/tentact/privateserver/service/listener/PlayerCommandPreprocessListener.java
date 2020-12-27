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

package de.tentact.privateserver.service.listener;

import de.tentact.languageapi.LanguageAPI;
import de.tentact.privateserver.PrivateServer;
import de.tentact.privateserver.i18n.I18N;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocessListener implements Listener {

    private final PrivateServer privateServer;
    private final LanguageAPI languageAPI = LanguageAPI.getInstance();

    public PlayerCommandPreprocessListener(PrivateServer privateServer) {
        this.privateServer = privateServer;
        Bukkit.getPluginManager().registerEvents(this, privateServer);
    }

    @EventHandler
    public void handlePlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if(event.getMessage().startsWith("/stop")) {
            if(this.privateServer.getCurrentPrivateServiceUtil() == null) {
                return;
            }
            if(!this.privateServer.getCurrentPrivateServiceUtil().getOwner().equals(player.getUniqueId())) {
                return;
            }
            this.languageAPI.getPlayerExecutor().kickAll(I18N.OWNER_REQUESTED_STOP_KICK_ALL.get().replace("%OWNER%", player.getDisplayName()));
        }
    }

}
