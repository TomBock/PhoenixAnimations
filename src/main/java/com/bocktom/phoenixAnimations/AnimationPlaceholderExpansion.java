package com.bocktom.phoenixAnimations;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AnimationPlaceholderExpansion extends PlaceholderExpansion {

	@Override
	public @NotNull String getIdentifier() {
		return "phoenixanimations";
	}

	@Override
	public @NotNull String getAuthor() {
		return "TomBock";
	}

	@Override
	public @NotNull String getVersion() {
		return "1.0";
	}

	@Override
	public @Nullable String onPlaceholderRequest(Player player, @NotNull String raw) {
		if(player == null) {
			return "";
		}
		return LuckPermsHelper.hasPermission(player, "phoenixanimations." + raw) ? "1" : "0";
	}
}
