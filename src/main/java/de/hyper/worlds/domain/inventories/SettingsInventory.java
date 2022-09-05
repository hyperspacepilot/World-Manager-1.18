package de.hyper.worlds.domain.inventories;

import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.obj.world.ServerWorldSettingChangedEvent;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import de.hyper.worlds.common.util.inventory.Inventory;
import de.hyper.worlds.common.util.inventory.buttons.Button;
import de.hyper.worlds.common.util.inventory.buttons.OpenInventoryButton;
import de.hyper.worlds.common.util.inventory.designs.CleanBackGroundDesign;
import de.hyper.worlds.common.util.items.GlassPane;
import de.hyper.worlds.common.util.items.ItemBuilder;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import de.hyper.worlds.domain.using.Performance;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;

public class SettingsInventory extends Inventory {

    protected ServerWorld serverWorld;
    Language lang = WorldManagement.get().getLanguage();
    Performance performance = WorldManagement.get().getPerformance();

    public SettingsInventory(ServerWorld serverWorld) {
        super("Settings", 6, true);
        this.setDesign(new CleanBackGroundDesign(this.getRows(), GlassPane.C7));
        this.serverWorld = serverWorld;
    }

    @Override
    public Inventory fillInventory() {
        registerButton(6, 0, new OpenInventoryButton(new ServerWorldInventory(serverWorld), player),
                new ItemBuilder(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .setLore(
                                lang.getText("inventory.general.back.desc"))
                        .getItem());
        int row = 2;
        int slot = 1;
        for (WorldSetting setting : serverWorld.getSettings()) {
            registerButton(row, slot, new Button() {
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