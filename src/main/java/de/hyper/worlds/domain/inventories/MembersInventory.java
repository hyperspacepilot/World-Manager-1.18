package de.hyper.worlds.domain.inventories;

import de.hyper.inventory.InfinityInventory;
import de.hyper.inventory.Inventory;
import de.hyper.inventory.InventoryManager;
import de.hyper.inventory.buttons.NoButton;
import de.hyper.inventory.buttons.OpenInventoryButton;
import de.hyper.inventory.designs.BottomLineBackGroundDesign;
import de.hyper.inventory.items.GlassPane;
import de.hyper.inventory.items.SimpleItemData;
import de.hyper.worlds.common.obj.ServerUser;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.SkullBuilder;
import de.hyper.worlds.common.util.items.SkullItemData;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Cache;
import de.hyper.worlds.domain.using.Language;
import de.hyper.worlds.domain.using.Performance;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MembersInventory extends InfinityInventory<ServerUser> {

    protected ServerWorld serverWorld;
    Language lang = WorldManagement.get().getLanguage();
    Performance performance = WorldManagement.get().getPerformance();
    Cache cache = WorldManagement.get().getCache();
    InventoryManager invManager = WorldManagement.get().getInventoryManager();

    public MembersInventory(Player player, ServerWorld serverWorld) {
        super(player, "Members", 6);
        this.setDesign(new BottomLineBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.serverWorld = serverWorld;
        this.list = this.serverWorld.getMembers();
        this.maxPage = list.size() / 45;
        this.currentPage = 0;
    }

    @Override
    public Inventory fillInventory() {
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
        registerButtonAndItem(5, 4, new OpenInventoryButton(invManager, new ServerWorldInventory(player, serverWorld), player),
                new SimpleItemData(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .addLore(
                                lang.getText("inventory.general.back.desc")));
        int row = 0;
        int slot = 0;
        for (int i = 0; i != 45; i++) {
            int get = i + (currentPage * 45);
            if (get < list.size()) {
                ServerUser serverUser = list.get(get);
                registerButtonAndItem(row, slot,
                        new OpenInventoryButton(invManager, new UserInventory(player, serverWorld, serverUser), player),
                        new SimpleItemData(SkullBuilder.getSkullByPlayerName(serverUser.getName()))
                                .setDisplayName("§b" + serverUser.getName())
                                .addLore(
                                        "§7UUID: §b" + serverUser.getUuid().toString(),
                                        lang.getText("inventory.members.each.desc.1",
                                                serverUser.getWorldRole(serverWorld).getName()),
                                        lang.getText("inventory.members.each.desc.2")));
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