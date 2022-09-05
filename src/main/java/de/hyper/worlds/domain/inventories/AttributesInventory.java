package de.hyper.worlds.domain.inventories;

import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.util.inventory.Inventory;
import de.hyper.worlds.common.util.inventory.buttons.Button;
import de.hyper.worlds.common.util.inventory.buttons.OpenInventoryButton;
import de.hyper.worlds.common.util.inventory.designs.TopBottomLineBackGroundDesign;
import de.hyper.worlds.common.util.items.GlassPane;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.ItemBuilder;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;

public class AttributesInventory extends Inventory {

    protected ServerWorld serverWorld;
    Language lang = WorldManagement.get().getLanguage();

    public AttributesInventory(ServerWorld serverWorld) {
        super("Attributes", 3, true);
        this.setDesign(new TopBottomLineBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.serverWorld = serverWorld;
    }

    @Override
    public Inventory fillInventory() {
        registerButton(3, 0,
                new OpenInventoryButton(new ServerWorldInventory(serverWorld), player),
                new ItemBuilder(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .setLore(
                                lang.getText("inventory.general.back.desc"))
                        .getItem());
        registerButton(2, 2, new Button() {
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
        }, serverWorld.getDifficulty().buildItemStack());
        registerButton(2, 3, new Button() {
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
        }, new ItemBuilder(serverWorld.getCategoryType().getItemStack())
                .setDisplayName("§b" + serverWorld.getCategoryType().getLabel())
                .setLore(
                        lang.getText("inventory.attributes.category.desc.1"))
                .getItem());
        registerButton(2, 5, new Button() {
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
        }, new ItemBuilder(HDBSkulls.GRASS_BLOCK)
                .setDisplayName("§b" + serverWorld.getGeneratorType().getName())
                .setLore(
                        lang.getText("inventory.attributes.generator.desc.1"))
                .getItem());
        registerButton(2, 6, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                if (player.hasPermission("worldmanager.admin.bypass.ignoration.change")) {
                    serverWorld.setIgnoreGeneration(!serverWorld.isIgnoreGeneration());
                    fillInventory();
                }
            }
        }, new ItemBuilder(Material.REDSTONE_TORCH)
                .setDisplayName(
                        lang.getText("inventory.attributes.ignoration.name"))
                .setLore(
                        lang.getText("inventory.attributes.ignoration.desc.1"),
                        lang.getText(
                                "inventory.attributes.ignoration.desc.2",
                                serverWorld.isIgnoreGeneration())).getItem());
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
