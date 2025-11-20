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

	private static ItemStack ownedItemStack;
	private static ItemStack notOwnedItemStack;

	public AnimationInventory(Player player, UUID owner) {
		super(player, owner, "animations", "§8Animationen");
	}

	@Override
	protected List<Item> getItems() {
		List<String> owned = new ArrayList<>();
		List<String> notowned = new ArrayList<>();

		Config.animations.get.getStringList("animations").forEach(name -> {

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
		ItemStack item = isOwned ? getOwnedItemStack() : getNotOwnedItemStack();

		Component displayName = item.getItemMeta().displayName().replaceText(TextReplacementConfig.builder()
				.matchLiteral("%name%").replacement(name).build());
		ItemMeta meta = item.getItemMeta();
		meta.displayName(displayName);
		item.setItemMeta(meta);
		return new AnimationItem(name, item, isOwned);
	}

	private ItemStack getOwnedItemStack() {
		if(ownedItemStack == null) {
			ownedItemStack = Config.gui.get.getItemStack("animations.items.owned");
		}
		return ownedItemStack.clone();
	}

	private ItemStack getNotOwnedItemStack() {
		if(notOwnedItemStack == null) {
			notOwnedItemStack = Config.gui.get.getItemStack("animations.items.notowned");
		}
		return notOwnedItemStack.clone();
	}
}
