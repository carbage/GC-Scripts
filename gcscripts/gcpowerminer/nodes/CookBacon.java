package gcscripts.gcpowerminer.nodes;

import gcscripts.gcpowerminer.GcPowerMiner;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class CookBacon extends Node { // This class mines the closest ore

	int[] IRON_ROCKS = { 11954, 11955, 11556 };

	boolean rockfound = false;

	public static boolean isDropping = false;

	@Override
	public boolean activate() {
		return Players.getLocal().isIdle() && !isDropping;
	}

	@Override
	public void execute() {
		if (!rockfound) {
			SceneObject rock = SceneEntities.getNearest(IRON_ROCKS);
			if (rock != null && !rockfound) {
				rockfound = true;
				System.out.println("Found rock with id: " + rock.getId());
				Camera.turnTo(rock);
				rock.interact("Mine");
				rockfound = false;
			} else  {
				GcPowerMiner.problemFound = true;
			}
		}
	}
}
