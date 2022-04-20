package de.hyper.worlds.common.util.minventorry;

import java.util.HashMap;

import de.hyper.worlds.domain.using.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class InventoryManager implements Listener {

	static HashMap<String, Minventorry> invs = new HashMap<String, Minventorry>();

	public static int id = 0;

	public static String getNewId() {
		String sid = "";
		for (char c : (id + "").toCharArray())
			sid += "§" + c;
		while (sid.length() < 8)
			sid = "§0" + sid;
		id++;
		if (id > 9999)
			id = 0;
		return sid;
	}

	@EventHandler
	public static void onInventoryClick(InventoryClickEvent event) {
		Inventory inv = event.getClickedInventory();
		InventoryView view = event.getView();
		if (inv != null && view != null) {
			if (view.getTitle() != null && view.getTitle().startsWith(Messages.MINV)) {
				event.setCancelled(true);
				String id = view.getTitle().substring(6, 14);
				if (invs.containsKey(id)) {
					invs.get(id).onClick(event.getSlot(), event.getAction());
				}
			}
		}
	}

	@EventHandler
	public static void onInventoryClose(InventoryCloseEvent event) {
		Inventory inv = event.getInventory();
		InventoryView view = event.getView();
		if (inv != null && view != null) {
			if (view.getTitle() != null && view.getTitle().startsWith(Messages.MINV)) {
				String id = view.getTitle().substring(6, 14);
				if (invs.containsKey(id)) {
					Minventorry minv = invs.get(id);
					if (minv.isCloseable()) {
						invs.remove(id);
					} else {
						minv.setPlayer((Player) event.getPlayer());
					}
				}
			}
		}
	}
}