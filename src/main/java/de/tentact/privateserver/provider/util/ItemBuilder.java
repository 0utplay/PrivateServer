package de.tentact.privateserver.provider.util;
/*  Created in the IntelliJ IDEA.
    Copyright(c) 2020
    Created by 0utplay | Aldin Sijamhodzic
    Datum: 07.08.2020
    Uhrzeit: 11:49
*/

import de.tentact.privateserver.provider.config.NPCServerItemProperty;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {

    private final ItemStack itemStack;

    public ItemBuilder(Material material, int amount) {
        if (material == null) {
            throw new NullPointerException("Check your materials in your config.json.\nAlso ONLY use items that exist in your SERVER version.\nThe shown names aren't the same as in code. Please use those ones for code.");
        }
        this.itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(String materialName) {
        this(Material.getMaterial(materialName));
    }

    public ItemBuilder setSubId(byte subId) {
        if (subId == -1) {
            return this;
        }
        this.itemStack.setDurability(subId);
        return this;
    }

    public ItemBuilder(NPCServerItemProperty serverItemProperty) {
        this(serverItemProperty.getMaterialName());
        this
                .setDisplayName(serverItemProperty.getDisplayName())
                .setLore(serverItemProperty.getLore())
                .setSubId(serverItemProperty.getSubid());
    }

    public ItemBuilder setLore(List<String> lore) {
        this.itemStack.setLore(lore);
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
