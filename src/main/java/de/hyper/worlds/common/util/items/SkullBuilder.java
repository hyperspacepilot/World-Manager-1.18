package de.hyper.worlds.common.util.items;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
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
		GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "");
		gameProfile.getProperties().put("textures", new Property("textures", skullValue));
		Field profileField = null;
		try {
			profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, gameProfile);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		head.setItemMeta(headMeta);
		return head;
	}

	public static ItemStack getSkullByHDB(HDBSkulls hdbskull) {
		return getSkullBySkullValue(hdbskull.getValue());
	}
}