package de.hyper.worlds.domain.inventories;

import de.hyper.inventory.Inventory;
import de.hyper.inventory.InventoryManager;
import de.hyper.inventory.buttons.Button;
import de.hyper.inventory.buttons.NoButton;
import de.hyper.inventory.buttons.OpenInventoryButton;
import de.hyper.inventory.designs.CorneredBackGroundDesign;
import de.hyper.inventory.items.GlassPane;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.SkullItemData;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Cache;
import de.hyper.worlds.domain.using.Language;
import de.hyper.worlds.domain.using.Performance;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

public class ServerWorldInventory extends Inventory {

    protected ServerWorld serverWorld;
    Language lang = WorldManagement.get().getLanguage();
    Performance performance = WorldManagement.get().getPerformance();
    Cache cache = WorldManagement.get().getCache();
    InventoryManager invManager = WorldManagement.get().getInventoryManager();

    public ServerWorldInventory(Player player, ServerWorld serverWorld) {
        super(player, "World: " + serverWorld.getWorldName(), 5, false);
        this.setDesign(new CorneredBackGroundDesign(this.getRows(), null, GlassPane.C15, GlassPane.C7));
        this.serverWorld = serverWorld;
    }

    @Override
    public Inventory fillInventory() {
        registerButtonAndItem(2, 4, new NoButton(),
                new SkullItemData(HDBSkulls.EARTH)
                        .setDisplayName("§b" + serverWorld.getWorldName()));
        registerButtonAndItem(0, 4, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                if (serverWorld.isAllowed(player, "enter")) {
                    performance.sync(() -> {
                        if (!WorldManagement.get().getCache().isLoadedWorld(serverWorld.getWorldName())) {
                            serverWorld.load();
                        }
                        player.teleport(serverWorld.getSpawnLocation());
                    });
                }
            }
        }, new SkullItemData(HDBSkulls.EYE_OF_ENDER)
                .setDisplayName(
                        lang.getText("inventory.world.item.teleport.name"))
                .addLore(
                        lang.getText("inventory.world.item.teleport.desc")));
        registerButtonAndItem(1, 2, new OpenInventoryButton(WorldManagement.get().getInventoryManager(), new AttributesInventory(player, serverWorld), player),
                new SkullItemData(HDBSkulls.MONITOR_I).
                        setDisplayName(
                                lang.getText("inventory.world.item.info.name")).
                        addLore(
                                lang.getText(
                                        "inventory.world.item.info.desc.1",
                                        serverWorld.getWorldName()),
                                lang.getText(
                                        "inventory.world.item.info.desc.2",
                                        cache.getServerUser(serverWorld.getOwnerUUID()).getName()),
                                lang.getText(
                                        "inventory.world.item.info.desc.3",
                                        ((serverWorld.isAllowed(player, "seeseed") ?
                                                serverWorld.getRealSeedOfBukkitWorld() :
                                                lang.getText("inventory.world.item.info.cannotseeseed")))),
                                lang.getText(
                                        "inventory.world.item.info.desc.4",
                                        lang.getText("inventory.attributes.difficulty.desc." + serverWorld.getDifficulty().getLKey())),
                                lang.getText(
                                        "inventory.world.item.info.desc.5",
                                        serverWorld.getGeneratorType().getName()),
                                lang.getText(
                                        "inventory.world.item.info.desc.6",
                                        serverWorld.getCategoryType().getLabel()),
                                " ",
                                lang.getText("inventory.world.item.info.desc.7")
                        ));
        registerButtonAndItem(3, 2, new OpenInventoryButton(invManager, new RolesInventory(player, serverWorld), player),
                new SkullItemData(HDBSkulls.COMPUTER_1)
                        .setDisplayName(
                                lang.getText("inventory.world.item.roles.name"))
                        .addLore(
                                lang.getText("inventory.world.item.roles.desc")));
        registerButtonAndItem(3, 6, new OpenInventoryButton(invManager, new SettingsInventory(player, serverWorld), player),
                new SkullItemData(HDBSkulls.COMPUTER_2)
                        .setDisplayName(
                                lang.getText("inventory.world.settings.name"))
                        .addLore(
                                lang.getText("inventory.world.settings.desc.1")));
        registerButtonAndItem(1, 6, new OpenInventoryButton(invManager, new MembersInventory(player, serverWorld), player),
                new SkullItemData(HDBSkulls.MINER_NOTCH)
                        .setDisplayName(
                                lang.getText("inventory.world.users.name"))
                        .addLore(
                                lang.getText("inventory.world.users.desc.1")));
        if (WorldManagement.get().getCoreProtectAPI().isExistsCO()) {
            /*registerButton(4, 4, new Button() {
                        @Override
                        public void onClick(InventoryAction inventoryAction) {
                            WorldManagement.get().getPerformance().async(() -> {
                                HistoryInventory inventory1 = new HistoryInventory(serverWorld);
                                inventory1.open(player);
                                inventory1.fillInventory();
                            });
                        }
                    },
                    new ItemBuilder(HDBSkulls.CLOCK)
                            .setDisplayName(
                                    lang.getText("inventory.world.history.name"))
                            .setLore(
                                    lang.getText("inventory.world.history.desc.1"))
                            .getItem());

             */
        }
        return this;
    }

    @Override
    public Inventory clearInventory() {
        return this;
    }

    @Override
    public void onOpen() {
    }

    @Override
    public void onClose() {
    }
}