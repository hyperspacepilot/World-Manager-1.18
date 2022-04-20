package de.hyper.worlds.common.util.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GlassPane {
	
	public static final ItemStack C0 = get(Material.WHITE_STAINED_GLASS_PANE),
			C1 = get(Material.ORANGE_STAINED_GLASS_PANE),
			C2 = get(Material.MAGENTA_STAINED_GLASS_PANE),
			C3 = get(Material.LIGHT_BLUE_STAINED_GLASS_PANE),
			C4 = get(Material.YELLOW_STAINED_GLASS_PANE),
			C5 = get(Material.LIME_STAINED_GLASS_PANE),
			C6 = get(Material.PINK_STAINED_GLASS_PANE),
			C7 = get(Material.GRAY_STAINED_GLASS_PANE),
			C8 = get(Material.LIGHT_GRAY_STAINED_GLASS_PANE),
			C9 = get(Material.CYAN_STAINED_GLASS_PANE),
			C10 = get(Material.PURPLE_STAINED_GLASS_PANE),
			C11 = get(Material.BLUE_STAINED_GLASS_PANE),
			C12 = get(Material.BROWN_STAINED_GLASS_PANE),
			C13 = get(Material.GREEN_STAINED_GLASS_PANE),
			C14 = get(Material.RED_STAINED_GLASS_PANE),
			C15 = get(Material.BLACK_STAINED_GLASS_PANE);
	public static final ItemStack WHITE = C0, ORANGE = C1, MAGENTA = C2, LIGHT_BLUE = C3, YELLOW = C4, LIME = C5, PINK = C6, GRAY = C7, LIGHT_GRAY = C8, CYAN = C9, PURPLE = C10, BLUE = C11, BROWN = C12, GREEN = C13, RED = C14, BLACK = C15;
	
	private static final ItemStack get(Material mat) {
		ItemStack is = new ItemStack(mat, 1);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("§a");
		is.setItemMeta(im);
		return is;
	}
}
