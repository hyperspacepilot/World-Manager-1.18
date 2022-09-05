package de.hyper.worlds.domain.inventories;

import de.hyper.worlds.common.util.inventory.InfinityInventory;
import de.hyper.worlds.common.util.inventory.Inventory;
import de.hyper.worlds.common.util.inventory.buttons.NoButton;
import de.hyper.worlds.common.util.inventory.designs.BottomLineBackGroundDesign;
import de.hyper.worlds.common.util.items.GlassPane;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.ItemBuilder;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class LoadedWorldsInventory extends InfinityInventory<World> {

    Language lang = WorldManagement.get().getLanguage();

    public LoadedWorldsInventory() {
        super("Loaded-Worlds", 6, true);
        this.setDesign(new BottomLineBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.list = Bukkit.getWorlds();
        this.currentPage = 0;
        this.maxPage = list.size() / 45;
    }

    @Override
    public Inventory fillInventory() {
        if (currentPage > 0) {
            registerLastPageButton(6, 0,
                    new ItemBuilder(HDBSkulls.OAK_WOOD_ARROW_LEFT)
                            .setDisplayName(
                                    lang.getText("inventory.general.pages.last"))
                            .getItem());
        }
        if (currentPage < maxPage) {
            registerNextPageButton(6, 8,
                    new ItemBuilder(HDBSkulls.OAK_WOOD_ARROW_RIGHT)
                            .setDisplayName(
                                    lang.getText("inventory.general.pages.next"))
                            .getItem());
        }
        int row = 1;
        int slot = 0;
        for (int i = 0; i != 45; i++) {
            int get = i + (currentPage * 45);
            if (get < list.size()) {
                World world = list.get(get);
                registerButton(row, slot, new NoButton(),
                        new ItemBuilder(HDBSkulls.EARTH)
                                .setDisplayName("§b" + world.getName())
                                .setLore(
                                        "§7Seed: §b" + world.getSeed(),
                                        "§7Players: §b" + world.getPlayers().size())
                                .getItem());
                slot++;
                if (slot > 8) {
                    slot = 0;
                    row++;
                }
            } else break;
        }
        return this;
    }

    @Override
    public Inventory cleanInventory() {
        int row = 1;
        int slot = 0;
        for (int i = 0; i != 45; i++) {
            registerButton(row, slot, new NoButton(), null);
            slot++;
            if (slot > 8) {
                slot = 0;
                row++;
            }
        }
        registerButton(6, 0, new NoButton(), GlassPane.C7);
        registerButton(6, 8, new NoButton(), GlassPane.C7);
        return this;
    }

    @Override
    public void onOpen() {

    }

    @Override
    public void onClose() {

    }
}