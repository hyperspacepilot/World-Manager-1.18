package de.hyper.worlds.domain.inventories;

import de.hyper.inventory.Inventory;
import de.hyper.inventory.InventoryManager;
import de.hyper.inventory.buttons.Button;
import de.hyper.inventory.buttons.FetchChatMessageButton;
import de.hyper.inventory.buttons.NoButton;
import de.hyper.inventory.buttons.OpenInventoryButton;
import de.hyper.inventory.designs.BorderedBackGroundDesign;
import de.hyper.inventory.items.GlassPane;
import de.hyper.inventory.items.SimpleItemData;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.obj.world.role.WorldRole;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.SkullItemData;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

public class RolesInventory extends Inventory {

    protected ServerWorld serverWorld;
    Language lang = WorldManagement.get().getLanguage();
    InventoryManager invManager = WorldManagement.get().getInventoryManager();

    public RolesInventory(Player player, ServerWorld serverWorld) {
        super(player, "Roles", 5, false);
        this.setDesign(new BorderedBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.serverWorld = serverWorld;
    }

    @Override
    public Inventory fillInventory() {
        for (int r = 1; r < 4; r++) {
            registerButtonAndItem(r, 1, new NoButton(), GlassPane.C7);
        }
        registerButtonAndItem(4, 0,
                new OpenInventoryButton(invManager, new ServerWorldInventory(player, serverWorld), player),
                new SimpleItemData(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .addLore(
                                lang.getText("inventory.general.back.desc")));
        registerButtonAndItem(2, 1,
                new OpenInventoryButton(invManager, new RoleInventory(player, serverWorld, serverWorld.getDefaultRole()), player),
                serverWorld.getDefaultRole().buildDisplayItem());
        int row = 1;
        int slot = 2;
        for (WorldRole worldRole : serverWorld.getRoles()) {
            registerButtonAndItem(row, slot,
                    new OpenInventoryButton(invManager, new RoleInventory(player, serverWorld, worldRole), player),
                    worldRole.buildDisplayItem(true));
            slot++;
            if (slot > 7) {
                slot = 2;
                row++;
            }
        }
        if (serverWorld.isAllowed(player, "roles.add")) {
            registerButtonAndItem(4, 3, new FetchChatMessageButton(WorldManagement.get(), player) {
                @Override
                public void onReceiveMessage(String message) {
                    if (!serverWorld.existsRoleWithName(message.replaceAll(" &", ""))) {
                        serverWorld.addRole(WorldManagement.get().getLoadHelper().cloneOfDefaultRole(
                                message.replaceAll(" &", "")));
                    }
                    reopen();
                }
            }, new SkullItemData(HDBSkulls.GREEN_PLUS)
                    .setDisplayName(
                            lang.getText("inventory.roles.addrole.name"))
                    .addLore(
                            lang.getText("inventory.roles.addrole.desc.1"),
                            lang.getText("inventory.roles.addrole.desc.2"),
                            lang.getText("inventory.roles.addrole.desc.3"),
                            lang.getText(
                                    "inventory.roles.addrole.desc.4",
                                    WorldManagement.get().getMaxRoles()),
                            lang.getText("inventory.roles.addrole.desc.5")));
        }
        if (serverWorld.isAllowed(player, "roles.reset")) {
            registerButtonAndItem(4, 5, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    serverWorld.resetRoles();
                    clearInventory().fillInventory();
                }
            }, new SimpleItemData(Material.LAVA_BUCKET)
                    .setDisplayName(
                            lang.getText("inventory.roles.resetroles.name"))
                    .addLore(
                            lang.getText("inventory.roles.resetroles.desc.1")));
        }
        return this;
    }

    @Override
    public Inventory clearInventory() {
        int slot = 2;
        int row = 1;
        for (int i = 1; i <= 18; i++) {
            registerButtonAndItem(row, slot, new NoButton(), null);
            slot++;
            if (slot > 7) {
                slot = 2;
                row++;
            }
        }
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