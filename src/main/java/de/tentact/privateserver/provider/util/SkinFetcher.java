package de.tentact.privateserver.provider.util;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 22.08.2020
    Uhrzeit: 23:53
*/

import com.github.derrop.documents.Document;
import com.github.derrop.documents.Documents;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

public class SkinFetcher {

    private static final String SERVICE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false";

    public static String fetchValue(UUID uuid) {
        Document properties = getProperties(uuid);
        return properties.contains("value") ? properties.get("value", String.class) : "";
    }

    public static String fetchValue(String name) {
        return fetchValue(UUIDFetcher.getUUID(name));
    }

    public static String fetchSignature(UUID uuid) {
        Document properties = getProperties(uuid);
        return properties.contains("signature") ? properties.get("signature", String.class) : "";
    }

    public static String fetchSignature(String name) {
        return fetchSignature(UUIDFetcher.getUUID(name));
    }

    private static InputStream getSkinInputStream(UUID uuid) throws IOException {
        return new URL(String.format(SERVICE_URL, uuid)).openStream();
    }

    public static Document getProperties(UUID uuid) {
        try {
            Document document = Documents.jsonStorage().read(getSkinInputStream(uuid));
            return Documents.newDocument(document.get("properties").getAsJsonArray().get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Documents.newDocument();
    }
}
