package de.hyper.worlds.domain.inventories;

import de.hyper.inventory.Inventory;
import de.hyper.inventory.InventoryManager;
import de.hyper.inventory.buttons.Button;
import de.hyper.inventory.buttons.NoButton;
import de.hyper.inventory.buttons.OpenInventoryButton;
import de.hyper.inventory.designs.BorderedBackGroundDesign;
import de.hyper.inventory.items.GlassPane;
import de.hyper.inventory.items.SimpleItemData;
import de.hyper.worlds.common.obj.ServerUser;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.obj.world.role.WorldRole;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

public class ChangeRoleInventory extends Inventory {

    protected ServerWorld serverWorld;
    protected ServerUser serverUser;
    Language lang = WorldManagement.get().getLanguage();
    InventoryManager invManager = WorldManagement.get().getInventoryManager();

    public ChangeRoleInventory(Player player, ServerWorld serverWorld, ServerUser serverUser) {
        super(player, "Change Role", 5);
        this.setDesign(new BorderedBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.serverWorld = serverWorld;
        this.serverUser = serverUser;
    }

    @Override
    public Inventory fillInventory() {
        for (int r = 1; r < 4; r++) {
            registerButtonAndItem(r, 1, new NoButton(), GlassPane.C7);
        }
        registerButtonAndItem(4, 0,
                new OpenInventoryButton(invManager, new UserInventory(player, serverWorld, serverUser), player),
                new SimpleItemData(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .addLore(
                                lang.getText("inventory.general.back.desc")));
        registerButtonAndItem(3, 1, new Button() {
            @Override
            public void onClick(InventoryAction event) {
                serverUser.put(serverWorld, serverWorld.getDefaultRole());
                UserInventory userInventory = new UserInventory(player, serverWorld, serverUser);
                WorldManagement.get().getInventoryBuilder().buildInventory(userInventory);
            }
        }, serverWorld.getDefaultRole().buildDisplayItem());
        int row = 1;
        int slot = 2;
        for (WorldRole worldRole : serverWorld.getRoles()) {
            registerButtonAndItem(row, slot, new Button() {
                @Override
                public void onClick(InventoryAction inventoryAction) {
                    serverUser.put(serverWorld, worldRole);
                    UserInventory userInventory = new UserInventory(player, serverWorld, serverUser);
                    WorldManagement.get().getInventoryBuilder().buildInventory(userInventory);
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