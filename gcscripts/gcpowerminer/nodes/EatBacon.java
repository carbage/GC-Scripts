package gcscripts.gcpowerminer.nodes;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;

public class EatBacon extends Node { // This class empties the inventory if full

	@Override
	public boolean activate() {
		return Inventory.isFull() || CookBacon.isDropping;
	}

	@Override
	public void execute() {
		CookBacon.isDropping = true;
		for (int i = 1; i <= 28; i++) {
			Inventory.getItem(i).getWidgetChild().interact("Drop");
		}
		CookBacon.isDropping = false;

	}

}
