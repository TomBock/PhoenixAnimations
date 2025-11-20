package com.bocktom.phoenixAnimations.config;

import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

import java.util.List;

public class ForwardItem extends PageItem {

	private final ItemStack item;

	public ForwardItem(ItemStack item) {
		super(true);
		this.item = item;
	}

	@Override
	public ItemProvider getItemProvider(PagedGui<?> gui) {
		ItemStack item = this.item.clone();
		if(gui.hasNextPage())
			item.setLore(List.of());
		return new ItemBuilder(item);
	}
}
