package de.hyper.worlds.domain.inventories;

import de.hyper.inventory.Inventory;
import de.hyper.inventory.InventoryManager;
import de.hyper.inventory.buttons.Button;
import de.hyper.inventory.buttons.OpenInventoryButton;
import de.hyper.inventory.designs.TopBottomLineBackGroundDesign;
import de.hyper.inventory.items.GlassPane;
import de.hyper.inventory.items.SimpleItemData;
import de.hyper.worlds.common.obj.ServerUser;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.SkullItemData;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

public class UserInventory extends Inventory {

    protected ServerWorld serverWorld;
    protected ServerUser serverUser;
    Language lang = WorldManagement.get().getLanguage();
    InventoryManager invManager = WorldManagement.get().getInventoryManager();

    public UserInventory(Player player, ServerWorld serverWorld, ServerUser serverUser) {
        super(player, "Member: " + serverUser.getName(), 3);
        this.setDesign(new TopBottomLineBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.serverWorld = serverWorld;
        this.serverUser = serverUser;
    }

    @Override
    public Inventory fillInventory() {
        registerButtonAndItem(2, 0, new OpenInventoryButton(invManager, new MembersInventory(player, serverWorld), this.player),
                new SimpleItemData(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .addLore(
                                lang.getText("inventory.general.back.desc")));
        registerButtonAndItem(1, 2, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                if (serverWorld.isAllowed(player, "users.role.change")) {
                    ChangeRoleInventory changeRoleInventory = new ChangeRoleInventory(player, serverWorld, serverUser);
                    WorldManagement.get().getInventoryBuilder().buildInventory(changeRoleInventory);
                }
            }
        }, new SkullItemData(HDBSkulls.CHANGE_IRON_BLUE)
                .setDisplayName(
                        lang.getText("inventory.user.change.name"))
                .addLore(
                        lang.getText("inventory.user.change.desc.1")));
        registerButtonAndItem(1, 4,
                new OpenInventoryButton(invManager, new RoleInventory(player, serverWorld, serverUser.getWorldRole(serverWorld)),
                        player),
                serverUser.getWorldRole(serverWorld).buildDisplayItem());
        registerButtonAndItem(1, new Button() {
            @Override
            public void onClick(InventoryAction event) {
                if (serverWorld.isAllowed(player, "users.remove")) {
                    serverUser.removeRole(serverWorld);
                    MembersInventory membersInventory = new MembersInventory(player, serverWorld);
                    WorldManagement.get().getInventoryBuilder().buildInventory(membersInventory);
                }
            }
        }, new SkullItemData(HDBSkulls.RED_MINUS)
                .setDisplayName(
                        lang.getText("inventory.user.remove.name"))
                .addLore(
                        lang.getText("inventory.user.remove.desc.1")));
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