package de.tentact.privateserver.i18n;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 05.08.2020
    Uhrzeit: 14:06
*/


import de.tentact.languageapi.LanguageAPI;
import de.tentact.languageapi.i18n.Translation;
import de.tentact.privateserver.PrivateServer;

public class I18N {

    private static final LanguageAPI languageAPI = LanguageAPI.getInstance();

    public static Translation PREFIX = languageAPI.getTranslation("pserver-prefix");

    public static Translation NO_BASECOMMAND_PERMISSION = languageAPI.getTranslationWithPrefix(PREFIX, "pserver-basecommand-no-permission");

    public static Translation COMMAND_HELP = languageAPI.getTranslationWithPrefix(PREFIX, "pserver-basecommand-help");

    public static Translation WRONG_TEMPLATE_FORMAT = languageAPI.getTranslationWithPrefix(PREFIX, "pserver-wrong-template-format");

    public static Translation NO_BASECOMMAND_ON_PSERVER = languageAPI.getTranslationWithPrefix(PREFIX, "pserver-basecommand-on-pserver");

    public static Translation STARTING_PSERVER = languageAPI.getTranslationWithPrefix(PREFIX, "pserver-starting");

    public static Translation TELEPORT_AFTER_START = languageAPI.getTranslationWithPrefix(PREFIX, "pserver-teleport-after-start");

    public static Translation STARTING_PSERVER_ERROR = languageAPI.getTranslationWithPrefix(PREFIX, "pserver-starting-error");

    public static Translation TEMPLATE_NOT_FOUND = languageAPI.getTranslationWithPrefix(PREFIX, "pserver-template-not-found");

    public static Translation NO_TEMPLATE_START_PERMISSION = languageAPI.getTranslationWithPrefix(PREFIX, "pserver-no-template-start-permission");

    public static Translation OWNER_LEFT_STOPPING_SERVER = languageAPI.getTranslationWithPrefix(PREFIX, "pserver-owner-left-stopping-server");

    public static Translation OWNER_LEFT_KICK_MESSAGE = languageAPI.getTranslationWithPrefix(PREFIX, "pserver-owner-left-kick-message");

    public static Translation PLAYER_ALREADY_HAS_PSERVER = languageAPI.getTranslationWithPrefix(PREFIX, "pserver-player-already-has-pserver");

    public static Translation PLAYER_NO_NPC_OPEN_PERMS = languageAPI.getTranslationWithPrefix(PREFIX, "pserver-player-no-npc-open-perms");

    public static Translation PLAYER_NO_TEMPLATE_START = languageAPI.getTranslationWithPrefix(PREFIX, "pserver-player-no-template-start-perms");

    public static Translation OWNER_REQUESTED_STOP_KICK_ALL = languageAPI.getTranslationWithPrefix(PREFIX, "pserver-owner-requested-stop-kick-all");

    public static void createDefaultMessages(PrivateServer privateServer) {
        privateServer.logInfo("Creating default translations");
        PREFIX.createDefaults("&ePServer x &7");
        NO_BASECOMMAND_PERMISSION.createDefaults("Dazu hast du keine Rechte");
        COMMAND_HELP.createDefaults("Nutze /pserver create <Prefix/Name>\n" +
                "Nutze /pserver npc <create | delete>");
        WRONG_TEMPLATE_FORMAT.createDefaults("Templates sollten folgender Maßsen aussehen: Prefix/Name");
        NO_BASECOMMAND_ON_PSERVER.createDefaults("Du kannst diesen Command nicht auf einem privaten Server nutzen.");
        STARTING_PSERVER.createDefaults("Es wird ein privater Server mit dem Template %TEMPLATE_PREFIX% gestartet.", "%TEMPLATE_PREFIX%,%TEMPLATE_NAME%,%TEMPLATE%");
        TELEPORT_AFTER_START.createDefaults("Sobald der Server hochgefahren ist, wirst du teleportiert.");
        STARTING_PSERVER_ERROR.createDefaults("Beim Starten des Servers ist ein Fehler aufgetreten.");
        TEMPLATE_NOT_FOUND.createDefaults("Das Template %TEMPLATE% konnte nicht gefunden werden.", "%TEMPLATE_PREFIX%,%TEMPLATE_NAME%,%TEMPLATE%");
        NO_TEMPLATE_START_PERMISSION.createDefaults("Du hast keine Rechte um das Template %TEMPLATE% zu starten.", "%TEMPLATE_PREFIX%,%TEMPLATE_NAME%,%TEMPLATE%");
        OWNER_LEFT_STOPPING_SERVER.createDefaults("Der Owner des Servers %OWNER% hat den Server verlassen. Der Server wird in 15 Sekunden gestoppt.", "%OWNER%");
        OWNER_LEFT_KICK_MESSAGE.createDefaults("Der Server wird nun gestoppt.", "%OWNER%");
        PLAYER_ALREADY_HAS_PSERVER.createDefaults("Du kannst nur einen PServer haben");
        PLAYER_NO_NPC_OPEN_PERMS.createDefaults("Du hast keine Rechte um diesen NPC zu öffnen. Kaufe dir Premium auf Server.de");
        PLAYER_NO_TEMPLATE_START.createDefaults("Du hast keine Rechte diesen Server (%TEMPLATE%) zustarten. ", "%TEMPLATE_PREFIX%,%TEMPLATE_NAME%,%TEMPLATE%");
        OWNER_REQUESTED_STOP_KICK_ALL.createDefaults("%OWNER% hat seinen Server gestoppt.");
    }
}
