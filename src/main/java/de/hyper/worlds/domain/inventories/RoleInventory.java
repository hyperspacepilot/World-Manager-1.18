package de.hyper.worlds.domain.inventories;

import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.obj.world.role.RoleAdmission;
import de.hyper.worlds.common.obj.world.role.WorldRole;
import de.hyper.worlds.common.util.inventory.InfinityInventory;
import de.hyper.worlds.common.util.inventory.Inventory;
import de.hyper.worlds.common.util.inventory.buttons.Button;
import de.hyper.worlds.common.util.inventory.buttons.NoButton;
import de.hyper.worlds.common.util.inventory.buttons.OpenInventoryButton;
import de.hyper.worlds.common.util.inventory.designs.BottomLineBackGroundDesign;
import de.hyper.worlds.common.util.items.GlassPane;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.ItemBuilder;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;

public class RoleInventory extends InfinityInventory<RoleAdmission> {

    protected ServerWorld serverWorld;
    protected WorldRole worldRole;
    Language lang = WorldManagement.get().getLanguage();

    public RoleInventory(ServerWorld serverWorld, WorldRole worldRole) {
        super("Role: " + worldRole.getName(), 6, true);
        this.setDesign(new BottomLineBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.serverWorld = serverWorld;
        this.worldRole = worldRole;
        this.list = this.worldRole.getAdmissions();
        this.maxPage = list.size() / 45;
        this.currentPage = 0;
    }

    @Override
    public Inventory fillInventory() {
        registerButton(6, 4, new OpenInventoryButton(new RolesInventory(serverWorld), player),
                new ItemBuilder(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .setLore(
                                lang.getText("inventory.general.back.desc"))
                        .getItem());
        if (currentPage > 0) {
            registerLastPageButton(6, 0,
                    new ItemBuilder(HDBSkulls.OAK_WOOD_ARROW_LEFT)
                            .setDisplayName(
                                    lang.getText("inventory.general.pages.last"))
                            .getItem());
        }
        if (currentPage < maxPage) {
            registerNextPageButton(6, 8,
                    new ItemBuilder(HDBSkulls.OAK_WOOD_ARROW_RIGHT)
                            .setDisplayName(
                                    lang.getText("inventory.general.pages.next"))
                            .getItem());
        }
        int row = 1;
        int slot = 0;
        for (int i = 0; i != 45; i++) {
            int get = i + (currentPage * 45);
            if (get < list.size()) {
                RoleAdmission roleAdmission = list.get(get);
                registerButton(row, slot, new Button() {
                    @Override
                    public void onClick(InventoryAction inventoryAction) {
                        if (serverWorld.isAllowed(player, "roles.edit")) {
                            roleAdmission.switchAllowed();
                            cleanInventory().fillInventory();
                        }
                    }
                }, new ItemBuilder(roleAdmission.getMaterial())
                        .setDisplayName("§b" + roleAdmission.getDisplay())
                        .setLore("§7Key: §b" + roleAdmission.getKey(),
                                lang.getText(
                                        "inventory.role.each.desc.1",
                                        roleAdmission.isAllowed()),
                                lang.getText("inventory.role.each.desc.2"))
                        .hideAttributes()
                        .getItem());
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
    public Inventory cleanInventory() {
        int row = 1;
        int slot = 0;
        for (int i = 0; i != 45; i++) {
            registerButton(row, slot, new NoButton(), null);
            slot++;
            if (slot > 8) {
                slot = 0;
                row++;
            }
        }
        registerButton(6, 0, new NoButton(), GlassPane.C7);
        registerButton(6, 8, new NoButton(), GlassPane.C7);
        return this;
    }

    @Override
    public void onOpen() {

    }

    @Override
    public void onClose() {

    }
}
