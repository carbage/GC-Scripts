package gcscripts.gcpowerminer.nodes;

import gcscripts.gcpowerminer.GcPowerMiner;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class CookBacon extends Node { // This class mines the closest ore

    int[] IRON_ROCKS = { 11954, 11955, 11956 };
    int[] DEPLETED_IRON_ROCKS = { 11555, 11556, 11557 };

    Area oreArea = null;

    boolean rockfound = false;

    public static boolean isDropping = false;

    @Override
    public boolean activate() {
	return Players.getLocal().isIdle() && !isDropping
		&& !Inventory.isFull();
    }

    @Override
    public void execute() {
	if (!rockfound) {
	    SceneObject rock = SceneEntities.getNearest(IRON_ROCKS);
	    if (rock != null && !rockfound) {
		rockfound = true;
		// oreArea = LocationMethods.getObjectBox(rock);
		// if (oreArea.contains(Players.getLoaded())) {*/
		GcPowerMiner.logger.log("Found rock with id: " + rock.getId());
		rock.click(true);
		Task.sleep(1000, 1500);
		Timer timer = new Timer(5000);
		while (Players.getLocal().isIdle() && timer.isRunning()) {
		}
		// }
		rockfound = false;
	    } else if (SceneEntities.getNearest(DEPLETED_IRON_ROCKS) != null) {
		GcPowerMiner.logger.log("No rocks found, stopping...");
		GcPowerMiner.problemFound = true;
	    }
	}
    }
}
