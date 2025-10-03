package de.hyper.worlds.domain.inventories;

import de.hyper.inventory.Inventory;
import de.hyper.inventory.InventoryManager;
import de.hyper.inventory.buttons.Button;
import de.hyper.inventory.buttons.OpenInventoryButton;
import de.hyper.inventory.designs.CleanBackGroundDesign;
import de.hyper.inventory.items.GlassPane;
import de.hyper.inventory.items.SimpleItemData;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.obj.world.ServerWorldSettingChangedEvent;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import de.hyper.worlds.domain.using.Performance;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

public class SettingsInventory extends Inventory {

    protected ServerWorld serverWorld;
    Language lang = WorldManagement.get().getLanguage();
    Performance performance = WorldManagement.get().getPerformance();
    InventoryManager invManager = WorldManagement.get().getInventoryManager();

    public SettingsInventory(Player player, ServerWorld serverWorld) {
        super(player, "Settings", 6);
        this.setDesign(new CleanBackGroundDesign(this.getRows(), GlassPane.C7));
        this.serverWorld = serverWorld;
    }

    @Override
    public Inventory fillInventory() {
        registerButtonAndItem(5, 0, new OpenInventoryButton(invManager, new ServerWorldInventory(player, serverWorld), player),
                new SimpleItemData(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .addLore(
                                lang.getText("inventory.general.back.desc")));
        int row = 1;
        int slot = 1;
        for (WorldSetting setting : serverWorld.getSettings()) {
            registerButtonAndItem(row, slot, new Button() {
                @Override
                public void onClick(InventoryAction inventoryAction) {
                    boolean allowed;
                    if (setting.isAdminSetting()) {
                        allowed = player.hasPermission("worldmanager.admin.adminsettings");
                    } else {
                        allowed = serverWorld.isAllowed(
                                player,
                                "settings." + setting.getName().replace("-", "").toLowerCase() + ".change");
                    }
                    if (allowed) {
                        setting.change(inventoryAction);
                        performance.sync(() -> {
                            Bukkit.getPluginManager().callEvent(
                                    new ServerWorldSettingChangedEvent(
                                            serverWorld,
                                            player,
                                            serverWorld.getBukkitWorld(),
                                            setting.getType()));
                        });
                        fillInventory();
                    }
                }
            }, setting.buildDisplayItem());
            slot++;
            if (slot == 4) {
                slot++;
            }
            if (slot > 7) {
                slot = 1;
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