package de.hyper.worlds.domain.inventories;

import de.hyper.worlds.common.enums.SortDirection;
import de.hyper.worlds.common.obj.ServerWorldComparator;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.util.inventory.InfinityInventory;
import de.hyper.worlds.common.util.inventory.Inventory;
import de.hyper.worlds.common.util.inventory.buttons.Button;
import de.hyper.worlds.common.util.inventory.buttons.NoButton;
import de.hyper.worlds.common.util.inventory.buttons.OpenInventoryButton;
import de.hyper.worlds.common.util.inventory.designs.BottomLineBackGroundDesign;
import de.hyper.worlds.common.util.items.GlassPane;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.ItemBuilder;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;

import java.util.List;

public class ServerWorldsInventory extends InfinityInventory<ServerWorld> {

    Language lang = WorldManagement.get().getLanguage();

    protected SortDirection sortDirection;

    public ServerWorldsInventory(List<ServerWorld> list) {
        super("Worlds", 6, true);
        this.setDesign(new BottomLineBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.sortDirection = SortDirection.UP;
        this.list = list;
        this.list.sort(new ServerWorldComparator(sortDirection));
        this.currentPage = 0;
        this.maxPage = list.size() / 45;
    }

    @Override
    public Inventory fillInventory() {
        registerButton(6, 0, new OpenInventoryButton(new MainInventory(), player),
                new ItemBuilder(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .setLore(
                                lang.getText("inventory.general.back.desc"))
                        .getItem());
        registerButton(6, 4, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                sortDirection = sortDirection.next();
                list.sort(new ServerWorldComparator(sortDirection));
                cleanInventory().fillInventory();
            }
        }, new ItemBuilder((this.sortDirection == SortDirection.UP ? HDBSkulls.OAK_WOOD_ARROW_UP : HDBSkulls.OAK_WOOD_ARROW_DOWN)).setDisplayName((this.sortDirection == SortDirection.UP ? lang.getText("inventory.listed.sortdirection.up") : lang.getText("inventory.listed.sortdirection.down"))).getItem());
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
                ServerWorld serverWorld = list.get(get);
                if (serverWorld.isAllowedToSee(player)) {
                    registerButton(row, slot, new OpenInventoryButton(new ServerWorldInventory(serverWorld), player),
                            new ItemBuilder(HDBSkulls.EARTH)
                                    .setDisplayName(
                                            "§b" + serverWorld.getWorldName())
                                    .setLore(
                                            lang.getText("inventory.listed.each.desc.1"))
                                    .getItem());
                } else {
                    registerButton(row, slot, new NoButton(),
                            new ItemBuilder(HDBSkulls.BARRIER)
                                    .setDisplayName(
                                            lang.getText("inventory.listed.notavailable.name"))
                                    .setLore(
                                            lang.getText("inventory.listed.notavailable.desc.1"))
                                    .getItem());
                }
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