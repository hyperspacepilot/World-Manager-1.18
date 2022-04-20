package de.hyper.worlds.common.util.minventorry.buttons;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

public class CloseButton extends Button {

	@Override
	public void onClick(InventoryAction action) {
		Player player = minv.player;
		player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1F, 1F);
		minv.close();
	}
}
