package gcscripts.gcwarriorsguild.nodes;

import gcscripts.gcwarriorsguild.GcWarriorsGuild;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.GroundItem;

public class DefenderCollector extends Node {

	private int defenderCount;

	@Override
	public boolean activate() {
		return true;
	}

	@Override
	public void execute() {
		GroundItem defender = GroundItems
				.getNearest(GcWarriorsGuild.defenderId);
		if (defender != null) {
			defenderCount = Inventory.getCount(GcWarriorsGuild.defenderId);
			defender.interact("Pick up");
			Timer timer = new Timer(10000);
			while (defenderCount == Inventory
					.getCount(GcWarriorsGuild.defenderId) && timer.isRunning()) {
			}
			if (!timer.isRunning()
					&& defenderCount == Inventory
							.getCount(GcWarriorsGuild.defenderId)) {
				GcWarriorsGuild.logger
						.log("Defender pick-up timed out after 10 seconds stopping.");
				GcWarriorsGuild.problemFound = true;
			} else {
				GcWarriorsGuild.defendersCollected++;
			}
		}
	}

}
