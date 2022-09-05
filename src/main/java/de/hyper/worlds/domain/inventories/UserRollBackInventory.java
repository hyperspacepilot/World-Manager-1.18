package de.hyper.worlds.domain.inventories;

import de.hyper.worlds.common.enums.RollBackTimeTemplate;
import de.hyper.worlds.common.obj.ServerUser;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.util.TimeStampFormatting;
import de.hyper.worlds.common.util.inventory.Inventory;
import de.hyper.worlds.common.util.inventory.buttons.Button;
import de.hyper.worlds.common.util.inventory.buttons.FetchChatMessageButton;
import de.hyper.worlds.common.util.inventory.buttons.InventoryActionMultiButton;
import de.hyper.worlds.common.util.inventory.designs.TopBottomLineBackGroundDesign;
import de.hyper.worlds.common.util.items.GlassPane;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.ItemBuilder;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;

public class UserRollBackInventory extends Inventory {

    protected ServerWorld serverWorld;
    protected ServerUser serverUser;
    protected RollBackTimeTemplate rollBackTimeTemplate;
    protected long timeToRollBack;
    Language lang = WorldManagement.get().getLanguage();

    public UserRollBackInventory(ServerWorld serverWorld, ServerUser serverUser) {
        super("User-Rollback", 4);
        this.setDesign(new TopBottomLineBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.serverWorld = serverWorld;
        this.serverUser = serverUser;
        this.rollBackTimeTemplate = RollBackTimeTemplate.SIXTY_MINUTES;
        this.timeToRollBack = this.rollBackTimeTemplate.getTime();

    }

    @Override
    public Inventory fillInventory() {
        registerButton(2, 1, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                long timeToSubtract = 86400000;
                if (timeToRollBack - timeToSubtract > 0) {
                    timeToRollBack = timeToRollBack - timeToSubtract;
                    fillInventory();
                }
            }
        }, new ItemBuilder(HDBSkulls.RED_MINUS)
                .setDisplayName(
                        lang.getText("inventory.rollback.changetime.minus.oneday"))
                .setAmount(64)
                .getItem());
        registerButton(2, 2, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                long timeToSubtract = 3600000;
                if (timeToRollBack - timeToSubtract > 0) {
                    timeToRollBack = timeToRollBack - timeToSubtract;
                    fillInventory();
                }
            }
        }, new ItemBuilder(HDBSkulls.RED_MINUS)
                .setDisplayName(
                        lang.getText("inventory.rollback.changetime.minus.onehour"))
                .setAmount(10)
                .getItem());
        registerButton(2, 3, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                long timeToSubtract = 300000;
                if (timeToRollBack - timeToSubtract > 0) {
                    timeToRollBack = timeToRollBack - timeToSubtract;
                    fillInventory();
                }
            }
        }, new ItemBuilder(HDBSkulls.RED_MINUS)
                .setDisplayName(
                        lang.getText("inventory.rollback.changetime.minus.fiveminutes"))
                .setAmount(5)
                .getItem());


        registerButton(2, 4, new InventoryActionMultiButton().register(InventoryAction.PICKUP_ALL, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                rollBackTimeTemplate = rollBackTimeTemplate.next();
                timeToRollBack = rollBackTimeTemplate.getTime();
                fillInventory();
            }
        }).register(InventoryAction.PICKUP_HALF, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                rollBackTimeTemplate = rollBackTimeTemplate.last();
                timeToRollBack = rollBackTimeTemplate.getTime();
                fillInventory();
            }
        }).register(InventoryAction.MOVE_TO_OTHER_INVENTORY, new FetchChatMessageButton(UserRollBackInventory.this.player) {
            @Override
            public void onStartListening(InventoryAction inventoryAction) {
                setInstantDelete(false);
            }

            @Override
            public void onReceiveMessage(String message) {
                long newTime = TimeStampFormatting.convertLongFormString(message);
                timeToRollBack = newTime;
                open(this.player).fillInventory();
                setInstantDelete(true);
            }
        }), new ItemBuilder(Material.CLOCK)
                .setDisplayName(
                        lang.getText("inventory.rollback.templatetime.name"))
                .setLore(
                        this.rollBackTimeTemplate.getItemLore(this.timeToRollBack))
                .getItem());


        registerButton(2, 5, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                long timeToAdd = 300000;
                if (!(timeToRollBack + timeToAdd > Long.MAX_VALUE)) {
                    timeToRollBack = timeToRollBack + timeToAdd;
                    fillInventory();
                }
            }
        }, new ItemBuilder(HDBSkulls.GREEN_PLUS)
                .setDisplayName(
                        lang.getText("inventory.rollback.changetime.plus.fiveminutes"))
                .setAmount(5)
                .getItem());
        registerButton(2, 6, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                long timeToAdd = 3600000;
                if (!(timeToRollBack + timeToAdd > Long.MAX_VALUE)) {
                    timeToRollBack = timeToRollBack + timeToAdd;
                    fillInventory();
                }
            }
        }, new ItemBuilder(HDBSkulls.GREEN_PLUS)
                .setDisplayName(
                        lang.getText("inventory.rollback.changetime.plus.onehour"))
                .setAmount(10)
                .getItem());
        registerButton(2, 7, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                long timeToAdd = 86400000;
                if (!(timeToRollBack + timeToAdd > Long.MAX_VALUE)) {
                    timeToRollBack = timeToRollBack + timeToAdd;
                    fillInventory();
                }
            }
        }, new ItemBuilder(HDBSkulls.GREEN_PLUS)
                .setDisplayName(
                        lang.getText("inventory.rollback.changetime.plus.oneday"))
                .setAmount(64)
                .getItem());

        registerButton(3, 4, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                if (serverWorld.isAllowed(player, "rollback")) {
                    player.closeInventory();
                    String rollBackCommand =
                            "co rollback user: "
                                    + serverUser.getName()
                                    + " time: "
                                    + ((long) (timeToRollBack / 1000))
                                    + "s radius: #"
                                    + serverWorld.getWorldName();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rollBackCommand);
                }
            }
        }, new ItemBuilder(HDBSkulls.GREEN_CHECKMARK)
                .setDisplayName(
                        lang.getText("inventory.rollback.confirm.name"))
                .setLore(
                        lang.getText(
                                "inventory.rollback.confirm.desc.1",
                                serverUser.getName()))
                .getItem());

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
