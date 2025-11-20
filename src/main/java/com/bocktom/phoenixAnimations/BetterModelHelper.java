package com.bocktom.phoenixAnimations;

import kr.toxicity.model.api.BetterModel;
import kr.toxicity.model.api.animation.AnimationModifier;
import kr.toxicity.model.api.tracker.EntityTracker;
import org.bukkit.entity.Player;

import java.util.Optional;

public class BetterModelHelper {

	public static void playAnimation(Player player, String animation) {
		// Reference: https://github.com/toxicity188/BetterModel/wiki/API-example
		Optional<EntityTracker> trackerOpt = BetterModel.limb("emotes").map(r -> r.getOrCreate(player));
		if(trackerOpt.isPresent()) {

			// Play Animation
			EntityTracker t = trackerOpt.get();
			if (!t.animate(animation, AnimationModifier.DEFAULT_WITH_PLAY_ONCE, t::close)) {
				player.sendMessage("§cAnimation nicht gefunden: " + animation + "! Wende dich bitte an das Team.");
			}

		} else {
			player.sendMessage("§cKein Emote-Modell gefunden! Wende dich bitte an das Team.");
		}
	}
}
