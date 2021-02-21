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
            this.languageAPI.getPlayerExecutor().broadcastMessage(I18N.OWNER_LEFT_STOPPING_SERVER.get().replace("%OWNER%", player.getName()));

            Bukkit.getScheduler().runTaskLater(this.privateServer, () -> {
                if(Bukkit.getPlayer(this.currentPrivateServiceUtil.getOwner()) != null) {
                    return;
                }
                this.languageAPI.getPlayerExecutor().kickAll(I18N.OWNER_LEFT_KICK_MESSAGE.get().replace("%OWNER%", player.getName()));
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
        Bukkit.getScheduler().runTaskLater(this.privateServer, () -> {
            if(Bukkit.getOnlinePlayers().size() != 0) {
                return;
            }
            Bukkit.shutdown();
        }, 20 * 2L);
    }
}
