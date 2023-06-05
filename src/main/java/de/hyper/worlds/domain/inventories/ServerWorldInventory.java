package de.hyper.worlds.domain.inventories;

import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.util.inventory.Inventory;
import de.hyper.worlds.common.util.inventory.buttons.Button;
import de.hyper.worlds.common.util.inventory.buttons.NoButton;
import de.hyper.worlds.common.util.inventory.buttons.OpenInventoryButton;
import de.hyper.worlds.common.util.inventory.designs.CorneredBackgroundDesign;
import de.hyper.worlds.common.util.items.GlassPane;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.ItemBuilder;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Cache;
import de.hyper.worlds.domain.using.Language;
import de.hyper.worlds.domain.using.Performance;
import org.bukkit.event.inventory.InventoryAction;

public class ServerWorldInventory extends Inventory {

    protected ServerWorld serverWorld;
    Language lang = WorldManagement.get().getLanguage();
    Performance performance = WorldManagement.get().getPerformance();
    Cache cache = WorldManagement.get().getCache();

    public ServerWorldInventory(ServerWorld serverWorld) {
        super("World: " + serverWorld.getWorldName(), 5, true);
        this.setDesign(new CorneredBackgroundDesign(this.getRows(), null, GlassPane.C15, GlassPane.C7));
        this.serverWorld = serverWorld;
    }

    @Override
    public Inventory fillInventory() {
        registerButton(3, 4, new NoButton(),
                new ItemBuilder(HDBSkulls.EARTH)
                        .setDisplayName("§b" + serverWorld.getWorldName())
                        .getItem());
        registerButton(1, 4, new Button() {
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
        }, new ItemBuilder(HDBSkulls.EYE_OF_ENDER)
                .setDisplayName(
                        lang.getText("inventory.world.item.teleport.name"))
                .setLore(
                        lang.getText("inventory.world.item.teleport.desc"))
                .getItem());
        registerButton(2, 2, new OpenInventoryButton(new AttributesInventory(serverWorld), player),
                new ItemBuilder(HDBSkulls.MONITOR_I).
                        setDisplayName(
                                lang.getText("inventory.world.item.info.name")).
                        setLore(
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
                        ).getItem());
        registerButton(4, 2, new OpenInventoryButton(new RolesInventory(serverWorld), player),
                new ItemBuilder(HDBSkulls.COMPUTER_1)
                        .setDisplayName(
                                lang.getText("inventory.world.item.roles.name"))
                        .setLore(
                                lang.getText("inventory.world.item.roles.desc"))
                        .getItem());
        registerButton(4, 6, new OpenInventoryButton(new SettingsInventory(serverWorld), player),
                new ItemBuilder(HDBSkulls.COMPUTER_2)
                        .setDisplayName(
                                lang.getText("inventory.world.settings.name"))
                        .setLore(
                                lang.getText("inventory.world.settings.desc.1"))
                        .getItem());
        registerButton(2, 6, new OpenInventoryButton(new MembersInventory(serverWorld), player),
                new ItemBuilder(HDBSkulls.MINER_NOTCH)
                        .setDisplayName(
                                lang.getText("inventory.world.users.name"))
                        .setLore(
                                lang.getText("inventory.world.users.desc.1"))
                        .getItem());
        if (WorldManagement.get().getCoreProtectAPI().isExistsCO()) {
            /*registerButton(5, 4, new Button() {
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
    public Inventory cleanInventory() {
        return this;
    }

    @Override
    public void onOpen() {

    }

    @Override
    public void onClose() {

    }
}