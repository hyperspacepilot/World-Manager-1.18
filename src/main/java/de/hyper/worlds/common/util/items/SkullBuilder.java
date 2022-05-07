package de.hyper.worlds.common.util.items;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class SkullBuilder {

	public static ItemStack getSkullByPlayerName(String playerName) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
		SkullMeta skullmeta = (SkullMeta) item.getItemMeta();
		skullmeta.setOwner(playerName);
		item.setItemMeta(skullmeta);
		return item;
	}

	public static ItemStack getSkullBySkullValue(String skullValue) {
		ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
		if (skullValue.isEmpty())
			return head;
		SkullMeta headMeta = (SkullMeta) head.getItemMeta();
		PlayerProfile playerProfile = Bukkit.createProfile(UUID.randomUUID(), "");
		playerProfile.getProperties().add(new ProfileProperty("textures", skullValue));
		headMeta.setPlayerProfile(playerProfile);
		head.setItemMeta(headMeta);
		return head;
	}

	public static ItemStack getSkullByHDB(HDBSkulls hdbskull) {
		return getSkullBySkullValue(hdbskull.getValue());
	}
}