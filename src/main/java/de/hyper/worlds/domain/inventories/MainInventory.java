package de.hyper.worlds.domain.inventories;

import de.hyper.inventory.Inventory;
import de.hyper.inventory.InventoryManager;
import de.hyper.inventory.buttons.OpenInventoryButton;
import de.hyper.inventory.designs.CorneredBackGroundDesign;
import de.hyper.inventory.items.GlassPane;
import de.hyper.inventory.items.SimpleItemData;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.SkullItemData;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MainInventory extends Inventory {

    Language lang = WorldManagement.get().getLanguage();
    InventoryManager inventoryManager = WorldManagement.get().getInventoryManager();

    public MainInventory(Player player) {
        super(player, "Worlds", 5, false);
        this.setDesign(new CorneredBackGroundDesign(this.getRows(), null, GlassPane.C15, GlassPane.C7));
    }

    @Override
    public Inventory fillInventory() {
        registerButtonAndItem(2, 2,
                new OpenInventoryButton(inventoryManager, new ServerWorldsInventory(player, WorldManagement.get().getCache().getAllServerWorlds()), player),
                new SimpleItemData(Material.CAULDRON)
                        .setDisplayName(
                                lang.getText("inventory.main.all.name"))
                        .addLore(
                                lang.getText("inventory.main.all.desc.1")));
        if (WorldManagement.get().getCache().existsServerWorld(player.getWorld().getName())) {
            registerButtonAndItem(2, 4,
                    new OpenInventoryButton(inventoryManager, new ServerWorldInventory(player, WorldManagement.get().getCache().getServerWorld(player.getWorld())), player),
                    new SkullItemData(HDBSkulls.OAK_WOOD_ARROW_DOWN)
                            .setDisplayName(
                                    lang.getText("inventory.main.current.name"))
                            .addLore(
                                    lang.getText("inventory.main.current.desc.1")));
        }
        registerButtonAndItem(2, 6,
                new OpenInventoryButton(inventoryManager, new FilterInventory(player), player),
                new SimpleItemData(Material.SPYGLASS)
                        .setDisplayName(
                                lang.getText("inventory.main.filter.name"))
                        .addLore(
                                lang.getText("inventory.main.filter.desc.1")));
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