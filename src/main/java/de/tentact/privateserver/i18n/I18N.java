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

package de.tentact.privateserver.i18n;


import de.tentact.languageapi.LanguageAPI;
import de.tentact.languageapi.i18n.Translation;

import java.util.Arrays;

public enum I18N {

    PREFIX("pserver-prefix", "&ePServer x &7"),
    BASECOMMAND_PERMISSION("pserver-basecommand-no-permission", "Dazu hast du keine Rechte"),
    COMMAND_HELP("pserver-basecommand-help", "Nutze /pserver create <Prefix/Name>\n" +
            "Nutze /pserver npc create <shouldImitadePlayer> <shouldLookAtPlayer> <NPCName>\n" +
            "Nutze /pserver npc delete"),
    SKIN_FETCH_FAIL("pserver-command-skin-fetch-fail", "Der Skin von %PLAYER% konnte nicht geladen werden.", "%PLAYER%"),
    WRONG_TEMPLATE_FORMAT("pserver-wrong-template-format", "Templates sollten folgender Maßsen aussehen: Prefix/Name"),
    BASECOMMAND_ON_PSERVER("pserver-basecommand-on-pserver", "Du kannst diesen Command nicht auf einem privaten Server nutzen."),
    STARTING_PSERVER("pserver-starting", "Es wird ein privater Server mit dem Template %TEMPLATE_PREFIX% gestartet.", "%TEMPLATE_PREFIX%", "%TEMPLATE_NAME%", "%TEMPLATE%"),
    TELEPORT_AFTER_START("pserver-teleport-after-start", "Sobald der Server hochgefahren ist, wirst du teleportiert."),
    STARTING_PSERVER_ERROR("pserver-starting-error", "Beim Starten des Servers ist ein Fehler aufgetreten."),
    TEMPLATE_NOT_FOUND("pserver-template-not-found", "Das Template %TEMPLATE% konnte nicht gefunden werden.", "%TEMPLATE_PREFIX%", "%TEMPLATE_NAME%", "%TEMPLATE%"),
    NO_TEMPLATE_START_PERMISSION("pserver-no-template-start-permission", "Du hast keine Rechte um das Template %TEMPLATE% zu starten.", "%TEMPLATE_PREFIX%", "%TEMPLATE_NAME%", "%TEMPLATE%"),
    OWNER_LEFT_STOPPING_SERVER("pserver-owner-left-stopping-server", "Der Owner des Servers %OWNER% hat den Server verlassen. Der Server wird in 15 Sekunden gestoppt.", "%OWNER%"),
    OWNER_LEFT_KICK_MESSAGE("pserver-owner-left-kick-message", "Der Server wird nun gestoppt.", "%OWNER%"),
    PLAYER_ALREADY_HAS_PSERVER("pserver-player-already-has-pserver", "Du kannst nur einen PServer haben"),
    PLAYER_NO_NPC_OPEN_PERMS("pserver-player-no-npc-open-perms", "Du hast keine Rechte um diesen NPC zu öffnen. Kaufe dir Premium auf Server.de"),
    PLAYER_NO_TEMPLATE_START_PERMS("pserver-player-no-template-start-perms", "Du hast keine Rechte diesen Server (%TEMPLATE%) zustarten. ", "%TEMPLATE_PREFIX%", "%TEMPLATE_NAME%", "%TEMPLATE%"),
    OWNER_REQUESTED_STOP_KICK_ALL("pserver-owner-requested-stop-kick-all", "%OWNER% hat seinen Server gestoppt.");

    private final String key;

    I18N(String key, String defaultTranslation) {
        this(key, defaultTranslation, new String[0]);
    }

    I18N(String key, String defaultTranslation, String... parameter) {
        this.key = key;
        LanguageAPI.getInstance().addMessageToDefault(key, defaultTranslation, Arrays.asList(parameter));
    }

    public Translation get() {
        return this.get(true);
    }

    public Translation get(boolean withPrefix) {
        LanguageAPI languageAPI = LanguageAPI.getInstance();
        if (withPrefix) {
            return languageAPI.getTranslationWithPrefix(PREFIX.get(false), this.key);
        }
        return languageAPI.getTranslation(this.key);
    }
}
