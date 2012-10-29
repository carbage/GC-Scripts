package gcscripts.gcpowerminer.nodes;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class EatBacon extends Node { // This class empties the inventory if full

	@Override
	public boolean activate() {
		return CookBacon.isDropping;
	}

	@Override
	public void execute() {
		CookBacon.isDropping = true;
		System.out.println("Inventory full, dropping.");
		for (int i = 1; i <= 28; i++) {
			Item ore = Inventory.getItem(i);
			if (ore != null) ore.getWidgetChild().interact("Drop");
		}
		CookBacon.isDropping = false;
	}

}
