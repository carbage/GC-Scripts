package gcscripts.gcpowerminer.nodes;

import gcscripts.gcpowerminer.GcPowerMiner;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class CookBacon extends Node { // This class mines the closest ore

	int[] IRON_ROCKS = { 2092, 440, 15, 35, 3 };

	boolean rockfound = false;

	public static boolean isDropping = false;

	@Override
	public boolean activate() {
		return Players.getLocal().isIdle() && isDropping;
	}

	@Override
	public void execute() {
		SceneObject rock = SceneEntities.getNearest(IRON_ROCKS);
		if (rock != null && !rockfound) {
			rockfound = true;
			rock.interact("Mine");
		} else {
			GcPowerMiner.problemFound = true;
		}
	}
}
