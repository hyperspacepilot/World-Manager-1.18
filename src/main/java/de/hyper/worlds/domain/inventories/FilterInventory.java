package de.hyper.worlds.domain.inventories;

import de.hyper.inventory.ChooseInventory;
import de.hyper.inventory.Inventory;
import de.hyper.inventory.InventoryManager;
import de.hyper.inventory.buttons.Button;
import de.hyper.inventory.buttons.FetchChatMessageButton;
import de.hyper.inventory.buttons.OpenInventoryButton;
import de.hyper.inventory.designs.TopBottomLineBackGroundDesign;
import de.hyper.inventory.items.GlassPane;
import de.hyper.inventory.items.ItemData;
import de.hyper.inventory.items.SimpleItemData;
import de.hyper.worlds.common.enums.FilterCategoryType;
import de.hyper.worlds.common.enums.FilterGeneratorType;
import de.hyper.worlds.common.obj.world.WorldFilter;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.SkullItemData;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

public class FilterInventory extends Inventory {

    protected WorldFilter worldFilter;
    Language lang = WorldManagement.get().getLanguage();
    InventoryManager invManager = WorldManagement.get().getInventoryManager();

    public FilterInventory(Player player) {
        super(player, "Worlds-Filtering", 3);
        this.setDesign(new TopBottomLineBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.worldFilter = new WorldFilter();
    }

    @Override
    public Inventory fillInventory() {
        registerButtonAndItem(2, 0,
                new OpenInventoryButton(invManager, new MainInventory(player), player),
                new SimpleItemData(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .addLore(
                                lang.getText("inventory.general.back.desc")));
        registerButtonAndItem(1, 1, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                ChooseInventory<FilterCategoryType> chooseInventory = new ChooseInventory<FilterCategoryType>(player, 4, FilterCategoryType.asList()) {
                    @Override
                    public Inventory clearInventory() {
                        return this;
                    }

                    @Override
                    public ItemData buildItemStackData(FilterCategoryType filterCategoryType) {
                        return new SkullItemData(filterCategoryType.getItemStack(filterCategoryType.getCategoryType()))
                                .setDisplayName(
                                        lang.getText(
                                                "inventory.filter.subinv.cat.each.name",
                                                filterCategoryType.getLabel()))
                                .addLore(
                                        lang.getText(
                                                "inventory.filter.subinv.cat.each.desc.1",
                                                filterCategoryType.getLabel()));
                    }

                    @Override
                    public void selected(FilterCategoryType filterCategoryType) {
                        worldFilter.setCategoryType(filterCategoryType);
                        FilterInventory filterInventory = new FilterInventory(player);
                        WorldManagement.get().getInventoryBuilder().buildInventory(filterInventory);
                    }
                };
                WorldManagement.get().getInventoryBuilder().buildInventory(chooseInventory);
            }
        }, new SkullItemData(worldFilter.getCategoryType().getItemStack(worldFilter.getCategoryType().getCategoryType()))
                .setDisplayName(
                        lang.getText("inventory.filter.category.name"))
                .addLore(
                        lang.getText("inventory.filter.category.desc.1")));
        registerButtonAndItem(1, 3, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                ChooseInventory<FilterGeneratorType> chooseInventory = new ChooseInventory<FilterGeneratorType>(player, 3, FilterGeneratorType.asList()) {
                    @Override
                    public Inventory clearInventory() {
                        return this;
                    }

                    @Override
                    public void selected(FilterGeneratorType filterGeneratorType) {
                        worldFilter.setGeneratorType(filterGeneratorType);
                        FilterInventory filterInventory = new FilterInventory(player);
                        WorldManagement.get().getInventoryBuilder().buildInventory(filterInventory);
                    }

                    @Override
                    public ItemData buildItemStackData(FilterGeneratorType filterGeneratorType) {
                        return new SkullItemData(filterGeneratorType.getItemStack())
                                .setDisplayName(
                                        lang.getText(
                                                "inventory.filter.subinv.gen.each.name",
                                                filterGeneratorType.getLabel()))
                                .addLore(
                                        lang.getText(
                                                "inventory.filter.subinv.gen.each.desc.1",
                                                filterGeneratorType.getLabel()));
                    }
                };
                WorldManagement.get().getInventoryBuilder().buildInventory(chooseInventory);
            }
        }, new SkullItemData(worldFilter.getGeneratorType().getItemStack())
                .setDisplayName(lang.getText("inventory.filter.generator.name"))
                .addLore(lang.getText("inventory.filter.generator.desc.1")));
        registerButtonAndItem(1, 5, new FetchChatMessageButton(WorldManagement.get(), player) {
            @Override
            public void onReceiveMessage(String message) {
                worldFilter.setName(message.replace(" ", ""));
                reopen();
            }
        }, new SimpleItemData(Material.PAPER)
                .setDisplayName(
                        lang.getText("inventory.filter.name.name"))
                .addLore(
                        lang.getText("inventory.filter.name.desc.1"),
                        lang.getText(
                                "inventory.filter.name.desc.2",
                                worldFilter.getName().replace("#", ""))));
        registerButtonAndItem(1, 7, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                ServerWorldsInventory worldsInventory = new ServerWorldsInventory(player,
                        worldFilter.filter(WorldManagement.get().getCache().getAllServerWorlds()));
                WorldManagement.get().getInventoryBuilder().buildInventory(worldsInventory);
            }
        }, new SkullItemData(HDBSkulls.GREEN_CHECKMARK)
                .setDisplayName(
                        lang.getText("inventory.filter.success.name"))
                .addLore(
                        lang.getText("inventory.filter.success.desc.1")));
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

    public void reopen() {
        WorldManagement.get().getInventoryBuilder().buildInventory(this);
    }
}