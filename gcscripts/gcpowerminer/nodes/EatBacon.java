package gcscripts.gcpowerminer.nodes;

import gcscripts.gcpowerminer.GcPowerMiner;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;

public class EatBacon extends Node { // This class empties the inventory if full

    int IRON_ORE_ID = 440;

    @Override
    public boolean activate() {
	return Inventory.isFull();
    }

    @Override
    public void execute() {
	CookBacon.isDropping = true;
	GcPowerMiner.logger.log("Inventory full, dropping.");
	
	for(Item i : Inventory.getAllItems(true))
		i.getWidgetChild().interact("Drop");
	
	for (int i = 0; i <= 27; i++) {
	    Item item = Inventory.getItemAt(i);
	    if (item != null) {
	    	item.getWidgetChild().interact("Drop");
	    }
	}
	GcPowerMiner.logger.log("Inventory emptied, continuing.");
	CookBacon.isDropping = false;
    }

}
