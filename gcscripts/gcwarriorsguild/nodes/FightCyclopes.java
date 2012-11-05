package gcscripts.gcwarriorsguild.nodes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import gcapi.constants.Areas;
import gcapi.methods.GenericMethods;
import gcapi.utils.comparators.ClosestNpc;
import gcscripts.gcwarriorsguild.GcWarriorsGuild;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.interactive.NPC;

public class FightCyclopes extends Node {

	@Override
	public boolean activate() {
		return Areas.WARRIORS_GUILD_CYCLOPS_AREA.contains(Players.getLocal());
	}

	@Override
	public void execute() {
		if (Players.getLocal().isIdle() && !Players.getLocal().isInCombat()) {
			NPC[] cyclopes = NPCs.getLoaded(gcapi.constants.NPCs.CYCLOPES);
			if (cyclopes.length > 0) {
				Arrays.sort(cyclopes, new ClosestNpc());
				for (NPC n : cyclopes) {
					if (n != null) {
						if (!n.isOnScreen()) {
							GcWarriorsGuild.logger.log("Attacking cyclops");
							n.getLocation().clickOnMap();
						}
						Camera.turnTo(n);
						if (!n.isInCombat()) {
							GcWarriorsGuild.logger.log("Attacking cyclops");
							n.click(true);
							GenericMethods.waitForCondition(Players.getLocal().isInCombat(), 10000);
							break;
						}
					}
				}
			}

		}
	}

}
