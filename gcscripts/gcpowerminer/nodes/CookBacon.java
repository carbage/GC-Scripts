package gcscripts.gcpowerminer.nodes;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class CookBacon extends Node { // This class mines the closest ore
	
	int[] IRON_ROCKS = {2092, 440, 15, 35, 3};
	
	boolean rockfound = false;

	@Override
	public boolean activate() {
		return Players.getLocal().isIdle();
	}

	@Override
	public void execute() {
		for(int i = 0; i > IRON_ROCKS.length; i++) {
			SceneObject rock = SceneEntities.getNearest(IRON_ROCKS[i]);
			if(rock != null && !rockfound) {
				rockfound = true;
				rock.interact("Mine");
				break;
			}
		}
	}

}
