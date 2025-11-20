package com.bocktom.phoenixAnimations;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class AnimationItem extends AbstractItem {

	public String name;
	public ItemStack item;
	public boolean isOwned;

	public AnimationItem(String name, ItemStack item, boolean isOwned) {
		this.name = name;
		this.item = item;
		this.isOwned = isOwned;
	}

	@Override
	public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
		if(!isOwned)
			return;

		player.closeInventory();
		BetterModelHelper.playAnimation(player, name);
	}

	@Override
	public ItemProvider getItemProvider() {
		return new ItemBuilder(item);
	}
}
