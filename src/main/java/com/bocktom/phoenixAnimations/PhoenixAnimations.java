package com.bocktom.phoenixAnimations;

import com.bocktom.phoenixAnimations.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

public final class PhoenixAnimations extends JavaPlugin {

	public static PhoenixAnimations plugin;

	@Override
	public void onEnable() {
		plugin = this;

		new Config(plugin);

		getCommand("animation").setExecutor(new AnimationCommand());

		new AnimationPlaceholderExpansion().register();

		if(!LuckPermsHelper.setup()) {
			getServer().getPluginManager().disablePlugin(this);
		}
	}

}
