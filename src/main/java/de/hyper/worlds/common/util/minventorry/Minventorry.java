package de.hyper.worlds.common.util.minventorry;

import de.hyper.worlds.common.util.minventorry.buttons.Button;
import de.hyper.worlds.common.util.minventorry.designs.Design;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Messages;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class  Minventorry {

	Design design = null;
	public Player player = null;
	public Inventory inventory;
	String id = "", title;
	HashMap<Integer, Button> actions = new HashMap<>();
	@Setter @Getter private boolean closeable;

	public Minventorry(String title, int rows) {
		this.title = title;
		createInv(rows);
		closeable = true;
	}

	public void setSlot(int row, int slot, Button button, ItemStack is) {
		setSlot((row - 1) * 9 + slot, button);
		setSlot((row - 1) * 9 + slot, is);
	}

	public void createInv(int rows) {
		if (inventory == null)
			inventory = Bukkit.createInventory(null, rows * 9,
					Messages.MINV + id + (title.startsWith("§") || id.endsWith("0") ? title : "§0" + title));
		else {
			Design des = design;
			setDesign(null);
			ItemStack[] items = inventory.getContents();
			inventory = Bukkit.createInventory(null, rows * 9,
					Messages.MINV + id + (title.startsWith("§") || id.endsWith("0") ? title : "§0" + title));
			for (int i = 0; i < inventory.getSize() && i < items.length; i++)
				inventory.setItem(i, items[i]);
			setDesign(des);
		}
	}

	public void setRows(int rows) {
		createInv(rows);
		player.openInventory(inventory);
	}

	public void setSlot(int slot, Button button) {
		if (button == null)
			actions.remove(slot);
		else {
			actions.put(slot, button);
			button.add(this);
		}
	}

	public void setSlot(int slot, Button button, ItemStack is) {
		if (is == null)
			inventory.setItem(slot,
					design == null ? null : design.setSlot(1 + (slot - (slot % 9)) / 9, slot % 9, inventory.getSize() / 9));
		else
			inventory.setItem(slot, is);
		if (button == null)
			actions.remove(slot);
		else {
			actions.put(slot, button);
			button.add(this);
		}
	}

	public void setSlot(int slot, ItemStack is) {
		if (is == null)
			inventory.setItem(slot,
					design == null ? null : design.setSlot(1 + (slot - (slot % 9)) / 9, slot % 9, inventory.getSize() / 9));
		else
			inventory.setItem(slot, is);
	}

	public void onClick(int slot, InventoryAction action) {
		if (actions.containsKey(slot)) {
			actions.get(slot).onClick(action);
		}
	}

	public void setDesign(Design design) {
		if (this.design != null) {
			for (int i = 0; i < inventory.getSize(); i++)
				if (inventory.getItem(i) == null
						|| inventory.getItem(i).equals(this.design.setSlot(1 + (i - (i % 9)) / 9, i % 9, inventory.getSize() / 9)))
					inventory.setItem(i,
							design == null ? null : design.setSlot(1 + (i - (i % 9)) / 9, i % 9, inventory.getSize() / 9));
		} else if (design != null)
			for (int i = 0; i < inventory.getSize(); i++)
				if (inventory.getItem(i) == null)
					inventory.setItem(i, design.setSlot(1 + (i - (i % 9)) / 9, i % 9, inventory.getSize() / 9));
		this.design = design;
	}

	public void close() {
		WorldManagement.get().getPerformance().sync(() -> {
			player.closeInventory();
		});
	}

	public String getId() {
		return id;
	}

	public void setPlayer(Player player) {
		if (this.player == null) {
			id = InventoryManager.getNewId();
			ItemStack[] items = inventory.getContents();
			createInv(inventory.getSize() / 9);
			inventory.setContents(items);
		}
		WorldManagement.get().getPerformance().sync(() -> {
			player.closeInventory();
			player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1F, 1F);
			player.openInventory(inventory);
		});
		InventoryManager.invs.put(getId(), this);
		this.player = player;
	}
}