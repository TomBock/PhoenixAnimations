package com.bocktom.phoenixAnimations.config;

import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.ScrollGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;
import xyz.xenondevs.invui.item.impl.controlitem.ScrollItem;

import java.util.List;

public class BackItem extends PageItem {

	private final ItemStack item;

	public BackItem(ItemStack item) {
		super(false);
		this.item = item;
	}

	@Override
	public ItemProvider getItemProvider(PagedGui<?> gui) {
		ItemStack item = this.item.clone();
		if(gui.hasPreviousPage())
			item.setLore(List.of());
		return new ItemBuilder(item);
	}
}
