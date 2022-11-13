package de.hyper.worlds.common.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@Getter
public class InventoryData {

    private String ownerUUID;
    private long timestamp;
    private ItemStack[] storageContents;
}
