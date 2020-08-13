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

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        this(new ItemStack(material, amount));
    }

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(String materialName) {
        this(Material.getMaterial(materialName, true));
    }

    public ItemBuilder setSubId(byte subId) {
        if(subId == -1) {
            return this;
        }
        this.itemStack.setDurability(subId);
        return this;
    }

    public ItemBuilder(NPCServerItemProperty serverItemProperty) {
        this(serverItemProperty.getMaterialName());
        this.setDisplayName(serverItemProperty.getDisplayName());
        this.setLore(serverItemProperty.getLore());
        this.setSubId(serverItemProperty.getSubid());
    }

    public ItemBuilder setLore(List<String> lore) {
        this.itemStack.setLore(lore);
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        this.itemMeta.setDisplayName(displayName);
        return this;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }

}
