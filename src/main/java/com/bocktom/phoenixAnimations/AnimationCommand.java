package com.bocktom.phoenixAnimations;

import com.bocktom.phoenixAnimations.config.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AnimationCommand implements CommandExecutor, TabCompleter {



	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage("Dieser Befehl kann nur von Spielern ausgeführt werden.");
			return true;
		}

		if(args.length == 0) {
			new AnimationInventory(player, player.getUniqueId());
			return true;
		}

		if(args.length == 1) {
			List<String> available = Config.animations.get.getStringList("animations");
			if(!available.contains(args[0])) {
				sender.sendMessage("§cDiese Animation existiert nicht: " + args[0]);
				return true;
			}
			if(!player.hasPermission("phoenixanimations." + args[0])) {
				sender.sendMessage("§cDu besitzt diese Animation nicht: " + args[0]);
				return true;
			}

			BetterModelHelper.playAnimation(player, args[0]);
			return true;
		}


		sender.sendMessage("§cInkorrekte Nutzung! /animation");

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage("Dieser Befehl kann nur von Spielern ausgeführt werden.");
			return List.of();
		}

		if(args.length == 1) {
			List<String> available = Config.animations.get.getStringList("animations");
			return available.stream()
					.filter(name -> player.hasPermission("phoenixanimations." + name))
					.toList();
		}
		return List.of();
	}
}
