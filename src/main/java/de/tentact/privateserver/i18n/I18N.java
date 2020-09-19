package de.tentact.privateserver.i18n;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 05.08.2020
    Uhrzeit: 14:06
*/


import de.tentact.languageapi.LanguageAPI;
import de.tentact.languageapi.i18n.Translation;

public enum I18N {

    PREFIX("pserver-prefix", "&ePServer x &7"),
    NO_BASECOMMAND_PERMISSION("pserver-basecommand-no-permission", "Dazu hast du keine Rechte"),
    COMMAND_HELP("pserver-basecommand-help", "Nutze /pserver create <Prefix/Name>\n" +
            "Nutze /pserver npc <create | delete>"),
    SKIN_FETCH_FAIL("pserver-command-skin-fetch-fail", "Der Skin von %PLAYER% konnte nicht geladen werden.", "%PLAYER%"),
    WRONG_TEMPLATE_FORMAT("pserver-wrong-template-format", "Templates sollten folgender Maßsen aussehen: Prefix/Name"),
    NO_BASECOMMAND_ON_PSERVER("pserver-basecommand-on-pserver", "Du kannst diesen Command nicht auf einem privaten Server nutzen."),
    STARTING_PSERVER("pserver-starting", "Es wird ein privater Server mit dem Template %TEMPLATE_PREFIX% gestartet.", "%TEMPLATE_PREFIX%,%TEMPLATE_NAME%,%TEMPLATE%"),
    TELEPORT_AFTER_START("pserver-teleport-after-start", "Sobald der Server hochgefahren ist, wirst du teleportiert."),
    STARTING_PSERVER_ERROR("pserver-starting-error", "Beim Starten des Servers ist ein Fehler aufgetreten."),
    TEMPLATE_NOT_FOUND("pserver-template-not-found", "Das Template %TEMPLATE% konnte nicht gefunden werden.", "%TEMPLATE_PREFIX%,%TEMPLATE_NAME%,%TEMPLATE%"),
    NO_TEMPLATE_START_PERMISSION("pserver-no-template-start-permission", "Du hast keine Rechte um das Template %TEMPLATE% zu starten.", "%TEMPLATE_PREFIX%,%TEMPLATE_NAME%,%TEMPLATE%"),
    OWNER_LEFT_STOPPING_SERVER("pserver-owner-left-stopping-server", "Der Owner des Servers %OWNER% hat den Server verlassen. Der Server wird in 15 Sekunden gestoppt.", "%OWNER%"),
    OWNER_LEFT_KICK_MESSAGE("pserver-owner-left-kick-message", "Der Server wird nun gestoppt.", "%OWNER%"),
    PLAYER_ALREADY_HAS_PSERVER("pserver-player-already-has-pserver", "Du kannst nur einen PServer haben"),
    PLAYER_NO_NPC_OPEN_PERMS("pserver-player-no-npc-open-perms", "Du hast keine Rechte um diesen NPC zu öffnen. Kaufe dir Premium auf Server.de"),
    PLAYER_NO_TEMPLATE_START("pserver-player-no-template-start-perms", "Du hast keine Rechte diesen Server (%TEMPLATE%) zustarten. ", "%TEMPLATE_PREFIX%,%TEMPLATE_NAME%,%TEMPLATE%"),
    OWNER_REQUESTED_STOP_KICK_ALL("pserver-owner-requested-stop-kick-all", "%OWNER% hat seinen Server gestoppt.");

    private final LanguageAPI languageAPI = LanguageAPI.getInstance();
    private final String key;

    I18N(String key, String defaultTranslation) {
        this(key, defaultTranslation, null);
    }

    I18N(String key, String defaultTranslation, String parameter) {
        this.key = key;
        this.languageAPI.addMessageToDefault(key, defaultTranslation, parameter);
    }

    public Translation get() {
        return this.get(true);
    }

    public Translation get(boolean withPrefix) {
        if (withPrefix) {
            return this.languageAPI.getTranslationWithPrefix(PREFIX.get(false), this.key);
        }
        return this.languageAPI.getTranslation(this.key);
    }
}
