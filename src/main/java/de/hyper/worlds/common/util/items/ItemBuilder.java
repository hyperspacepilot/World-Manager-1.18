package de.hyper.worlds.common.util.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta itemMeta;

    public ItemBuilder(HDBSkulls hdbskull) {
        this.item = hdbskull.getSkull().clone();
        itemMeta = item.getItemMeta();
    }

    public ItemBuilder(ItemStack item) {
        this.item = item.clone();
        itemMeta = item.getItemMeta();
    }

    public ItemBuilder(Material material) {
        item = new ItemStack(material);
        itemMeta = item.getItemMeta();
    }

    public ItemBuilder(Material material, int subID) {
        item = new ItemStack(material, 1, (short) subID);
        itemMeta = item.getItemMeta();
    }

    public ItemBuilder setDisplayName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setLore(List<String> list) {
        itemMeta.setLore(list);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder addEnchantLimit(Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, false);
        return this;
    }

    public ItemBuilder setAmont(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder setDurability(int durability) {
        item.setDurability((short) durability);
        return this;
    }

    public int getAmount() {
        return item.getAmount();
    }

    public int getDurability() {
        return item.getDurability();
    }

    public ItemBuilder hideEnchants() {
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder hideAttributes() {
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        return this;
    }

    public ItemBuilder setUnbreakable() {
        itemMeta.setUnbreakable(true);
        return this;
    }

    public ItemBuilder setGlowing(boolean enabled) {
        return enabled ? setGlowing() : this;
    }

    public ItemBuilder setGlowing() {
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        return this;
    }

    public ItemStack getItem() {
        item.setItemMeta(itemMeta);
        return item;
    }

    public ItemBuilder setLeatherArmorColor(int red, int blue, int green) {
        LeatherArmorMeta lmeta = (LeatherArmorMeta) item.getItemMeta();
        lmeta.setColor(Color.fromRGB(red, green, blue));
        item.setItemMeta(lmeta);
        return this;
    }
}
