package com.bocktom.phoenixAnimations;

import com.bocktom.phoenixAnimations.config.Config;
import com.bocktom.phoenixAnimations.config.MSG;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class AnimationCommand implements CommandExecutor, TabCompleter {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player player) {
			if(args.length == 0) {
				new AnimationInventory(player, player.getUniqueId());
				return true;
			}

			if(args.length == 1) {
				Set<String> available = Config.animations.get.getConfigurationSection("animations").getKeys(false);
				if(!available.contains(args[0])) {
					sender.sendMessage("§cDiese Animation existiert nicht: " + args[0]);
					return true;
				}
				String permission = "phoenixanimations." + args[0];
				if(!LuckPermsHelper.hasPermission(player, permission)) {
					sender.sendMessage("§cDu besitzt diese Animation nicht: " + args[0]);
					return true;
				}

				BetterModelHelper.playAnimation(player, args[0]);
				return true;
			}
		}

		if(args.length == 3) {
			if(args[0].equalsIgnoreCase("purchase") && sender.hasPermission("phoenixanimations.purchase")) {
				String targetName = args[1];
				String permission = "phoenixanimations." + args[2];

				Player target = Bukkit.getPlayer(targetName);
				if(target == null) {
					sender.sendMessage("§cDer Spieler ist nicht online: " + targetName);
					return true;
				}

				if(LuckPermsHelper.hasPermission(target, permission)) {
					return true;
				}

				return attemptPurchase(args[2], target, permission);
			}
		}

		sender.sendMessage("§cInkorrekte Nutzung! /animation");

		return true;
	}

	private static boolean attemptPurchase(String animationName, Player target, String permission) {
		boolean hasGutschein = false;
		ItemStack gutschein = null;
		for (ItemStack item : target.getInventory()) {

			// Check for nametag with custom model data 26
			if(item != null
					&& item.getType() == Material.NAME_TAG
					&& item.getItemMeta() != null
					&& item.getItemMeta().lore() != null
					&& item.getItemMeta().getCustomModelDataComponent().getFloats().contains(26f)) {

				PlainTextComponentSerializer plain = PlainTextComponentSerializer.plainText();

				// Check if the nametag lore contains the target player's name
				if(item.getItemMeta().lore().stream()
						.map(plain::serialize)
						.anyMatch(line -> line.contains(target.getName()))) {
					hasGutschein = true;
					gutschein = item.clone();
					gutschein.setAmount(1);
					break;
				}
			}
		}

		if(hasGutschein) {
			target.getInventory().removeItem(gutschein);
			LuckPermsHelper.grantPermission(target, permission);
			target.sendMessage(MSG.get("purchase_success").replace("%name%", animationName));
		} else {
			target.sendMessage(MSG.get("purchase_no_voucher"));
		}
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage("Dieser Befehl kann nur von Spielern ausgeführt werden.");
			return List.of();
		}

		if(args.length == 1) {
			Set<String> available = Config.animations.get.getConfigurationSection("animations").getKeys(false);
			return available.stream()
					.filter(name -> player.hasPermission("phoenixanimations." + name))
					.toList();
		}
		return List.of();
	}
}
