package de.hyper.worlds.domain.inventories;

import de.hyper.inventory.Inventory;
import de.hyper.inventory.buttons.Button;
import de.hyper.inventory.buttons.OpenInventoryButton;
import de.hyper.inventory.designs.TopBottomLineBackGroundDesign;
import de.hyper.inventory.items.GlassPane;
import de.hyper.inventory.items.SimpleItemData;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.SkullItemData;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

public class AttributesInventory extends Inventory {

    protected ServerWorld serverWorld;
    Language lang = WorldManagement.get().getLanguage();

    public AttributesInventory(Player player, ServerWorld serverWorld) {
        super(player, "Attributes", 3, false);
        this.setDesign(new TopBottomLineBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.serverWorld = serverWorld;
    }

    @Override
    public Inventory fillInventory() {
        registerButtonAndItem(2, 0,
                new OpenInventoryButton(WorldManagement.get().getInventoryManager(), new ServerWorldInventory(player, serverWorld), player),
                new SimpleItemData(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .addLore(
                                lang.getText("inventory.general.back.desc")));
        registerButtonAndItem(1, 2, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                if (serverWorld.isAllowed(player, "attributes.difficulty.change")) {
                    if (inventoryAction.equals(InventoryAction.PICKUP_ALL)) {
                        serverWorld.setDifficulty(serverWorld.getDifficulty().next());
                    } else if (inventoryAction.equals(InventoryAction.PICKUP_HALF)) {
                        serverWorld.setDifficulty(serverWorld.getDifficulty().last());
                    }
                    fillInventory();
                }
            }
        }, serverWorld.getDifficulty().buildItemData());
        registerButtonAndItem(1, 3, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                if (player.hasPermission("worldmanager.admin.bypass.category.change")) {
                    if (inventoryAction.equals(InventoryAction.PICKUP_ALL)) {
                        serverWorld.setCategoryType(serverWorld.getCategoryType().next());
                    } else if (inventoryAction.equals(InventoryAction.PICKUP_HALF)) {
                        serverWorld.setCategoryType(serverWorld.getCategoryType().last());
                    }
                    fillInventory();
                }
            }
        }, new SkullItemData(serverWorld.getCategoryType().getItemStack())
                .setDisplayName("§b" + serverWorld.getCategoryType().getLabel())
                .addLore(
                        lang.getText("inventory.attributes.category.desc.1")));
        registerButtonAndItem(1, 5, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                if (player.hasPermission("worldmanager.admin.bypass.generator.change")) {
                    if (inventoryAction.equals(InventoryAction.PICKUP_ALL)) {
                        serverWorld.setGeneratorType(serverWorld.getGeneratorType().next());
                    } else if (inventoryAction.equals(InventoryAction.PICKUP_HALF)) {
                        serverWorld.setGeneratorType(serverWorld.getGeneratorType().last());
                    }
                    fillInventory();
                }
            }
        }, new SkullItemData(HDBSkulls.GRASS_BLOCK)
                .setDisplayName("§b" + serverWorld.getGeneratorType().getName())
                .addLore(
                        lang.getText("inventory.attributes.generator.desc.1")));
        registerButtonAndItem(1, 6, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                if (player.hasPermission("worldmanager.admin.bypass.ignoration.change")) {
                    serverWorld.setIgnoreGeneration(!serverWorld.isIgnoreGeneration());
                    fillInventory();
                }
            }
        }, new SimpleItemData(Material.REDSTONE_TORCH)
                .setDisplayName(
                        lang.getText("inventory.attributes.ignoration.name"))
                .addLore(
                        lang.getText("inventory.attributes.ignoration.desc.1"),
                        lang.getText(
                                "inventory.attributes.ignoration.desc.2",
                                serverWorld.isIgnoreGeneration())));
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