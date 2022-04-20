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

    private int id;
    private String lKey;
    private org.bukkit.Difficulty dif;

    private Difficulty(int id, String lKey, org.bukkit.Difficulty dif) {
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
            r[a++] = ((d.equals(this)) ? "§7➙ " : "    ") + lang.getText("inventory.attributes.difficulty.desc." + d.getLKey());
        }
        r[a++] = "  ";
        r[a++] = lang.getText("inventory.attributes.difficulty.desc.2");
        r[a] = lang.getText("inventory.attributes.difficulty.desc.3");
        return new ItemBuilder(Material.FLINT_AND_STEEL).setDisplayName(lang.getText("inventory.attributes.difficulty.name")).setLore(r).hideAttributes().getItem();
    }

    public Difficulty next() {
        int order = this.ordinal();
        int newSpot = 0;
        Difficulty result = this;
        if (order == values().length - 1)
            newSpot = 0;
        else
            newSpot = order + 1;
        for (Difficulty val : values())
            if (val.ordinal() == newSpot)
                result = val;
        return result;
    }
    public Difficulty last() {
        int order = this.ordinal();
        int newSpot = 0;
        Difficulty result = this;
        if (order == 0)
            newSpot = values().length - 1;
        else
            newSpot = order - 1;
        for (Difficulty val : values())
            if (val.ordinal() == newSpot)
                result = val;
        return result;
    }
}
