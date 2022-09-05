package de.hyper.worlds.domain.inventories;

import de.hyper.worlds.common.obj.ServerUser;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.obj.world.role.WorldRole;
import de.hyper.worlds.common.util.inventory.Inventory;
import de.hyper.worlds.common.util.inventory.buttons.Button;
import de.hyper.worlds.common.util.inventory.buttons.NoButton;
import de.hyper.worlds.common.util.inventory.buttons.OpenInventoryButton;
import de.hyper.worlds.common.util.inventory.designs.BorderedBackGroundDesign;
import de.hyper.worlds.common.util.items.GlassPane;
import de.hyper.worlds.common.util.items.ItemBuilder;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;

public class ChangeRoleInventory extends Inventory {

    protected ServerWorld serverWorld;
    protected ServerUser serverUser;
    Language lang = WorldManagement.get().getLanguage();

    public ChangeRoleInventory(ServerWorld serverWorld, ServerUser serverUser) {
        super("Change Role", 5, true);
        this.setDesign(new BorderedBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.serverWorld = serverWorld;
        this.serverUser = serverUser;
    }

    @Override
    public Inventory fillInventory() {
        for (int r = 2; r < 5; r++) {
            registerButton(r, 1, new NoButton(), GlassPane.C7);
        }
        registerButton(5, 0,
                new OpenInventoryButton(new UserInventory(serverWorld, serverUser), player),
                new ItemBuilder(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .setLore(
                                lang.getText("inventory.general.back.desc"))
                        .getItem());
        registerButton(3, 1, new Button() {
            @Override
            public void onClick(InventoryAction event) {
                serverUser.put(serverWorld, serverWorld.getDefaultRole());
                UserInventory userInventory = new UserInventory(serverWorld, serverUser);
                userInventory.open(player).fillInventory();
            }
        }, serverWorld.getDefaultRole().buildDisplayItem());
        int row = 2;
        int slot = 2;
        for (WorldRole worldRole : serverWorld.getRoles()) {
            registerButton(row, slot, new Button() {
                @Override
                public void onClick(InventoryAction inventoryAction) {
                    serverUser.put(serverWorld, worldRole);
                    UserInventory userInventory = new UserInventory(serverWorld, serverUser);
                    userInventory.open(player).fillInventory();
                }
            }, worldRole.buildDisplayItem(false));
            slot++;
            if (slot > 7) {
                slot = 2;
                row++;
            }
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
