package com.bocktom.phoenixAnimations;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.concurrent.CompletableFuture;

import static org.bukkit.Bukkit.getLogger;

public class LuckPermsHelper {

	private static LuckPerms perms;

	public static boolean setup() {
		RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
		if (provider == null) {
			getLogger().severe("LuckPerms not found! Disabling plugin...");
			return false;
		}
		perms = provider.getProvider();
		return true;
	}

	public static CompletableFuture<Boolean> grantPermission(Player player, String perm) {
		User permUser = perms.getUserManager().getUser(player.getUniqueId());
		if(permUser == null)
			return CompletableFuture.completedFuture(false);

		permUser.data().add(Node.builder(perm).value(true).build());
		CompletableFuture<Void> future = perms.getUserManager().saveUser(permUser);
		return future.thenApply(v -> true);
	}

	public static boolean hasPermission(Player player, String perm) {
		return player.hasPermission(perm);
	}
}
