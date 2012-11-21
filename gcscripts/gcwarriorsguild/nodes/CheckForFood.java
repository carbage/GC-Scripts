package gcscripts.gcwarriorsguild.nodes;

import gcapi.constants.items.Food;
import gcscripts.gcwarriorsguild.GcWarriorsGuild;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;

public class CheckForFood extends Node {

	@Override
	public boolean activate() {
		return true;
	}

	@Override
	public void execute() {
		if (Players.getLocal().getHpPercent() < 45 || Inventory.isFull()) {
			Item food = null;
			GcWarriorsGuild.logger.log("Eating food: " + GcWarriorsGuild.foodName);
			for (Item i : Inventory.getAllItems(false)) {
				if (i != null) {
					if (i.getName().contains(GcWarriorsGuild.foodName)) {
						food = i;
					}
				}
			}
			if (food == null) {
				GcWarriorsGuild.logger.log("Out of food, banking.");
				GcWarriorsGuild.isBanking = true;
				return;
			} else {
				food.getWidgetChild().click(true);
			}
		}
	}

}
