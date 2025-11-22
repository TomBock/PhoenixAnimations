package com.bocktom.phoenixAnimations;

import com.bocktom.phoenixAnimations.config.Config;
import com.bocktom.phoenixAnimations.config.ConfigInventory;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.xenondevs.invui.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AnimationInventory extends ConfigInventory {

	public AnimationInventory(Player player, UUID owner) {
		super(player, owner, "animations", "§8Animationen");
	}

	@Override
	protected List<Item> getItems() {
		List<String> owned = new ArrayList<>();
		List<String> notowned = new ArrayList<>();

		Config.animations.get.getConfigurationSection("animations").getKeys(false).forEach(name -> {

			if(player.hasPermission("phoenixanimations." + name)) {
				owned.add(name);
			} else {
				notowned.add(name);
			}
		});

		owned.sort(String::compareToIgnoreCase);
		notowned.sort(String::compareToIgnoreCase);

		List<Item> uiItems = new ArrayList<>(owned.stream().map(name -> createAnimationItem(name, true)).toList());
		uiItems.addAll(notowned.stream().map(name -> createAnimationItem(name, false)).toList());

		return uiItems;
	}

	private Item createAnimationItem(String name, boolean isOwned) {
		ItemStack item = isOwned ? getOwnedItemStack(name) : getNotOwnedItemStack(name);

		Component displayName = item.getItemMeta().displayName().replaceText(TextReplacementConfig.builder()
				.matchLiteral("%name%").replacement(name).build());
		ItemMeta meta = item.getItemMeta();
		meta.displayName(displayName);
		item.setItemMeta(meta);
		return new AnimationItem(name, item, isOwned);
	}

	private ItemStack getOwnedItemStack(String name) {
		ItemStack itemStack = Config.animations.get.getItemStack("animations." + name + ".owned");
		if(itemStack == null) {
			PhoenixAnimations.plugin.getLogger().warning("Could not find owned item stack for animation: " + name);
			return new ItemStack(org.bukkit.Material.BARRIER);
		}
		return itemStack.clone();
	}

	private ItemStack getNotOwnedItemStack(String name) {
		ItemStack itemStack = Config.animations.get.getItemStack("animations." + name + ".notowned");
		if(itemStack == null) {
			PhoenixAnimations.plugin.getLogger().warning("Could not find notowned item stack for animation: " + name);
			return new ItemStack(org.bukkit.Material.BARRIER);
		}
		return itemStack.clone();
	}
}
