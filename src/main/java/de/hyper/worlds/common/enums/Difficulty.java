package de.hyper.worlds.common.enums;

import de.hyper.worlds.common.util.items.ItemBuilder;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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

    public ItemStack buildItemStack() {
        Language lang = WorldManagement.get().getLanguage();
        String[] r = new String[5 + values().length];
        r[0] = lang.getText("inventory.attributes.difficulty.desc.1");
        r[1] = " ";
        int a = 2;
        for (Difficulty d : values()) {
            r[a++] = ((d.equals(this)) ? "§7➙ " : "    ") +
                    lang.getText("inventory.attributes.difficulty.desc." + d.getLKey());
        }
        r[a++] = "  ";
        r[a++] = lang.getText("inventory.attributes.difficulty.desc.2");
        r[a] = lang.getText("inventory.attributes.difficulty.desc.3");
        return new ItemBuilder(Material.FLINT_AND_STEEL)
                .setDisplayName(
                        lang.getText("inventory.attributes.difficulty.name"))
                .setLore(r)
                .hideAttributes()
                .getItem();
    }

    public Difficulty next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public Difficulty last() {
        return values()[(this.ordinal() - 1 + values().length) % values().length];
    }
}
