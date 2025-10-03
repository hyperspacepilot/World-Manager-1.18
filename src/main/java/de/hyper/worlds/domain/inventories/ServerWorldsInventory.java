package de.hyper.worlds.domain.inventories;

import de.hyper.inventory.InfinityInventory;
import de.hyper.inventory.Inventory;
import de.hyper.inventory.InventoryManager;
import de.hyper.inventory.buttons.Button;
import de.hyper.inventory.buttons.NoButton;
import de.hyper.inventory.buttons.OpenInventoryButton;
import de.hyper.inventory.designs.BottomLineBackGroundDesign;
import de.hyper.inventory.items.GlassPane;
import de.hyper.inventory.items.SimpleItemData;
import de.hyper.worlds.common.enums.SortDirection;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.obj.world.ServerWorldComparator;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.SkullItemData;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

import java.util.List;

public class ServerWorldsInventory extends InfinityInventory<ServerWorld> {

    protected SortDirection sortDirection;
    Language lang = WorldManagement.get().getLanguage();
    InventoryManager invManager = WorldManagement.get().getInventoryManager();

    public ServerWorldsInventory(Player player, List<ServerWorld> list) {
        super(player, "Worlds", 6);
        this.setDesign(new BottomLineBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.sortDirection = SortDirection.UP;
        this.list = list;
        this.list.sort(new ServerWorldComparator(sortDirection));
        this.currentPage = 0;
        this.maxPage = list.size() / 45;
    }

    @Override
    public Inventory fillInventory() {
        registerButtonAndItem(5, 0, new OpenInventoryButton(invManager, new MainInventory(player), player),
                new SimpleItemData(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .addLore(
                                lang.getText("inventory.general.back.desc")));
        registerButtonAndItem(5, 4, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                sortDirection = sortDirection.next();
                list.sort(new ServerWorldComparator(sortDirection));
                clearInventory().fillInventory();
            }
        }, new SkullItemData((this.sortDirection == SortDirection.UP ? HDBSkulls.OAK_WOOD_ARROW_UP : HDBSkulls.OAK_WOOD_ARROW_DOWN)).setDisplayName((this.sortDirection == SortDirection.UP ? lang.getText("inventory.listed.sortdirection.up") : lang.getText("inventory.listed.sortdirection.down"))));
        if (currentPage > 0) {
            registerLastPageButton(5, 0,
                    new SkullItemData(HDBSkulls.OAK_WOOD_ARROW_LEFT)
                            .setDisplayName(
                                    lang.getText("inventory.general.pages.last")));
        }
        if (currentPage < maxPage) {
            registerNextPageButton(5, 8,
                    new SkullItemData(HDBSkulls.OAK_WOOD_ARROW_RIGHT)
                            .setDisplayName(
                                    lang.getText("inventory.general.pages.next")));
        }
        int row = 0;
        int slot = 0;
        for (int i = 0; i != 45; i++) {
            int get = i + (currentPage * 45);
            if (get < list.size()) {
                ServerWorld serverWorld = list.get(get);
                if (serverWorld.isAllowedToSee(player)) {
                    registerButtonAndItem(row, slot, new OpenInventoryButton(invManager, new ServerWorldInventory(player, serverWorld), player),
                            new SkullItemData(HDBSkulls.EARTH)
                                    .setDisplayName(
                                            "§b" + serverWorld.getWorldName())
                                    .addLore(
                                            lang.getText("inventory.listed.each.desc.1")));
                } else {
                    registerButtonAndItem(row, slot, new NoButton(),
                            new SkullItemData(HDBSkulls.BARRIER)
                                    .setDisplayName(
                                            lang.getText("inventory.listed.notavailable.name"))
                                    .addLore(
                                            lang.getText("inventory.listed.notavailable.desc.1")));
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
    public Inventory clearInventory() {
        int row = 0;
        int slot = 0;
        for (int i = 0; i != 45; i++) {
            registerButtonAndItem(row, slot, new NoButton(), null);
            slot++;
            if (slot > 8) {
                slot = 0;
                row++;
            }
        }
        registerButtonAndItem(5, 0, new NoButton(), GlassPane.C7);
        registerButtonAndItem(5, 8, new NoButton(), GlassPane.C7);
        return this;
    }

    @Override
    public void onOpen() {
    }

    @Override
    public void onClose() {
    }
}