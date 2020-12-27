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

package de.tentact.privateserver.provider.config;

import java.util.ArrayList;
import java.util.List;

public class NPCInventoryLayout {

    private final String materialName;
    private final String displayName;
    private final byte subId;
    private final List<String> lore;

    public NPCInventoryLayout(String materialName, String displayName, byte subId, List<String> lore) {
        this.materialName = materialName;
        this.displayName = displayName;
        this.subId = subId;
        this.lore = new ArrayList<>(lore);
    }

    public String getMaterialName() {
        return this.materialName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public byte getSubId() {
        return this.subId;
    }

    public List<String> getLore() {
        return this.lore;
    }

}
