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

import org.bukkit.ChatColor;

public class NPCInventory {

    private final String name;
    private final String openPermission;
    private final int size;
    private final NPCInventoryLayout inventoryFillItem;

    public NPCInventory(String name, String openPermission, int size, NPCInventoryLayout inventoryFillItem) {
        this.name = name;
        this.openPermission = openPermission;
        this.size = size;
        this.inventoryFillItem = inventoryFillItem;
    }

    public int getSize() {
        return this.size;
    }

    public String getName() {
        return ChatColor.translateAlternateColorCodes('&', this.name);
    }

    public String getOpenPermission() {
        return this.openPermission;
    }

    public NPCInventoryLayout getInventoryFillItem() {
        return this.inventoryFillItem;
    }
}
