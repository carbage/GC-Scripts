package gcscripts.gcpowerminer.nodes;

import gcscripts.gcpowerminer.GcPowerMiner;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class CookBacon extends Node { // This class mines the closest ore

	int[] IRON_ROCKS = { 11954, 11955, 11956};
	int[] DEPLETED_IRON_ROCKS = { 11555, 11556, 11557};

	boolean rockfound = false;

	public static boolean isDropping = false;

	@Override
	public boolean activate() {
		return Players.getLocal().isIdle() && !isDropping;
	}

	@Override
	public void execute() {
		if(Inventory.isFull()) isDropping = true;
		if (!rockfound) {
			SceneObject rock = SceneEntities.getNearest(IRON_ROCKS);
			if (rock != null && !rockfound) {
				rockfound = true;
				System.out.println("Found rock with id: " + rock.getId());
				rock.click(true);
				Task.sleep(500, 1000);
				rockfound = false;
			} else if (SceneEntities.getNearest(DEPLETED_IRON_ROCKS) != null) {
				System.out.println("No rocks found, stopping...");
				GcPowerMiner.problemFound = true;
			}
		}
	}
}
