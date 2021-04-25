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

import de.tentact.privateserver.provider.config.NPCCurrentServerItemProperty;
import de.tentact.privateserver.provider.config.NPCInventoryLayout;
import de.tentact.privateserver.provider.config.NPCServerItemProperty;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class ItemBuilder {

    private final ItemStack itemStack;

    public ItemBuilder(Material material, int amount) {
        if (material == null) {
            throw new NullPointerException("Check your materials in your config.json." +
                    "\nAlso ONLY use items that exist in your SERVER version." +
                    "\nThe shown names aren't the same as in code. Please use those ones for code from the docs.");
        }
        this.itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(String materialName) {
        this(Material.getMaterial(materialName));
    }

    public void setSubId(byte subId) {
        if (subId <= 0) {
            return;
        }
        this.itemStack.setDurability(subId);
    }

    public ItemBuilder(NPCServerItemProperty serverItemProperty) {
        this(serverItemProperty.getMaterialName());
        this
                .setDisplayName(serverItemProperty.getDisplayName())
                .setLore(serverItemProperty.getLore())
                .setSubId(serverItemProperty.getSubId());
    }

    public ItemBuilder(NPCCurrentServerItemProperty serverItemProperty, String startTemplate) {
        this(serverItemProperty.getMaterialName());
        List<String> lore = serverItemProperty.getLore();
        Collections.replaceAll(lore, "%TEMPLATE%", startTemplate);
        Collections.replaceAll(lore, "%TEMPLATE_PREFIX%", startTemplate.split("/")[0]);
        Collections.replaceAll(lore, "%TEMPLATE_NAME%", startTemplate.split("/")[1]);

        this.setDisplayName(
                serverItemProperty.getDisplayName()
                .replace("%TEMPLATE%", startTemplate)
                .replace("%TEMPLATE_PREFIX%", startTemplate.split("/")[0])
                .replace("%TEMPLATE_NAME%", startTemplate.split("/")[1]))
                .setLore(lore)
                .setSubId(serverItemProperty.getSubId());
    }

    public ItemBuilder(NPCInventoryLayout inventoryLayout) {
        this(inventoryLayout.getMaterialName());
        this.setDisplayName(inventoryLayout.getDisplayName());
        this.setSubId(inventoryLayout.getSubId());
        this.setLore(inventoryLayout.getLore());
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setLore(lore);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build() {
        return this.itemStack;
    }

}
