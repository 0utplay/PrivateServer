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

package de.tentact.privateserver.provider.util;

import com.github.derrop.documents.Document;
import com.github.derrop.documents.Documents;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SkinFetcher {

    private static final String SERVICE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false";
    private static final Cache<UUID, Document> skinCache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();

    public static String fetchValue(UUID uuid) {
        Document properties = skinCache.getIfPresent(uuid);
        if(properties == null) {
            properties = getProperties(uuid);
            skinCache.put(uuid, properties);
        }
        return properties.contains("value") ? properties.get("value", String.class) : "";
    }

    public static String fetchValue(String name) {
        return fetchValue(UUIDFetcher.getUUID(name));
    }

    public static String fetchSignature(UUID uuid) {
        Document properties = skinCache.getIfPresent(uuid);
        if(properties == null) {
            properties = getProperties(uuid);
            skinCache.put(uuid, properties);
        }
        return properties.contains("signature") ? properties.get("signature", String.class) : "";
    }

    public static String fetchSignature(String name) {
        return fetchSignature(UUIDFetcher.getUUID(name));
    }

    private static InputStream getSkinInputStream(UUID uuid) throws IOException {
        return new URL(String.format(SERVICE_URL, uuid)).openStream();
    }

    private static Document getProperties(UUID uuid) {
        try {
            Document document = Documents.jsonStorage().read(getSkinInputStream(uuid));
            return Documents.newDocument(document.get("properties").getAsJsonArray().get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Documents.newDocument();
    }
}
