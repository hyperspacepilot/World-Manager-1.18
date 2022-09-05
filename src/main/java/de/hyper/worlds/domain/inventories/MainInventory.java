package de.hyper.worlds.domain.inventories;

import de.hyper.worlds.common.util.inventory.Inventory;
import de.hyper.worlds.common.util.inventory.buttons.OpenInventoryButton;
import de.hyper.worlds.common.util.inventory.designs.CorneredBackgroundDesign;
import de.hyper.worlds.common.util.items.GlassPane;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.ItemBuilder;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import org.bukkit.Material;

public class MainInventory extends Inventory {

    Language lang = WorldManagement.get().getLanguage();

    public MainInventory() {
        super("Worlds", 5, true);
        this.setDesign(new CorneredBackgroundDesign(this.getRows(), null, GlassPane.C15, GlassPane.C7));
    }

    @Override
    public Inventory fillInventory() {
        registerButton(3, 2,
                new OpenInventoryButton(new ServerWorldsInventory(WorldManagement.get().getCache().getAllServerWorlds()), player),
                new ItemBuilder(Material.CAULDRON)
                        .setDisplayName(
                                lang.getText("inventory.main.all.name"))
                        .setLore(
                                lang.getText("inventory.main.all.desc.1"))
                        .getItem());
        registerButton(3, 4,
                new OpenInventoryButton(new ServerWorldInventory(WorldManagement.get().getCache().getServerWorld(player.getWorld())), player),
                new ItemBuilder(HDBSkulls.OAK_WOOD_ARROW_DOWN)
                        .setDisplayName(
                                lang.getText("inventory.main.current.name"))
                        .setLore(
                                lang.getText("inventory.main.current.desc.1"))
                        .getItem());
        registerButton(3, 6,
                new OpenInventoryButton(new FilterInventory(), player),
                new ItemBuilder(Material.SPYGLASS)
                        .setDisplayName(
                                lang.getText("inventory.main.filter.name"))
                        .setLore(
                                lang.getText("inventory.main.filter.desc.1"))
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
