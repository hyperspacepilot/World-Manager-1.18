package de.hyper.worlds.common.enums;

import de.hyper.inventory.items.ItemData;
import de.hyper.inventory.items.SimpleItemData;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

@Getter
public enum Difficulty {

    PEACEFUL(0, "peaceful", org.bukkit.Difficulty.PEACEFUL),
    EASY(1, "easy", org.bukkit.Difficulty.EASY),
    NORMAL(2, "normal", org.bukkit.Difficulty.NORMAL),
    HARD(3, "hard", org.bukkit.Difficulty.HARD);

    private final int id;
    private final String lKey;
    private final org.bukkit.Difficulty dif;

    Difficulty(int id, String lKey, org.bukkit.Difficulty dif) {
        this.id = id;
        this.lKey = lKey;
        this.dif = dif;
    }

    public ItemData buildItemData() {
        Language lang = WorldManagement.get().getLanguage();
        ItemData itemData = new SimpleItemData(Material.FLINT_AND_STEEL);
        itemData.setDisplayName(lang.getText("inventory.attributes.difficulty.name"));
        itemData.addLore(lang.getText("inventory.attributes.difficulty.desc.1"));
        itemData.addLore(" ");
        for (Difficulty d : values()) {
            itemData.addLore(((d.equals(this)) ? "§7➙ " : "    ") +
                    lang.getText("inventory.attributes.difficulty.desc." + d.getLKey()));
        }
        itemData.addLore(" ");
        itemData.addLore(lang.getText("inventory.attributes.difficulty.desc.2"));
        itemData.addLore(lang.getText("inventory.attributes.difficulty.desc.3"));
        itemData.addItemFlags(ItemFlag.values());
        return itemData;
    }

    public Difficulty next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public Difficulty last() {
        return values()[(this.ordinal() - 1 + values().length) % values().length];
    }
}
