package de.hyper.worlds.domain.inventories;

import de.hyper.inventory.InfinityInventory;
import de.hyper.inventory.Inventory;
import de.hyper.inventory.InventoryManager;
import de.hyper.inventory.buttons.Button;
import de.hyper.inventory.buttons.NoButton;
import de.hyper.inventory.buttons.OpenInventoryButton;
import de.hyper.inventory.designs.BottomLineBackGroundDesign;
import de.hyper.inventory.items.GlassPane;
import de.hyper.inventory.items.SimpleItemData;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.obj.world.role.RoleAdmission;
import de.hyper.worlds.common.obj.world.role.WorldRole;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.SkullItemData;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemFlag;

public class RoleInventory extends InfinityInventory<RoleAdmission> {

    protected ServerWorld serverWorld;
    protected WorldRole worldRole;
    Language lang = WorldManagement.get().getLanguage();
    InventoryManager invManager = WorldManagement.get().getInventoryManager();

    public RoleInventory(Player player, ServerWorld serverWorld, WorldRole worldRole) {
        super(player, "Role: " + worldRole.getName(), 6);
        this.setDesign(new BottomLineBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.serverWorld = serverWorld;
        this.worldRole = worldRole;
        this.list = this.worldRole.getAdmissions();
        this.maxPage = list.size() / 45;
        this.currentPage = 0;
    }

    @Override
    public Inventory fillInventory() {
        registerButtonAndItem(5, 4, new OpenInventoryButton(invManager, new RolesInventory(player, serverWorld), player),
                new SimpleItemData(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .addLore(
                                lang.getText("inventory.general.back.desc")));
        if (currentPage > 0) {
            registerLastPageButton(5, 0,
                    new SkullItemData(HDBSkulls.OAK_WOOD_ARROW_LEFT)
                            .setDisplayName(
                                    lang.getText("inventory.general.pages.last")));
        }
        if (currentPage < maxPage) {
            registerNextPageButton(5, 8,
                    new SkullItemData(HDBSkulls.OAK_WOOD_ARROW_RIGHT)
                            .setDisplayName(
                                    lang.getText("inventory.general.pages.next")));
        }
        int row = 0;
        int slot = 0;
        for (int i = 0; i != 45; i++) {
            int get = i + (currentPage * 45);
            if (get < list.size()) {
                RoleAdmission roleAdmission = list.get(get);
                registerButtonAndItem(row, slot, new Button() {
                    @Override
                    public void onClick(InventoryAction inventoryAction) {
                        if (serverWorld.isAllowed(player, "roles.edit")) {
                            roleAdmission.switchAllowed();
                            clearInventory().fillInventory();
                        }
                    }
                }, new SimpleItemData(roleAdmission.getMaterial())
                        .setDisplayName("§b" + roleAdmission.getDisplay())
                        .addLore("§7Key: §b" + roleAdmission.getKey(),
                                lang.getText(
                                        "inventory.role.each.desc.1",
                                        roleAdmission.isAllowed()),
                                lang.getText("inventory.role.each.desc.2"))
                        .addItemFlags(ItemFlag.values()));
                slot++;
                if (slot > 8) {
                    slot = 0;
                    row++;
                }
            } else break;
        }
        return this;
    }

    @Override
    public Inventory clearInventory() {
        int row = 0;
        int slot = 0;
        for (int i = 0; i != 45; i++) {
            registerButtonAndItem(row, slot, new NoButton(), null);
            slot++;
            if (slot > 8) {
                slot = 0;
                row++;
            }
        }
        registerButtonAndItem(5, 0, new NoButton(), GlassPane.C7);
        registerButtonAndItem(5, 8, new NoButton(), GlassPane.C7);
        return this;
    }

    @Override
    public void onOpen() {
    }

    @Override
    public void onClose() {
    }
}