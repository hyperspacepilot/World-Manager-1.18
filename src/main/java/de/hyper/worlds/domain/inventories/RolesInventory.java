package de.hyper.worlds.domain.inventories;

import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.obj.world.role.WorldRole;
import de.hyper.worlds.common.util.inventory.Inventory;
import de.hyper.worlds.common.util.inventory.buttons.Button;
import de.hyper.worlds.common.util.inventory.buttons.FetchChatMessageButton;
import de.hyper.worlds.common.util.inventory.buttons.NoButton;
import de.hyper.worlds.common.util.inventory.buttons.OpenInventoryButton;
import de.hyper.worlds.common.util.inventory.designs.BorderedBackGroundDesign;
import de.hyper.worlds.common.util.items.GlassPane;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.ItemBuilder;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;

public class RolesInventory extends Inventory {

    protected ServerWorld serverWorld;
    Language lang = WorldManagement.get().getLanguage();

    public RolesInventory(ServerWorld serverWorld) {
        super("Roles", 5, true);
        this.setDesign(new BorderedBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.serverWorld = serverWorld;
    }

    @Override
    public Inventory fillInventory() {
        for (int r = 2; r < 5; r++) {
            registerButton(r, 1, new NoButton(), GlassPane.C7);
        }
        registerButton(5, 0,
                new OpenInventoryButton(new ServerWorldInventory(serverWorld), player),
                new ItemBuilder(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .setLore(
                                lang.getText("inventory.general.back.desc"))
                        .getItem());
        registerButton(3, 1,
                new OpenInventoryButton(new RoleInventory(serverWorld, serverWorld.getDefaultRole()), player),
                serverWorld.getDefaultRole().buildDisplayItem());
        int row = 2;
        int slot = 2;
        for (WorldRole worldRole : serverWorld.getRoles()) {
            registerButton(row, slot,
                    new OpenInventoryButton(new RoleInventory(serverWorld, worldRole), player),
                    worldRole.buildDisplayItem(true));
            slot++;
            if (slot > 7) {
                slot = 2;
                row++;
            }
        }
        if (serverWorld.isAllowed(player, "roles.add")) {
            registerButton(5, 3, new FetchChatMessageButton(player) {
                @Override
                public void onStartListening(InventoryAction inventoryAction) {
                    setInstantDelete(false);
                }

                @Override
                public void onReceiveMessage(String message) {
                    if (!serverWorld.existsRoleWithName(message.replaceAll(" &", ""))) {
                        serverWorld.addRole(WorldManagement.get().getLoadHelper().cloneOfDefaultRole(
                                message.replaceAll(" &", "")));
                    }
                    open(player).cleanInventory().fillInventory();
                    setInstantDelete(true);
                }
            }, new ItemBuilder(HDBSkulls.GREEN_PLUS)
                    .setDisplayName(
                            lang.getText("inventory.roles.addrole.name"))
                    .setLore(
                            lang.getText("inventory.roles.addrole.desc.1"),
                            lang.getText("inventory.roles.addrole.desc.2"),
                            lang.getText("inventory.roles.addrole.desc.3"),
                            lang.getText(
                                    "inventory.roles.addrole.desc.4",
                                    WorldManagement.get().getMaxRoles()),
                            lang.getText("inventory.roles.addrole.desc.5"))
                    .getItem());
        }
        if (serverWorld.isAllowed(player, "roles.reset")) {
            registerButton(5, 5, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    serverWorld.resetRoles();
                    cleanInventory().fillInventory();
                }
            }, new ItemBuilder(Material.LAVA_BUCKET)
                    .setDisplayName(
                            lang.getText("inventory.roles.resetroles.name"))
                    .setLore(
                            lang.getText("inventory.roles.resetroles.desc.1"))
                    .getItem());
        }
        return this;
    }

    @Override
    public Inventory cleanInventory() {
        int slot = 0;
        int row = 2;
        for (int i = 1; i <= 18; i++) {
            registerButton(row, slot, new NoButton(), null);
            slot++;
            if (slot > 8) {
                slot = 0;
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
}
