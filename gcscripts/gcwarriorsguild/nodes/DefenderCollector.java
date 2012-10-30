package gcscripts.gcwarriorsguild.nodes;

import gcscripts.gcwarriorsguild.GcWarriorsGuild;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.wrappers.node.GroundItem;

public class DefenderCollector extends Node {

	@Override
	public boolean activate() {
		return true;
	}

	@Override
	public void execute() {
		GroundItem defender = GroundItems
				.getNearest(GcWarriorsGuild.defenderId);
		if (defender != null) {
			defender.interact("Pick up");
		}
	}

}
