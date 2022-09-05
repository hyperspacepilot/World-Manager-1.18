package de.hyper.worlds.domain.inventories;

import de.hyper.worlds.common.obj.ServerUser;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.util.inventory.InfinityInventory;
import de.hyper.worlds.common.util.inventory.Inventory;
import de.hyper.worlds.common.util.inventory.buttons.InventoryActionMultiButton;
import de.hyper.worlds.common.util.inventory.buttons.NoButton;
import de.hyper.worlds.common.util.inventory.buttons.OpenInventoryButton;
import de.hyper.worlds.common.util.inventory.designs.BottomLineBackGroundDesign;
import de.hyper.worlds.common.util.items.GlassPane;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.ItemBuilder;
import de.hyper.worlds.common.util.items.SkullBuilder;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;

public class HistoryInventory extends InfinityInventory<String> {

    protected ServerWorld serverWorld;
    Language lang = WorldManagement.get().getLanguage();

    public HistoryInventory(ServerWorld serverWorld) {
        super("History", 6, true);
        this.setDesign(new BottomLineBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.serverWorld = serverWorld;
        this.list = WorldManagement.get().getCoreProtectAPI().getUUIDsOfPlayersInWorld(serverWorld.getWorldName());
        this.currentPage = 0;
        this.maxPage = list.size() / 45;
    }

    @Override
    public Inventory fillInventory() {
        registerButton(6, 1,
                new OpenInventoryButton(new ServerWorldInventory(serverWorld), player),
                new ItemBuilder(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .setLore(
                                lang.getText("inventory.general.back.desc"))
                        .getItem());
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
                String stringUUID = list.get(get);
                ServerUser serverUser = WorldManagement.get().getCache().getServerUserByStringUUID(stringUUID);
                if (serverUser != null) {
                    registerButton(row, slot,
                            new InventoryActionMultiButton()
                                    .register(InventoryAction.PICKUP_ALL,
                                            new OpenInventoryButton(new UserHistoryInventory(serverWorld, serverUser), player))
                                    .register(InventoryAction.PICKUP_HALF,
                                            new OpenInventoryButton(new UserRollBackInventory(serverWorld, serverUser), player)),
                            new ItemBuilder(SkullBuilder.getSkullByPlayerName(serverUser.getName()))
                                    .setDisplayName("§b" + serverUser.getName())
                                    .setLore(
                                            lang.getText("inventory.history.user.desc.1"),
                                            lang.getText("inventory.history.user.desc.2"))
                                    .getItem());
                    slot++;
                    if (slot > 8) {
                        slot = 0;
                        row++;
                    }
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
