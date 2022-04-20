package de.hyper.worlds.common.util.minventorry.buttons;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryAction;

public class RunCommandButton extends Button {
	
	String cmd;
	boolean inConsole;
	
	public RunCommandButton(String cmd, boolean inConsole) {
		super();
		this.cmd = cmd;
		this.inConsole = inConsole;
	}
	
	@Override
	public void onClick(InventoryAction action) {
		if (inConsole) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
		else minv.player.chat("/"+cmd);
	}
}
