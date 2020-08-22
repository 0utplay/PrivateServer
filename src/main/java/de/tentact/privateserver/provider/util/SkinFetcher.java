package de.tentact.privateserver.provider.util;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 22.08.2020
    Uhrzeit: 23:53
*/

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public class SkinFetcher {

    private static final String SERVICE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false";

    public static String fetch(UUID uuid) {
        return (String) getProperties(uuid).get("value");
    }

    public static String fetch(String name) {
        return fetch(UUIDFetcher.getUUID(name));
    }

    private static InputStream getSkinInputStream(UUID uuid) throws IOException {
        return new URL(String.format(SERVICE_URL, uuid)).openStream();
    }

    private static JSONObject getProperties(UUID uuid) {
        JSONParser jsonParser = new JSONParser();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getSkinInputStream(uuid)))) {
            JSONObject jsonObject =(JSONObject) jsonParser.parse(bufferedReader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("properties");
            return (JSONObject) jsonArray.get(0);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }


}
