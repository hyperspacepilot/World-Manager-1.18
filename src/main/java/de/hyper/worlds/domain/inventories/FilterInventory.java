package de.hyper.worlds.domain.inventories;

import de.hyper.worlds.common.enums.FilterCategoryType;
import de.hyper.worlds.common.enums.FilterGeneratorType;
import de.hyper.worlds.common.obj.world.WorldFilter;
import de.hyper.worlds.common.util.inventory.ChooseInventory;
import de.hyper.worlds.common.util.inventory.Inventory;
import de.hyper.worlds.common.util.inventory.buttons.Button;
import de.hyper.worlds.common.util.inventory.buttons.FetchChatMessageButton;
import de.hyper.worlds.common.util.inventory.buttons.OpenInventoryButton;
import de.hyper.worlds.common.util.inventory.designs.TopBottomLineBackGroundDesign;
import de.hyper.worlds.common.util.items.GlassPane;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.ItemBuilder;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public class FilterInventory extends Inventory {

    protected WorldFilter worldFilter;
    Language lang = WorldManagement.get().getLanguage();

    public FilterInventory() {
        super("Worlds-Filtering", 3, true);
        this.setDesign(new TopBottomLineBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.worldFilter = new WorldFilter();
        setInstantDelete(false);
    }

    @Override
    public Inventory fillInventory() {
        registerButton(3, 0,
                new OpenInventoryButton(new MainInventory(), player),
                new ItemBuilder(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .setLore(
                                lang.getText("inventory.general.back.desc"))
                        .getItem());
        registerButton(2, 1, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                ChooseInventory<FilterCategoryType> chooseInventory = new ChooseInventory<>(4, FilterCategoryType.asList()) {
                    @Override
                    public void selected(FilterCategoryType filterCategoryType) {
                        worldFilter.setCategoryType(filterCategoryType);
                        FilterInventory.this.open(player).fillInventory();
                    }

                    @Override
                    public ItemStack buildItem(FilterCategoryType filterCategoryType) {
                        return new ItemBuilder(filterCategoryType.getItemStack(filterCategoryType.getCategoryType()))
                                .setDisplayName(
                                        lang.getText(
                                                "inventory.filter.subinv.cat.each.name",
                                                filterCategoryType.getLabel()))
                                .setLore(
                                        lang.getText(
                                                "inventory.filter.subinv.cat.each.desc.1",
                                                filterCategoryType.getLabel()))
                                .getItem();
                    }
                };
                chooseInventory.fillInventory().open(FilterInventory.this.player);
            }
        }, new ItemBuilder(worldFilter.getCategoryType().getItemStack(worldFilter.getCategoryType().getCategoryType()))
                .setDisplayName(
                        lang.getText("inventory.filter.category.name"))
                .setLore(
                        lang.getText("inventory.filter.category.desc.1"))
                .getItem());
        registerButton(2, 3, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                ChooseInventory<FilterGeneratorType> chooseInventory = new ChooseInventory<>(3, FilterGeneratorType.asList()) {
                    @Override
                    public void selected(FilterGeneratorType filterGeneratorType) {
                        worldFilter.setGeneratorType(filterGeneratorType);
                        FilterInventory.this.open(player).fillInventory();
                    }

                    @Override
                    public ItemStack buildItem(FilterGeneratorType filterGeneratorType) {
                        return new ItemBuilder(filterGeneratorType.getItemStack())
                                .setDisplayName(
                                        lang.getText(
                                                "inventory.filter.subinv.gen.each.name",
                                                filterGeneratorType.getLabel()))
                                .setLore(
                                        lang.getText(
                                                "inventory.filter.subinv.gen.each.desc.1",
                                                filterGeneratorType.getLabel()))
                                .getItem();
                    }
                };
                chooseInventory.fillInventory().open(FilterInventory.this.player);
            }
        }, new ItemBuilder(worldFilter.getGeneratorType().getItemStack())
                .setDisplayName(
                        lang.getText("inventory.filter.generator.name"))
                .setLore(
                        lang.getText("inventory.filter.generator.desc.1"))
                .getItem());
        registerButton(2, 5, new FetchChatMessageButton(player) {
            @Override
            public void onStartListening(InventoryAction inventoryAction) {
                setInstantDelete(false);
            }

            @Override
            public void onReceiveMessage(String message) {
                worldFilter.setName(message.replace(" ", ""));
                open(player).fillInventory();
                setInstantDelete(true);
            }
        }, new ItemBuilder(Material.PAPER)
                .setDisplayName(
                        lang.getText("inventory.filter.name.name"))
                .setLore(
                        lang.getText("inventory.filter.name.desc.1"),
                        lang.getText(
                                "inventory.filter.name.desc.2",
                                worldFilter.getName().replace("#", "")))
                .getItem());
        registerButton(2, 7, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                setInstantDelete(true);
                ServerWorldsInventory worldsInventory = new ServerWorldsInventory(
                        worldFilter.filter(WorldManagement.get().getCache().getAllServerWorlds()));
                worldsInventory.open(player).fillInventory();
            }
        }, new ItemBuilder(HDBSkulls.GREEN_CHECKMARK)
                .setDisplayName(
                        lang.getText("inventory.filter.success.name"))
                .setLore(
                        lang.getText("inventory.filter.success.desc.1"))
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