package de.hyper.worlds.domain.inventories;

import de.hyper.worlds.common.obj.ServerUser;
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

public class UserInventory extends Inventory {

    protected ServerWorld serverWorld;
    protected ServerUser serverUser;
    Language lang = WorldManagement.get().getLanguage();

    public UserInventory(ServerWorld serverWorld, ServerUser serverUser) {
        super("Member: " + serverUser.getName(), 3, true);
        this.setDesign(new TopBottomLineBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.serverWorld = serverWorld;
        this.serverUser = serverUser;
    }

    @Override
    public Inventory fillInventory() {
        registerButton(6, 0, new OpenInventoryButton(new MembersInventory(serverWorld), this.player),
                new ItemBuilder(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .setLore(
                                lang.getText("inventory.general.back.desc"))
                        .getItem());
        registerButton(2, 2, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                if (serverWorld.isAllowed(player, "users.role.change")) {
                    ChangeRoleInventory changeRoleInventory = new ChangeRoleInventory(serverWorld, serverUser);
                    changeRoleInventory.open(player).fillInventory();
                }
            }
        }, new ItemBuilder(HDBSkulls.CHANGE_IRON_BLUE)
                .setDisplayName(
                        lang.getText("inventory.user.change.name"))
                .setLore(
                        lang.getText("inventory.user.change.desc.1"))
                .getItem());
        registerButton(2, 4,
                new OpenInventoryButton(
                        new RoleInventory(
                                serverWorld,
                                serverUser.getWorldRole(serverWorld)),
                        player),
                serverUser.getWorldRole(serverWorld).buildDisplayItem());
        registerButton(2, 6, new Button() {
            @Override
            public void onClick(InventoryAction event) {
                if (serverWorld.isAllowed(player, "users.remove")) {
                    serverUser.removeRole(serverWorld);
                    MembersInventory membersInventory = new MembersInventory(serverWorld);
                    membersInventory.open(player).fillInventory();
                }
            }
        }, new ItemBuilder(HDBSkulls.RED_MINUS)
                .setDisplayName(
                        lang.getText("inventory.user.remove.name"))
                .setLore(
                        lang.getText("inventory.user.remove.desc.1"))
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