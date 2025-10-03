package de.hyper.worlds.domain.inventories;

import de.hyper.inventory.InfinityInventory;
import de.hyper.inventory.Inventory;
import de.hyper.inventory.InventoryManager;
import de.hyper.inventory.buttons.InventoryActionMultiButton;
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
import de.hyper.worlds.domain.using.Language;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

public class HistoryInventory extends InfinityInventory<String> {

    protected ServerWorld serverWorld;
    Language lang = WorldManagement.get().getLanguage();
    InventoryManager invManager = WorldManagement.get().getInventoryManager();

    public HistoryInventory(Player player, ServerWorld serverWorld) {
        super(player, "History", 6);
        this.setDesign(new BottomLineBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.serverWorld = serverWorld;
        this.list = WorldManagement.get().getCoreProtectAPI().getUUIDsOfPlayersInWorld(serverWorld.getWorldName());
        this.currentPage = 0;
        this.maxPage = list.size() / 45;
    }

    @Override
    public Inventory fillInventory() {
        registerButtonAndItem(5, 1,
                new OpenInventoryButton(invManager, new ServerWorldInventory(player, serverWorld), player),
                new SimpleItemData(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .addLore(
                                lang.getText("inventory.general.back.desc")));
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
                String stringUUID = list.get(get);
                ServerUser serverUser = WorldManagement.get().getCache().getServerUserByStringUUID(stringUUID);
                if (serverUser != null) {
                    registerButtonAndItem(row, slot,
                            new InventoryActionMultiButton()
                                    .register(InventoryAction.PICKUP_ALL,
                                            new OpenInventoryButton(invManager, new UserHistoryInventory(player, serverWorld, serverUser), player))
                                    .register(InventoryAction.PICKUP_HALF,
                                            new OpenInventoryButton(invManager, new UserRollBackInventory(player, serverWorld, serverUser), player)),
                            new SimpleItemData(SkullBuilder.getSkullByPlayerName(serverUser.getName()))
                                    .setDisplayName("§b" + serverUser.getName())
                                    .addLore(
                                            lang.getText("inventory.history.user.desc.1"),
                                            lang.getText("inventory.history.user.desc.2")));
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
    public Inventory clearInventory() {
        int row = 0;
        int slot = 0;
        for (int i = 0; i != 45; i++) {
            registerButtonAndItem(row, slot, new NoButton(), null);
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