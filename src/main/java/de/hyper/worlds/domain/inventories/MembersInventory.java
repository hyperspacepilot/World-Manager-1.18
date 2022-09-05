package de.hyper.worlds.domain.inventories;

import de.hyper.worlds.common.obj.ServerUser;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.util.inventory.InfinityInventory;
import de.hyper.worlds.common.util.inventory.Inventory;
import de.hyper.worlds.common.util.inventory.buttons.NoButton;
import de.hyper.worlds.common.util.inventory.buttons.OpenInventoryButton;
import de.hyper.worlds.common.util.inventory.designs.BottomLineBackGroundDesign;
import de.hyper.worlds.common.util.items.GlassPane;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.ItemBuilder;
import de.hyper.worlds.common.util.items.SkullBuilder;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Cache;
import de.hyper.worlds.domain.using.Language;
import de.hyper.worlds.domain.using.Performance;
import org.bukkit.Material;

public class MembersInventory extends InfinityInventory<ServerUser> {

    protected ServerWorld serverWorld;
    Language lang = WorldManagement.get().getLanguage();
    Performance performance = WorldManagement.get().getPerformance();
    Cache cache = WorldManagement.get().getCache();

    public MembersInventory(ServerWorld serverWorld) {
        super("Members", 6, true);
        this.setDesign(new BottomLineBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.serverWorld = serverWorld;
        this.list = this.serverWorld.getMembers();
        this.maxPage = list.size() / 45;
        this.currentPage = 0;
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
        registerButton(6, 4, new OpenInventoryButton(new ServerWorldInventory(serverWorld), player),
                new ItemBuilder(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .setLore(
                                lang.getText("inventory.general.back.desc"))
                        .getItem());
        int row = 1;
        int slot = 0;
        for (int i = 0; i != 45; i++) {
            int get = i + (currentPage * 45);
            if (get < list.size()) {
                ServerUser serverUser = list.get(get);
                registerButton(row, slot,
                        new OpenInventoryButton(new UserInventory(serverWorld, serverUser), player),
                        new ItemBuilder(SkullBuilder.getSkullByPlayerName(serverUser.getName()))
                                .setDisplayName("§b" + serverUser.getName())
                                .setLore(
                                        "§7UUID: §b" + serverUser.getUuid().toString(),
                                        lang.getText("inventory.members.each.desc.1",
                                                serverUser.getWorldRole(serverWorld).getName()),
                                        lang.getText("inventory.members.each.desc.2"))
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