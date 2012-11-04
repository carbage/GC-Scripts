package gcscripts.gcwarriorsguild.nodes;

import gcapi.constants.Areas;
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
			NPC cyclops = NPCs.getNearest(gcapi.constants.NPCs.CYCLOPES);
			if (cyclops != null) {
				if (!cyclops.isOnScreen()) {
					GcWarriorsGuild.logger.log("Attacking cyclops");
					cyclops.getLocation().clickOnMap();
				}
				Camera.turnTo(cyclops);
				if (!cyclops.isInCombat()) {
					GcWarriorsGuild.logger.log("Attacking cyclops");
					cyclops.click(true);
				}
			}
		}
	}

}
