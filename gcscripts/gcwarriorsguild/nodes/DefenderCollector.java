package gcscripts.gcwarriorsguild.nodes;

import gcapi.constants.Equipment;
import gcapi.methods.GenericMethods;
import gcscripts.gcwarriorsguild.GcWarriorsGuild;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.GroundItem;

public class DefenderCollector extends Node {

	private int defenderCount;

	@Override
	public boolean activate() {
		return true;
	}

	@Override
	public void execute() {
		GroundItem defender = GroundItems.getNearest(Equipment.DEFENDER_IDS);
		if (defender != null) {
			if (!defender.isOnScreen()) {
				Camera.turnTo(defender);
			}
			if (defender.interact("Take", "defender")) {
				GcWarriorsGuild.defendersCollected++;
			} else {
				GcWarriorsGuild.logger.log("Defender pick-up timed out after 10 seconds.");
			}
		}
	}

	public int getDefenderCount() {
		return defenderCount;
	}
}
